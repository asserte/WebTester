package lt.insoft.webdriver.runner.lister;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import lt.insoft.webdriver.runner.util.RunnerUtils;

public class TestLister {
	private static final Log LOG = LogFactory.getLog(TestLister.class);

	public static final String DEFAULT_PACKAGE = "com.test";

	public static void listTests(String scanPackage, String filePath) {
		LOG.info(String.format("Listing all @Test annotated methods from package %s to file %s", scanPackage, filePath));
		listItems(RunnerUtils.getTestMethods(scanPackage), filePath, "tests=");
	}
	
	public static void listPackages(String scanPackage, String filePath) {
		LOG.info(String.format("Listing all packages from package %s to file %s", scanPackage, filePath));
		listItems(RunnerUtils.getPackagesUnderPackage(scanPackage + ".*", scanPackage + ".scripts"), filePath, "packages=");
	}
	
	private static void listItems(List<String> list, String filePath, String type) {
		Assert.hasText(filePath, "File path must not be empty");
		try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
			out.write(type);
			out.write(buildString(list));
		} catch (Exception e) {
			System.err.println("Unable to list items to file " + filePath);
		}

		LOG.info("Done.");
	}

	private static String buildString(List<String> list) {
		StringJoiner joiner = new StringJoiner(",\\\n");
		list.stream().forEach(joiner::add);
		return joiner.toString();
	}
}