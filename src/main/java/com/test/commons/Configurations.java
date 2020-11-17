package com.test.commons;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

public final class Configurations {

	private Properties properties = null;
	private static Configurations instance = null;
	private static String propertyFile = System.getProperty("property.file", "app.sit_properties");
	private static String masterPassword = System.getProperty("master");
	
	public static void main(String[] args) {
		encrypt("string to encrypt");
	}
	
	private static void encrypt(String strToEncrypt) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(masterPassword);
		System.out.println(encryptor.encrypt(strToEncrypt));
	}

	/** Private constructor 
	 * @throws IOException */
	Configurations()  {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();     
		encryptor.setPassword(masterPassword);
		properties = new EncryptableProperties(encryptor);
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile));
			System.out.println("---------------------------- Loaded " + propertyFile + " file");
		} catch (IOException e) {
			System.out.println("Property file " + propertyFile + " could not be read");
			e.printStackTrace();
		} 
	}

	/** Creates the instance is synchronized to avoid multithreads problems 
	 * @throws IOException */
	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new Configurations();
		}
	}

	/** Get the properties instance. Uses singleton pattern 
	 * @throws IOException */
	private static Configurations getInstance() {
		// Uses singleton pattern to guarantee the creation of only one instance
		if (instance == null) {
			createInstance();
		}
		return instance;
	}
	
	public static String getProperty(String key) {
		return System.getProperty(key, Configurations.getPropertyLocal(key));
	}

	/** Get a property of the property file 
	 * @throws IOException */
	public static String getPropertyLocal(String key) {
		String result = null;
		if (key != null && !key.trim().isEmpty()) {
			result = getInstance().properties.getProperty(key);
		}
		if (result != null) {
			return result;
		} else {
			throw new InvalidParameterException("Parameter '" + key + "' is not present in the property file '" + propertyFile + "'. Check for mistypes.");
		}
	}

	/**
	 * Override the clone method to ensure the "unique instance" requirement of this
	 * class
	 */
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
