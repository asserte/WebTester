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
		t.get("https://en.wikipedia.org/");
		t.setText(TestScriptsPage.searchFieldBy, 5, "testing");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		t.click("Click Essay link ", TestScriptsPage.essayLinkBy, 2);
		t.find(TestScriptsPage.essayTextBy, 2);
	}

}
