package com.test.scripts.testScripts;

import org.openqa.selenium.By;

import com.test.commons.Utils;

public class TestScriptsPage extends Utils {

	public static By searchFieldBy = By.id("searchInput");
	public static By searchButtonBy = By.id("searchButton");
	public static By essayLinkBy = By.xpath("//span[text()='Essay']");
	public static By essayTextBy = By.xpath("//p[contains(text(), 'Items such as short answer or essay typically require a test taker to write a response to fulfill the requirements of the item.')]");
	public static By referencesCountBy = By.xpath("//div[contains(@class, 'reflist-columns')]//li");
}
