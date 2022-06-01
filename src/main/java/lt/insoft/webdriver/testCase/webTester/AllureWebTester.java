package lt.insoft.webdriver.testCase.webTester;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.test.commons.Action;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

/**
 * @author c644721 Dovilas This class adds Custom Allure step methods to the
 *         existing WebTester methods.
 */
public class AllureWebTester extends WebTester {

	/**
	 * Main click method for clicking elements by using By strategy. Every instance
	 * of method will take screen shot before the click. String parameter is only
	 * used for step text in the report. Throws exception and stops test case.
	 * 
	 * @param stepText   Allure step text
	 * @param by         By type variable by which found element would be clicked
	 * @param timeToWait time after which timeOutException will be thrown
	 * @throws Exception* @throws Exception
	 */
	@Step("{0}")
	@Attachment
	public byte[] click(String stepText, By by, int timeToWait) throws Exception {
		try {
			byte[] screen = click(by, timeToWait);
			return screen;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Step("Click [{2}][{3}] near [{0}][{1}]")
	public byte[] clickNear(String referenceLabelOrPath, int referenceIndex, String targetElementXpath, int targetIndex, int timeToWait) throws Exception {
		LOG.info("Click " + targetElementXpath + "[" + targetIndex + "] near " + referenceLabelOrPath + "[" + referenceIndex + "]");
		WebElement wb = findNear(referenceLabelOrPath, referenceIndex, targetElementXpath, targetIndex, timeToWait);
		scrollToWebElement(wb);
		byte[] screen = screenshot();
		wb.click();
		return screen;
	}
	
	@Step("Click [{1}][0] near [{0}][0]")
	public byte[] clickNear(String referenceLabelOrPath, String targetElementXpath, int timeToWait) throws Exception {
		return clickNear(referenceLabelOrPath, 0, targetElementXpath, 0, timeToWait);
	}
	
	@Step("Set [{2}][{3}] near [{0}][{1}]")
	public void setTextNear(String referenceLabelOrPath, int referenceIndex, String targetElementXpath, int targetIndex, int timeToWait, CharSequence... keysToSend) throws Exception {
		LOG.info("Set Text " + targetElementXpath + "[" + targetIndex + "] near " + referenceLabelOrPath + "[" + referenceIndex + "]");
		WebElement wb = findNear(referenceLabelOrPath, referenceIndex, targetElementXpath, targetIndex, timeToWait);
		Assert.assertTrue(wb + " input should be enabled.", wb.isEnabled());
		Assert.assertTrue(wb + " input should be displayed.", wb.isDisplayed());
		String existing = wb.getAttribute("value");
		if (!"".equals(existing)) {
			wb.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
		}
		wb.sendKeys(keysToSend);
	}
	
	@Step("Set [{2}][{3}] near [{0}][{1}]")
	public void setTextNear(String referenceLabelOrPath, String targetElementXpath, int timeToWait, CharSequence... keysToSend) throws Exception {
		setTextNear(referenceLabelOrPath, 0, targetElementXpath, 0, timeToWait, keysToSend);
	}
	
	@Step("Set [input][0] near [{0}][0]")
	public void setTextNear(String referenceLabelOrPath, int timeToWait, CharSequence... keysToSend) throws Exception {
		setTextNear(referenceLabelOrPath, 0, "input", 0, timeToWait, keysToSend);
	}

	/**
	 * Main existence check for finding elements by using By strategy. Throws
	 * exception and stops test case.
	 * 
	 * @param stepText   Allure step text
	 * @param by         By type variable by which element would be found
	 * @param timeToWait time after which timeOutException will be thrown
	 * @throws Exception* @throws Exception
	 */
	@Step("{0}")
	public WebElement checkIfExists(String stepText, By by, int timeToWait) throws Exception {
		try {
			return find(by, timeToWait);
		} catch (Exception e) {
			throw e;
		} finally {
			// t.screenshot();
		}
	}

	/**
	 * Main method for returning list of web elements by using By strategy. Throws
	 * exception and stops test case.
	 * 
	 * @param stepText   Allure step text
	 * @param by         By type variable by which list of elements would be found
	 * @param timeToWait time after which timeOutException will be thrown
	 * @throws Exception
	 */
	@Step("{0}")
	public List<WebElement> findAll(String stepText, By by, int timeToWait) throws Exception {
		try {
			return findElements(by, timeToWait);
		} catch (Exception e) {
			throw e;
		} finally {
			// t.screenshot();
		}
	}

	/**
	 * Main text entering method for elements by using By strategy. String parameter
	 * is only used for step text in the report. Throws exception and stops test
	 * case.
	 * 
	 * @param stepText   Allure step text
	 * @param by         By type variable by which element would be found
	 * @param timeToWait time after which timeOutException will be thrown
	 * @param value      value to be entered into found element
	 * @throws Exception
	 */
	@Step("{0}")
//	@Attachment
	public void setText(String stepText, By by, int timeToWait, CharSequence... value) throws Exception {
		try {
			setText(by, timeToWait, value);
		} catch (Exception e) {
			throw e;
		} finally {
			// t.screenshot();
		}
	}
	
	/**
	 * Method used to envelope methods in order to add an Allure step over them, it is a way to force the step functionality.
	 * 
	 * @param stepDescription Allure step description
	 * @param action          one or multiple methods provided by using lambda
	 *                        notation () -> anyMethod(String text);
	 * @throws Exception
	 */
	@Step("{0}")
	public void step(String stepDescription, Action action) throws Exception {
		action.doAction();
	}

	/**
	 * Main check for element not existing by using By strategy. Throws exception
	 * and stops test case.
	 * 
	 * @param stepText Allure step text
	 * @param by       By type variable by which element should not be found
	 * @throws Exception
	 */
	@Step("{0}")
	public void checkIfDoesNotExist(String stepText, By by, int timeToWait) throws Exception {
		failIfFound(by, timeToWait);
	}

}