package lt.insoft.webdriver.testCase.webTester;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import io.qameta.allure.Step;
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
			LOG.info("Element " + by + " failed to become visible");
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
	
	private List<WebElement> findNonStaleElements(By by) throws Exception {
		List<WebElement> foundElements = null;
		int count = 0;
		int maxTries = 5;
		while (true) {
			try {
				foundElements = driver.findElements(by);
				return foundElements;
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
			LOG.info("Find " + by);
			return wb;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("window " + driver.getTitle() + " " + e.getMessage(), e);
		}
	}
	
	
	public List<WebElement> findElements(By by, int timeToWait) throws Exception {
		waitToBeVisible(by, timeToWait);
		try {
			List<WebElement> wb = Highlighters.highlightGreen(driver, findNonStaleElements(by));
			scrollToWebElement(wb.get(0));
			LOG.info("Find " + by);
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

	@Step
	public byte[] click(By by, int timeToWait) throws Exception {
		LOG.info("Click " + by);
		WebElement wb = Highlighters.highlightRed(driver, find(by, timeToWait));
		scrollToWebElement(wb);
		byte[] screen = screenshot();
		wb.click();
		return screen;
	}

	@Step
	public void setText(By by, int timeToWait, CharSequence... value) throws Exception {
		LOG.info("Set Text " + by);
		WebElement wb = Highlighters.highlightRed(driver, find(by, timeToWait));
		Assert.assertTrue(by + " input should be enabled.", wb.isEnabled());
		Assert.assertTrue(by + " input should be displayed.", wb.isDisplayed());
		String existing = wb.getAttribute("value");
		if (!"".equals(existing)) {
			wb.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
		}
		wb.sendKeys(value);
	}

	protected void scrollToWebElement(WebElement element) throws Exception {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	@Step
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
	

	
	
	

	@Step
	public WebElement findNear(String referenceLabelOrPath, int referenceIndex, String targetElementXpath, int targetIndex, int timeToWait) throws Exception {
		LOG.info("Find " + targetElementXpath + "[" + targetIndex + "] near " + referenceLabelOrPath + "[" + referenceIndex + "]");
		int level = 0;
		WebElement reference = find(referenceLabelOrPath, referenceIndex, timeToWait);
		WebElement descendant = null;
		do {
			try {
				descendant = reference.findElements(By.xpath("./descendant::" + targetElementXpath)).get(targetIndex);
			} catch (IndexOutOfBoundsException e) {
			}
			reference = reference.findElement(By.xpath("./parent::*"));
			if (level++ > 10 || "html".equalsIgnoreCase(reference.getTagName())) {
				throw new NoSuchElementException("WebElement not found with reference label or xpath: '" + referenceLabelOrPath + "'[" + referenceIndex + "] and target '" + targetElementXpath + "'[" + targetIndex + "]");
			}
		} while (descendant == null);
		return Highlighters.highlightRed(driver, descendant);
	}
	
	
	@Step
	public WebElement find(String labelOrPath, int index, int timeToWait) throws Exception {
		String builtXpath = isXpath(labelOrPath) ? labelOrPath : buildLabelXpath("*", labelOrPath);
		builtXpath = builtXpath.replaceFirst("//", "./descendant::");
		By by = By.xpath(builtXpath);
		try {
			WebElement we = Highlighters.highlightBlue(driver, findVisibleElements(by, timeToWait).get(index));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
			return we;
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException("window " + driver.getTitle() + " " + by.toString(), e);
		}
	}
	
	
	@Step
	private List<WebElement> findVisibleElements(By by, int timeToWait) throws Exception {
		List<WebElement> visibleElements = new ArrayList<WebElement>();
		List<WebElement> foundElements = findElements(by, timeToWait);
		for (WebElement e : foundElements) {
			int attempts = 0;
			while (attempts < 5) {
				try {
					if (e.isDisplayed()) {
						visibleElements.add(e);
					}
					break;
				} catch (StaleElementReferenceException e2) {
				}
				attempts++;
			}
		}
		return visibleElements;
	}
	
	
//	public WebElement findVisibleElement(By by, int timeToWait) throws Exception {
//		List<WebElement> visibleElements = findVisibleElements(by, timeToWait);
//		if (!visibleElements.isEmpty()) {
//			return visibleElements.get(0);
//		} else {
//			throw new NoSuchElementException("Visible element not found by using by: " + by.toString());
//		}
//	}
	
	private boolean isXpath(String labelOrPath) {
		return StringUtils.containsAny(labelOrPath, "[=@")
				|| (labelOrPath != null && (labelOrPath.startsWith("//")) || labelOrPath.startsWith("./"));
	}
	
	private String buildLabelXpath(String tag, String label) {
		return "//" + tag + "[contains(text(),'" + label + "') or contains(@title,'" + label
				+ "') or contains(@value,'" + label + "')]";
	}

}