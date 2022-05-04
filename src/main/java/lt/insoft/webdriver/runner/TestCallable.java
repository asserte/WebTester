package lt.insoft.webdriver.runner;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.TimeoutException;

import lt.insoft.webdriver.runner.model.RunnerConfiguration;
import lt.insoft.webdriver.runner.model.TestItem;
import lt.insoft.webdriver.runner.model.ThreadId;
import lt.insoft.webdriver.runner.util.ConcurrentAllureRunListener;
import lt.insoft.webdriver.runner.util.RunnerUtils;
import lt.insoft.webdriver.testCase.WebDriverTestCase;
import ru.yandex.qatools.allure.annotations.Title;

public class TestCallable implements Callable<Void> {
	private static final Log LOG = LogFactory.getLog(TestCallable.class);

	private int index;
	private int testsCount;
	private TestItem testItem;
	private ConcurrentAllureRunListener allureRunListener;
	private Boolean success = true;

	public TestCallable(int index, int testsCount, TestItem testItem, RunnerConfiguration config,
			ConcurrentAllureRunListener allureRunListener) {
		this.index = index;
		this.testsCount = testsCount;
		this.testItem = testItem;
		this.allureRunListener = allureRunListener;
	}

	@Override
	public Void call() throws Exception {

		synchronized (TestCallable.class) {
			LOG.info("========================================================================================");
			LOG.info(String.format("Starting test %s/%s %s(%s)", index, testsCount, testItem.getTitle(), getTitle()));
			LOG.info("========================================================================================");
		}

		if (WebDriverTestCase.class.isAssignableFrom(testItem.getTestClass())) {
			executeWebtesterTest();
		} else {
			executeSimpleTest();
		}

		synchronized (TestCallable.class) {
			LOG.info("========================================================================================");
			LOG.info(String.format("Finished %s/%s %s(%s) %s", index, testsCount, testItem.getTitle(), getTitle(),
					success ? "Success" : "Failed"));
			LOG.info("========================================================================================");
		}

		return null;
	}

	private void executeSimpleTest() throws InterruptedException {
		Description description = Description.createTestDescription(testItem.getTestClass(),
				testItem.getTestMethod().getName(), testItem.getTestMethod().getAnnotations());
		allureRunListener.testStarted(description);

		try {
			Object object = testItem.getTestClass().newInstance();
			testItem.getTestMethod().invoke(object, new Object[] {});
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			TestItem.setNoSuccess();
			allureRunListener.testFailure(new Failure(description, e.getCause()));
			success = false;
		}
		allureRunListener.testFinished(description);

	}

	private void executeWebtesterTest() {
		try (WebDriverTestCase testCase = (WebDriverTestCase) testItem.getTestClass().newInstance()) {

			Description description = Description.createTestDescription(testItem.getTestClass(),
					testItem.getTestMethod().getName(), testItem.getTestMethod().getAnnotations());
			allureRunListener.testStarted(description);

			testCase.setThreadId(ThreadId.get());

			RunnerUtils.invokeMultiple(testCase,
					RunnerUtils.getMethodsAnnotatedWith(testItem.getTestClass(), Before.class));

			try {
				testItem.getTestMethod().invoke(testCase, new Object[] {});
			}catch (TimeoutException e) {
				LOG.error("------------------------------------- TimeoutException");
				LOG.error(e.getMessage(), e);
				TestItem.setNoSuccess();
				try {
					testCase.getTester().screenshot();
				} catch (NoSuchSessionException e2) {
					LOG.error("Screenshot could not be taken, WebDriver instance already destroyed.");
				}
				allureRunListener.testFailure(new Failure(description, e.getCause()));
				success = false;
				
			}catch (Exception e) {
				LOG.error(e.getMessage(), e);
				TestItem.setNoSuccess();
				try {
					testCase.getTester().screenshot();
				} catch (NoSuchSessionException e2) {
					LOG.error("Screenshot could not be taken, WebDriver instance already destroyed.");
				}
				allureRunListener.testFailure(new Failure(description, e.getCause()));
				success = false;
			} 
			
			allureRunListener.testFinished(description);

			RunnerUtils.invokeMultiple(testCase,
					RunnerUtils.getMethodsAnnotatedWith(testItem.getTestClass(), After.class));

		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
		}
	}

	private String getTitle() {
		if (testItem.getTestMethod().getAnnotation(Title.class) != null) {
			return testItem.getTestMethod().getAnnotation(Title.class).value();
		} else
			return "";
	}


}