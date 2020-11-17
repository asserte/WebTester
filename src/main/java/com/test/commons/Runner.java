package com.test.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * Class for running test script engine.
 * 
 *
 */

@SpringBootApplication(scanBasePackages = { "lt.insoft.webdriver.runner" })
public class Runner {

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
