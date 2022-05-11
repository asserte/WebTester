package com.test.scripts.testScripts;

import org.junit.Assert;
import org.junit.Test;

import com.test.commons.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import lt.insoft.webdriver.testCase.TestCase;

@Story("Testing")
public class TestScripts extends TestCase {

	TestScriptsUtils scriptUtils = null;
	TestScriptsPage testScriptsPage = null;

	// @Before
	public void before() throws Exception {
		super.before();
		scriptUtils = new TestScriptsUtils(t);
		testScriptsPage = new TestScriptsPage();
	}

	@Test
	@Description("Checking text in testing article")
	public void t01() throws Exception {
		t.get(Constants.DEFAULT_PORTAL_URL);
		t.setText("Enter text to search bar", TestScriptsPage.searchFieldBy, 5, "testing");
		t.allureStepTestMethod();
		t.allureStepTestMethod("Test test te");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		t.click("Click Essay link", TestScriptsPage.essayLinkBy, 2);
		t.checkIfExists("Check if text exists", TestScriptsPage.essayTextBy, 2);
	}

	@Test
	@Description("Count how many references are in Math article")
	public void t02() throws Exception {
		int expectedCount = 70;
		t.get(Constants.DEFAULT_PORTAL_URL);
		t.setText("Enter text to search bar", TestScriptsPage.searchFieldBy, 5, "maths");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		int count = t.findAll("Find all references", TestScriptsPage.referencesCountBy, 2).size();
		Assert.assertEquals("Count of references changed", expectedCount, count);
	}
	
	@Test
	@Description("Checking text in testing article")
	public void t03() throws Exception {
		t.get(Constants.DEFAULT_PORTAL_URL);
		t.setText("Enter text to search bar", TestScriptsPage.searchFieldBy, 5, "testing");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		t.click("Click Essay link", TestScriptsPage.essayLinkBy, 2);
		t.checkIfExists("Check if text exists", TestScriptsPage.essayTextBy, 2);
	}

	@Test
	@Description("Count how many references are in Math article")
	public void t04() throws Exception {
		int expectedCount = 70;
		t.get(Constants.DEFAULT_PORTAL_URL);
		t.setText("Enter text to search bar", TestScriptsPage.searchFieldBy, 5, "maths");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		int count = t.findAll("Find all references", TestScriptsPage.referencesCountBy, 2).size();
		Assert.assertEquals("Count of references changed", expectedCount, count);
	}

}
