package com.test.scripts.testScripts;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

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
	public void t00() throws Exception {
		t.get("https://www.epolicija.lt");
		t.sleepMillis(200);
		t.clickNear("Pranešti apie įvykį", "*", 5);
		t.sleepMillis(200);
		t.clickNear("Radau ar pamečiau daiktą", 0, "*", 2, 3);
		t.sleepMillis(200);
		t.clickNear(" Pranešti neprisijungus ", "*", 3);
		t.sleepMillis(200);
		t.clickNear("Ne", 0, "*", 1, 3);
		t.sleepMillis(200);
		t.clickNear("Radau daiktą", 0, "*", 1, 3);
		t.sleepMillis(200);
		t.setTextNear("Ką radote?", 3, "esst");
		t.sleepMillis(200);
		t.setTextNear("eigu galite, nurodykite dace", 1, "*", 5, 3, "essrerreerrt");
	}
	
	@Test
	@Description("Checking text in testing article")
	public void t000() throws Exception {
		t.get("http://demowebshop.tricentis.com/");
		t.sleepMillis(200);
		t.setTextNear("Search store", 0, "laptop");
		t.clickNear("Search", "input", 5);
		t.clickNear("Register", "a", 5);
		t.clickNear("Male", "input", 5);
		t.setTextNear("First name:", 2, "laptop");
		t.setTextNear("Last name:", 2, "laptop");
		t.setTextNear("Email:", 0, "input", 0, 2, "laptop");
	}
	
	@Test
	@Description("Checking text in testing article")
	public void t01() throws Exception {
		t.get(Constants.DEFAULT_PORTAL_URL);
		t.setText("Enter text to search bar", TestScriptsPage.searchFieldBy, 5, "essay");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		t.click("Click Essay link", TestScriptsPage.essayLinkBy, 2);
		t.checkIfExists("Check if text exists", TestScriptsPage.essayTextBy, 2);
	}

	@Test
	@Description("Count how many references are in Math article")
	public void t02() throws Exception {
		int expectedCount = 72;
		t.get(Constants.DEFAULT_PORTAL_URL);
		t.setText("Enter text to search bar", TestScriptsPage.searchFieldBy, 5, "maths");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		int count = t.findAll("Find all references", TestScriptsPage.referencesCountBy, 2).size();
		Assert.assertEquals("Count of references changed", expectedCount, count);
	}
	
	@Test
	@Description("Example of failing text find: Checking text in testing article")
	public void t03() throws Exception {
		t.get(Constants.DEFAULT_PORTAL_URL);
		t.setText("Enter text to search bar", TestScriptsPage.searchFieldBy, 5, "essasy");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		t.click("Click Essay link", TestScriptsPage.essayLinkBy, 2);
		t.checkIfExists("Check if text exists", TestScriptsPage.failingTextBy, 2);
	}

	@Test
	@Description("Example of failing assert: Count how many references are in Math article")
	public void t04() throws Exception {
		int expectedCount = 70;
		t.get(Constants.DEFAULT_PORTAL_URL);
		t.setText("Enter text to search bar", TestScriptsPage.searchFieldBy, 5, "maths");
		t.click("Click search button", TestScriptsPage.searchButtonBy, 1);
		int count = t.findAll("Find all references", TestScriptsPage.referencesCountBy, 2).size();
		Assert.assertEquals("Count of references changed", expectedCount, count);
	}

}
