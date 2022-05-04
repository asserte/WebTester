package lt.insoft.webdriver.testCase.webTester;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.test.commons.Constants;

import io.qameta.allure.Attachment;
import lt.insoft.webdriver.testCase.utils.DriverCreator;

/**
 * Class for webdriver initiation. </br>
 */
public abstract class WebTesterBase {
	protected WebDriver driver = null;
	private DriverCreator driverCreator = new DriverCreator();
	protected static final Log LOG = LogFactory.getLog(WebTesterBase.class);

	/**
	 * Variable, how long webdriver should search for element.
	 */
	private int implicitWait = Constants.DEFAULT_IMPLICIT_WAIT;
	private int pageLoadWaitDefault = Constants.DEFAULT_PAGE_LOAD_WAIT;

	public void init(String browser) throws Exception {
		driver = driverCreator.createDriver(browser);
		implicitWait(implicitWait);
		pageLoadTimeout(pageLoadWaitDefault);
	}

	/**
	 * Method returns time how long webdriver will search for element before throwing
	 * NoSuchElementException.
	 */
	public int getLatestImplicitWait() {
		return implicitWait;
	}

	/**
	 * Method sets time how long webdriver will search for element before throwing
	 * NoSuchElementException. By default this value is 30s or how much is set
	 * in defaultImplicitWait parameter.
	 */
	public void implicitWait(int implicitWait) {
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
	}

	/**
	 * Method sets time how long webdriver will search for element before throwing
	 * NoSuchElementException. Time is set by last
	 * {@link #implicitWait(int)} input value.
	 */
	public void implicitWait() {
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
	}

	public void pageLoadTimeout(int sec) {
		driver.manage().timeouts().pageLoadTimeout(sec, TimeUnit.SECONDS);
	}
	

	public void sleep(long sec) throws Exception {
		sleep(sec, TimeUnit.SECONDS);
	}

	public void sleepMillis(long millis) throws Exception {
		sleep(millis, TimeUnit.MILLISECONDS);
	}

	private void sleep(long amount, TimeUnit tu) throws Exception {
		long millis = tu.toMillis(amount);
		if (millis > 999) {
			LOG.info("sleep " + amount + " " + tu.toString());
		}
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public void destroy() throws IOException {
		try {
//			WindowsUtils.killByName("chrome.exe");
			driver.quit();
		} catch (NullPointerException e) {
			LOG.warn("Could not destroy driver.");
		}
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	@Attachment
	public byte[] screenshot() throws IOException {
		if (driver instanceof TakesScreenshot) {
			return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		} else {
			return new byte[] {};
		}
	}
	

	public File getResource(String resourceName) throws IOException {
		if (resourceName.equals("")) {
			return null;
		}
		File file = new File(System.getProperty("java.io.tmpdir") + resourceName);
		if (!file.exists()) {
			FileOutputStream fos = new FileOutputStream(file, false);
			try {
				IOUtils.copy(WebTesterBase.class.getClassLoader().getResourceAsStream(resourceName), fos);
			} finally {
				IOUtils.closeQuietly(fos);
			}
		}
		return file;
	}

}