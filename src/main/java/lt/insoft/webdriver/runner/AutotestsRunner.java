package lt.insoft.webdriver.runner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.test.commons.Constants;

import lt.insoft.webdriver.runner.lister.TestLister;
import lt.insoft.webdriver.runner.model.RunnerConfiguration;
import lt.insoft.webdriver.runner.model.TestItem;
import lt.insoft.webdriver.runner.util.ConcurrentAllureRunListener;
import lt.insoft.webdriver.runner.util.LogStream;
import lt.insoft.webdriver.runner.util.RunnerUtils;

@Component
public class AutotestsRunner implements CommandLineRunner, ApplicationContextAware {
	private static final Log LOG = LogFactory.getLog(AutotestsRunner.class);

	private ApplicationContext applicationContext;

	@Autowired
	private RunnerConfiguration runnerConfiguration;

	@Autowired
	private XmlBuilder xmlBuilder;

	@Autowired
	private ConcurrentAllureRunListener concurrentAllureRunListener;

	@Override
	public void run(String... args) throws Exception {

		switch (parseCommandLine(args, runnerConfiguration)) {
		case LIST_TESTS:
			TestLister.listTests(runnerConfiguration.getTestsPackage(), runnerConfiguration.getListerOutFile());

			System.exit(SpringApplication.exit(applicationContext));
			break;
		case LIST_PACKAGES:
			TestLister.listPackages(runnerConfiguration.getTestsPackage(), runnerConfiguration.getListerOutFile());

			System.exit(SpringApplication.exit(applicationContext));
			break;

		case RUN_TESTS:
			LOG.info("Running tests.");
			System.setOut(LogStream.getInstance()); // redirecting System.out.print[ln] to log.

			Map<Class<?>, List<TestItem>> testItems = buildTestItems();

			runTests(testItems);
//			xmlBuilder.buildEnvironmentDescription();

			LOG.info("Done.");
			System.exit(SpringApplication.exit(applicationContext, new TestsStatusGenerator()));
			break;

		case EXIT_ERROR:
		default:
			System.exit(1);
			break;
		}
	}

	private void runTests(Map<Class<?>, List<TestItem>> testItems) throws Exception {
		int testIndex = 0;
		ExecutorService executorService = Executors.newFixedThreadPool(Constants.THREAD_COUNT);

		for (Entry<Class<?>, List<TestItem>> entry : testItems.entrySet()) {

			RunnerUtils.invokeMultiple(RunnerUtils.getMethodsAnnotatedWith(entry.getKey(), BeforeClass.class));

			List<TestItem> sorted = entry.getValue().stream().sorted((x, y) -> x.getTitle().compareTo(y.getTitle()))
					.collect(Collectors.toList());

			for (TestItem item : sorted) {
					executorService.submit(new TestCallable(++testIndex, (int) testItems.values().stream().flatMap(List::stream).count(),
							item, runnerConfiguration, concurrentAllureRunListener));
			}

			RunnerUtils.invokeMultiple(RunnerUtils.getMethodsAnnotatedWith(entry.getKey(), AfterClass.class));
		}

		try {
			executorService.shutdown();
			executorService.awaitTermination(55, TimeUnit.HOURS);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		concurrentAllureRunListener.testRunFinished(null);

	}

	private Map<Class<?>, List<TestItem>> buildTestItems() throws Exception {
		List<TestItem> testItems = new ArrayList<TestItem>();

		for (String wt : runnerConfiguration.getTests()) {
			String[] pair = wt.split(":");
			testItems.add(new TestItem(Class.forName(pair[0]), StringUtils.substringBefore(pair[1], " (")));
		}
		Map<Class<?>, List<TestItem>> tests = testItems.stream().collect(Collectors.groupingBy(t -> t.getTestClass()));
		return tests;
	}

	private AppMode parseCommandLine(String[] args, RunnerConfiguration configuration) {
		CommandLineParser parser = new DefaultParser();

		Option list = Option.builder("l").longOpt("list").hasArgs().numberOfArgs(2).argName("package,file")
				.valueSeparator(',').desc("List all tests from package to file.").build();
		Option listPackage = Option.builder("lp").longOpt("listPackage").hasArgs().numberOfArgs(2).argName("package,file")
				.valueSeparator(',').desc("List all packages from package to file.").build();
		Option runAll = Option.builder("a").longOpt("all").hasArg().argName("package").optionalArg(true)
				.desc("Run all tests.").build();
		Option run = Option.builder("r").longOpt("run").hasArgs().argName("tests").valueSeparator(',')
				.desc("Test methods to run.").build();

		OptionGroup runGroup = new OptionGroup();
		runGroup.addOption(list);
		runGroup.addOption(listPackage);
		runGroup.addOption(runAll);
		runGroup.addOption(run);
		runGroup.setRequired(true);

		Options options = new Options();
		options.addOptionGroup(runGroup);

		try {
			CommandLine line = parser.parse(options, args);

			if (line.hasOption(runAll.getOpt())) {
				List<String> tests = RunnerUtils
						.getTestMethods(line.getOptionValue(runAll.getOpt(), TestLister.DEFAULT_PACKAGE));
				configuration.setTests(tests.toArray(new String[tests.size()]));
			} else if (line.hasOption(run.getOpt())) {
				configuration.setTests(line.getOptionValues(run.getOpt()));
			} else if (line.hasOption(list.getOpt())) {
				String[] params = line.getOptionValues(list.getOpt());
				configuration.setItemPackage(params[0]);
				configuration.setListerOutFile(params[1]);
				return AppMode.LIST_TESTS;
			} else if (line.hasOption(listPackage.getOpt())) {
				String[] params = line.getOptionValues(listPackage.getOpt());
				configuration.setItemPackage(params[0]);
				configuration.setListerOutFile(params[1]);
				return AppMode.LIST_PACKAGES;
			}
			return AppMode.RUN_TESTS;

		} catch (Exception e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.setWidth(150);
			formatter.printHelp("autotests-webdriver-runner", options, true);

			return AppMode.EXIT_ERROR;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private class TestsStatusGenerator implements ExitCodeGenerator {
		@Override
		public int getExitCode() {
			return TestItem.getIsSuccess() ? 0 : 1;
		}
	}

	private enum AppMode {
		RUN_TESTS, LIST_TESTS, EXIT_ERROR, LIST_PACKAGES
	}

}
