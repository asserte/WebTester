package com.test.scripts.testScripts;

import org.openqa.selenium.By;

import com.test.commons.Utils;

public class TestScriptsPage extends Utils {

	public By agreeButton = By.xpath("//span[text()='Sutinku']");
	public By searchButton = By.xpath("//input[@title='Ieškoti']");
	public By wuLink = By.xpath("//span[contains(text(),'Western Union Lietuva')]");
}
