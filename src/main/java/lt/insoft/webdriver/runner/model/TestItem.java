package lt.insoft.webdriver.runner.model;

import java.lang.reflect.Method;

import org.apache.commons.lang3.reflect.MethodUtils;

public class TestItem {
	private Class<?> testClass = null;
	private Method testMethod;

	private static boolean success = true;
	// private Throwable testExteption = null;

	public TestItem() {
	}

	public TestItem(Class<?> testClass, String testMethodName) {
		this.testMethod = MethodUtils.getAccessibleMethod(testClass, testMethodName, new Class[] {});
		this.testClass = testClass;
	}

	public Class<?> getTestClass() {
		return testClass;
	}

	public String getTitle() {
		return testClass.getSimpleName() + "." + testMethod.getName();
	}

	public Method getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(Method testMethod) {
		this.testMethod = testMethod;
	}

	public static boolean getIsSuccess() {
		return success;
	}

	public static void setNoSuccess() {
		success = false;
	}

}