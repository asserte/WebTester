package com.test.scripts.testScripts;

import org.junit.Test;
import org.openqa.selenium.Keys;

import lt.insoft.webdriver.testCase.TestCase;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

@Title("Testing")
public class TestScripts extends TestCase {

	TestScriptsUtils accumsAndCopayUtils = null;
	TestScriptsPage testScriptsPage = null;

	// @Before
	public void before() throws Exception {
		super.before();
		accumsAndCopayUtils = new TestScriptsUtils(t);
		testScriptsPage = new TestScriptsPage();
	}


	@Test
	@Description("Detailed description")
	public void t01() throws Exception {
		t.get("https://www.google.com");
		t.switchToFrame(0);
		t.click("Error example", testScriptsPage.agreeButton, 5);
		t.switchToDefaultContent();
		t.setText(testScriptsPage.searchButton, 2, "WU", Keys.ENTER);
		t.click("Click wu link", testScriptsPage.wuLink, 2);
		t.assertUrl("westernunion");
	}

}
