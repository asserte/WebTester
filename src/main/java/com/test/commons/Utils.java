package com.test.commons;

import lt.insoft.webdriver.testCase.webTester.AllureWebTester;

public class Utils {

	protected AllureWebTester t = null;
	
//	Go to Window → Preferences → Java → Installed JREs. Select the JRE you're using, click Edit, and there will be a line for Default VM Arguments which will apply to every execution.
//	-Dscript.user.password=<password>. If multiple variables need to be provided, use space as separator.
	public String getPassword() throws Exception {
		return Constants.USER_PASSWORD;
	}
}
