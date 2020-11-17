package lt.insoft.webdriver.testCase;

import org.junit.Before;

import com.test.commons.Constants;

import lt.insoft.webdriver.testCase.webTester.AllureWebTester;

/**
 * 
 * Class for a test case run. Module specific methods should be placed here.
 *
 */
public class TestCase extends WebDriverTestCase {
	
	protected AllureWebTester t = null;

	@Before
	public void before() throws Exception {
		t.init(Constants.BROWSER);
	}

	public TestCase() {
		setTester(new AllureWebTester());
	}

	public void setTester(AllureWebTester t) {
		this.t = t;
	}

	@Override
	public void close() throws Exception {
		t.destroy();
	}
	
	
	


	
}
