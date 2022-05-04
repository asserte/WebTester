package com.test.commons;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.EncryptableProperties;

public final class ConstantConfigurations {

	private Properties properties = null;
	private static ConstantConfigurations instance = null;
	private static String propertyFile = System.getProperty("property.file", "apps.properties");
	private static String masterPassword = System.getProperty("master"); 
	private static final Log LOG = LogFactory.getLog(ConstantConfigurations.class);
	
	public static void main(String[] args) {
		LOG.info(encrypt("string to encrypt"));
	}
	
	
	/**
	 * @param strToEncrypt
	 * 
	 * Reads a string value and returns an encrypted string  value by using a master key.
	 */
	private static String encrypt(String strToEncrypt) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(masterPassword);
		return encryptor.encrypt(strToEncrypt);
	}

	/** Private constructor 
	 * */
	ConstantConfigurations()  {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();   
		try {
			encryptor.setPassword(masterPassword);
		} catch (EncryptionInitializationException|ExceptionInInitializerError|IllegalArgumentException e) {
			LOG.error("Master password needs to be provided as a JDK parameter for scripts to run. The master password value is used to decrypt any encrypted parameters in the property file.");
			LOG.error("If Master Password value is not provided, encrypted parameters won't be read from property file, an empty value will be provided instead");
			e.printStackTrace();
		}
		properties = new EncryptableProperties(encryptor);
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile));
			LOG.info("---------------------------- Loaded " + propertyFile + " file");
		} catch (NullPointerException|IOException e) {
			LOG.error("Property file " + propertyFile + " could not be read");
			e.printStackTrace();
		} 
	}

	/** Creates the instance is synchronized to avoid multithreading problems 
	 * @throws IOException */
	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ConstantConfigurations();
		}
	}

	/** Get the properties instance. Uses singleton pattern 
	 * @throws IOException */
	private static ConstantConfigurations getInstance() {
		// Uses singleton pattern to guarantee the creation of only one instance
		if (instance == null) {
			createInstance();
		}
		return instance;
	}
	
	public static String getProperty(String key) {
		return System.getProperty(key, ConstantConfigurations.getPropertyLocal(key));
	}

	/** Get a property of the property file 
	 * @throws IOException */
	public static String getPropertyLocal(String key) {
		String result = null;
		if (key != null && !key.trim().isEmpty()) {
			try {
				result = getInstance().properties.getProperty(key);
			} catch (EncryptionInitializationException|ExceptionInInitializerError|IllegalArgumentException|EncryptionOperationNotPossibleException e) { //TODO
				LOG.error("Master password needs to be provided as a JDK parameter for scripts to run. The master password value is used to decrypt any encrypted parameters in the property file.");
				LOG.error("If Master Password value is not provided, encrypted parameters won't be read from property file, an empty value will be provided instead");	
				e.printStackTrace();
			}
		}
		if (result != null) {
			return result;
		} else {
			return "";
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
