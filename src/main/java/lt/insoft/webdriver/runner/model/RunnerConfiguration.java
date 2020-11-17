package lt.insoft.webdriver.runner.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "autotests.runner")
public class RunnerConfiguration {

	private String[] tests;
	private String itemPackage;
	private String listerOutFile;


	public String[] getTests() {
		return tests;
	}

	public void setTests(String[] tests) {
		this.tests = tests;
	}

	public String getTestsPackage() {
		return itemPackage;
	}

	public void setItemPackage(String itemPackage) {
		this.itemPackage = itemPackage;
	}

	public String getListerOutFile() {
		return listerOutFile;
	}

	public void setListerOutFile(String listerOutFile) {
		this.listerOutFile = listerOutFile;
	}




}
