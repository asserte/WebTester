package lt.insoft.webdriver.testCase;

import org.junit.After;

import lt.insoft.webdriver.testCase.webTester.WebTester;
import lt.insoft.webdriver.testCase.webTester.WebTesterBase;

public class WebDriverTestCase implements AutoCloseable {
	protected WebTester t = null;
	protected int threadId;

	@After
	public void after() throws Exception {
	}

	public WebDriverTestCase() {
		setTester(new WebTester());
	}

	public WebTesterBase getTester() {
		return t;
	}

	public void setTester(WebTester t) {
		this.t = t;
	}

	@Override
	public void close() throws Exception {
		t.destroy();
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public int getThreadId() {
		return threadId;
	}

}