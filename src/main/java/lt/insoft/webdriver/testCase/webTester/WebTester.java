package lt.insoft.webdriver.testCase.webTester;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.commons.Constants;

import lt.insoft.webdriver.testCase.utils.Highlighters;

/**
 * @author c644721 Dovilas This class adds extra features to the existing
 *         webdriver find, click, setText, etc, functionalities.
 */
public class WebTester extends WebTesterBase {

	private void waitToBeVisible(By by, long timeOutInSeconds) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutInSeconds));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (TimeoutException e) {
			System.out.println("Element " + by.toString() + " failed to become visible");
			throw e;
		}
	}

	private WebElement findNonStaleElement(By by) throws Exception {
		WebElement foundElement = null;
		int count = 0;
		int maxTries = 5;
		while (true) {
			try {
				foundElement = driver.findElement(by);
				return foundElement;
			} catch (StaleElementReferenceException ex) {
				sleepMillis(500);
				if (++count == maxTries)
					throw new StaleElementReferenceException("Too many stale element retries");
			}
		}
	}

	public WebElement find(By by, int timeToWait) throws Exception {
		waitToBeVisible(by, timeToWait);
		try {
			WebElement wb = Highlighters.highlightGreen(driver, findNonStaleElement(by));
			scrollToWebElement(wb);
			System.out.println("Find " + by.toString());
			return wb;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("window " + driver.getTitle() + " " + e.getMessage(), e);
		}
	}

	public void failIfFound(By by, int timeToWait) throws Exception {
		try {
			find(by, timeToWait);
			Assert.fail("Element shoud not be found: " + by);
		} catch (NoSuchElementException ne) {
		} catch (TimeoutException te) {
		}
	}

	public void waitForElementNotToBeVisible(By by, int timeToWait) throws Exception {
//		int currentTimer = 0;
//		implicitWait(1);
//		while (currentTimer < timeToWait) {
//			currentTimer++;
//			Boolean isSeen = false;
//			try {
//				isSeen = driver.findElement(by).isDisplayed();
//			} catch (Exception e) {
//				isSeen = false;
//			}
//			if (isSeen) {
//				sleep(1);
//			} else
//				break;
//
//			implicitWait();
//		}
// this breaks in multithread
		new WebDriverWait(driver, Duration.ofSeconds(timeToWait)).until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	public byte[] click(By by, int timeToWait) throws Exception {
		System.out.println("Click " + by);
		WebElement wb = Highlighters.highlightRed(driver, find(by, timeToWait));
		scrollToWebElement(wb);
		byte[] screen = screenshot();
		wb.click();
		return screen;
	}

	public void assertUrl(String expectedUrl) throws Exception {
		String currentUrl = getDriver().getCurrentUrl();
		Assert.assertTrue("Current url '" + currentUrl + "' doesn't contain '" + expectedUrl + "' fragment",
				currentUrl.contains(expectedUrl));
	}

	public void setText(By by, int timeToWait, CharSequence... value) throws Exception {
		System.out.println("Click " + by);
		WebElement wb = Highlighters.highlightBlue(driver, find(by, timeToWait));
		Assert.assertTrue(by + " input should be enabled.", wb.isEnabled());
		Assert.assertTrue(by + " input should be displayed.", wb.isDisplayed());
		String existing = wb.getAttribute("value");
		if (!"".equals(existing)) {
			wb.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
		}
		wb.sendKeys(value);
	}

	private void scrollToWebElement(WebElement element) throws Exception {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
	}

	public void get(String url) {
		driver.get(url);
	}

	public void switchToLastTab() throws Exception {
		ArrayList<String> childtab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(childtab.get(childtab.size() - 1));
	}

	public void closeLastTab() throws Exception {
		ArrayList<String> childtab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(childtab.get(childtab.size() - 1)).close();
		driver.switchTo().window(childtab.get(0));
	}

	public void switchToFrame(By by, int timeToWait) throws Exception {
		driver.switchTo().defaultContent();
		WebElement wb = find(by, timeToWait);
		driver.switchTo().frame(wb);
	}

	public void switchToFrame(WebElement frame) throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frame);
	}

	public void switchToFrame(Integer frame) throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frame);
	}

	public void switchToDefaultContent() throws Exception {
		driver.switchTo().defaultContent();
	}

	public byte[] screenshot() throws IOException {
		if (Constants.TAKE_SCREENSHOT) {
			if (driver instanceof TakesScreenshot) {
				return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			} else {
				return new byte[] {};
			}
		} else {
			return null;
		}
	}

}