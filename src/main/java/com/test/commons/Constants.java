package com.test.commons;


/**
 * @author c644721 Dovilas
 *
 */
public class Constants {
	
// Property file set properties																 
	public static final String USER_PASSWORD = ConstantConfigurations.getProperty("script.user.password");
	public static final String DEFAULT_PORTAL_URL = ConstantConfigurations.getProperty("script.default.portal.url");
	public static final int DEFAULT_IMPLICIT_WAIT = Integer.parseInt(ConstantConfigurations.getProperty("driver.default.implicit.wait"));
	public static final int DEFAULT_PAGE_LOAD_WAIT = Integer.parseInt(ConstantConfigurations.getProperty("driver.default.page.load.wait"));
	public static final String BROWSER = ConstantConfigurations.getProperty("driver.browser");
	public static final boolean TAKE_SCREENSHOT = Boolean.parseBoolean(ConstantConfigurations.getProperty("driver.take.screenshot"));
	public static final String PROXY_HOST = ConstantConfigurations.getProperty("driver.proxy.host");
	public static final String PROXY_PORT = ConstantConfigurations.getProperty("driver.proxy.port");
	
	public static final String REPORT_FOLDER = ConstantConfigurations.getProperty("runner.report.folder");
	public static final int THREAD_COUNT = Integer.parseInt(ConstantConfigurations.getProperty("runner.thread.count"));
	public static final boolean REUSE_BROWSER_SESSION = Boolean.parseBoolean(ConstantConfigurations.getProperty("runner.reuse.session"));

	// Constants
}
