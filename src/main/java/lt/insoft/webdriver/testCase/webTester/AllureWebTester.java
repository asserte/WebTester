package lt.insoft.webdriver.testCase.webTester;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.commons.Action;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * @author c644721 Dovilas
 * This class adds Custom Allure step methods to the existing WebTester methods.
 */
public class AllureWebTester extends WebTester{


	/**
	 * Main click method for clicking elements by using By strategy. Every instance
	 * of method will take screen shot before the click. String parameter is only
	 * used for step text in the report. Throws exception and stops test case.
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
	 * Main existence check for finding elements by using By strategy. Every
	 * instance of method will make a screen shot. Throws exception and stops test
	 * case.
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


	@Step("{0}")
	public void checkIfDoesNotExist(String stepText, By by, int timeToWait) throws Exception {
		failIfFound(by, timeToWait);
	}

	
	/**
	 * Main click method for clicking elements by using By strategy. Every instance
	 * of method will take screen shot before the click. String parameter is only
	 * used for step text in the report. Throws exception and stops test case.
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

}