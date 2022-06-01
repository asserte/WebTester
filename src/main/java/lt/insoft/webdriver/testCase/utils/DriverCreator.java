package lt.insoft.webdriver.testCase.utils;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.test.commons.Constants;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverCreator {

	//public static final String downloadDirecory = "C:\\tmp";
	public static final String CHROME = "chrome";
	public static final String EDGE = "edge";
	public static final String EXPLORER = "explorer";
	public static final String FIREFOX = "firefox";
	
	public WebDriver createDriver(String browser) throws Exception {
		String browserString = browser.toLowerCase().trim();
		if (browserString.contains(CHROME)) {
			return chromeDriverSetup();
		} else if (browserString.contains(EDGE)) {
			return edgeDriverSetup();
//		} else if (browserString.contains(EXPLORER)) {
//			return explorerDriverSetup();
		} else if (browserString.contains(FIREFOX)) {
			return firefoxDriverSetup();
		} else {
			return null;
		}
	}
	
	public static void setProxies() {
		System.setProperty("http.proxyHost", Constants.PROXY_HOST);
		System.setProperty("http.proxyPort", Constants.PROXY_PORT);
		System.setProperty("https.proxyHost", Constants.PROXY_HOST);
		System.setProperty("https.proxyPort", Constants.PROXY_PORT);
		System.setProperty("socks.proxyHost", Constants.PROXY_HOST);
		System.setProperty("socks.proxyPort", Constants.PROXY_PORT);
		System.setProperty("PROXY_HOST", Constants.PROXY_HOST);
		System.setProperty("PROXY_PORT", Constants.PROXY_PORT);
	}
	
	private WebDriver chromeDriverSetup() throws IOException, URISyntaxException {
	//	setProxies();
		WebDriverManager.chromedriver().clearResolutionCache();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-features=VizDisplayCompositor");
		options.addArguments("disable-plugins");
		options.addArguments("disable-extensions");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-gpu");
//		options.addArguments("--window-size=1920x1080");
//		options.addArguments("--headless");
		return new ChromeDriver(options);
	}

	private WebDriver firefoxDriverSetup() throws IOException, URISyntaxException {
		setProxies();
		WebDriverManager.firefoxdriver().clearResolutionCache();
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver edgeDriverSetup() throws IOException, URISyntaxException {
		setProxies();
		WebDriverManager.edgedriver().clearResolutionCache();
		WebDriverManager.edgedriver().setup();
		return new EdgeDriver();
	}
	
	/*
	 * @SuppressWarnings("deprecation") private WebDriver explorerDriverSetup()
	 * throws IOException, URISyntaxException { setProxies();
	 * WebDriverManager.iedriver().clearPreferences();
	 * WebDriverManager.iedriver().setup(); DesiredCapabilities capabilities =
	 * DesiredCapabilities.internetExplorer();
	 * capabilities.setCapability(InternetExplorerDriver.
	 * INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	 * capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	 * capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);
	 * capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,
	 * true);
	 * capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,
	 * true); capabilities.setCapability("disable-popup-blocking", true); return new
	 * InternetExplorerDriver(capabilities); }
	 */


}
