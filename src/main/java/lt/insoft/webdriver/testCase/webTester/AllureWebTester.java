package lt.insoft.webdriver.testCase.webTester;

import java.util.List;

import org.openqa.selenium.By;
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
	@Attachment
	public void setText(String stepText, By by, int timeToWait, CharSequence... value) throws Exception {
		try {
			setText(by, timeToWait, value);
		} catch (Exception e) {
			throw e;
		} finally {
			// t.screenshot();
		}
	}
	
//TODO remove after testing	
	@Step("Testing the allure step message")
	public void allureStepTestMethod() throws Exception {
		System.out.println("test");
	}
	
	@Step("{0}")
	public void allureStepTestMethod(String test) throws Exception {
		System.out.println("test");
	}

	/**
	 * Method used to envelope methods in order to add an Allure step over them.
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