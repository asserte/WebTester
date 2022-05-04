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
	@Description("Checking text in testing article")
	public void t01() throws Exception {
		t.get("https://en.wikipedia.org/");
		t.setText("Enter text to search bar",TestScriptsPage.searchFieldBy, 5, "testing");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		t.click("Click Essay link", TestScriptsPage.essayLinkBy, 2);
		t.checkIfExists("Check if text exists", TestScriptsPage.essayTextBy, 2);
	}
	
	@Test
	@Description("Checking text in math article")
	public void t02() throws Exception {
		t.get("https://en.wikipedia.org/");
		t.setText("Enter text to search bar",TestScriptsPage.searchFieldBy, 5, "maths");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
//		t.click("Click Essay link", TestScriptsPage.essayLinkBy, 2);
//		t.checkIfExists("Check if text exists", TestScriptsPage.essayTextBy, 2);
	}

}
