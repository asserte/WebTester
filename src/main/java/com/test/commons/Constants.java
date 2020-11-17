package com.test.commons;


/**
 * @author c644721 Dovilas
 *
 */
public class Constants {
//	VM argument set properties
	public static final String CONTRACTOR_ID = System.getProperty("script.contractor.id");
	public static final String CONTRACTOR_PASSWORD = System.getProperty("script.contractor.password");
	
// Property file set properties																 
	public static final String USER_PASSWORD = Configurations.getProperty("script.user.password");
	public static final String DEFAULT_PORTAL_URL = Configurations.getProperty("script.default.portal.url");
	public static final int DEFAULT_IMPLICIT_WAIT = Integer.parseInt(Configurations.getProperty("driver.default.implicit.wait"));
	public static final int DEFAULT_PAGE_LOAD_WAIT = Integer.parseInt(Configurations.getProperty("driver.default.page.load.wait"));
	public static final String BROWSER = Configurations.getProperty("driver.browser");
	public static final boolean TAKE_SCREENSHOT = Boolean.parseBoolean(Configurations.getProperty("driver.take.screenshot"));
	public static final String PROXY_HOST = Configurations.getProperty("driver.proxy.host");
	public static final String PROXY_PORT = Configurations.getProperty("driver.proxy.port");
	
	public static final boolean REUSE_BROWSER_SESSION = Boolean.parseBoolean(Configurations.getProperty("runner.reuse.session"));
	public static final String REPORT_FOLDER = Configurations.getProperty("runner.report.folder");
	public static final int THREAD_COUNT = Integer.parseInt(Configurations.getProperty("runner.thread.count"));

	// Constants
}
