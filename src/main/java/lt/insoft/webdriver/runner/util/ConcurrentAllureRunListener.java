package lt.insoft.webdriver.runner.util;

import org.junit.runner.Description;
import org.springframework.stereotype.Component;

import io.qameta.allure.junit4.AllureJunit4;


@Component
public class ConcurrentAllureRunListener extends AllureJunit4 {

//	@Override
//	public synchronized String getSuiteUid(Description description) {
//
//		return super.getSuiteUid(description);
//	}
	
//    public void testIgnored(Description description, String ignoreReason) {
//        startFakeTestCase(description);
//        getLifecycle().fire(new TestCasePendingEvent().withMessage(ignoreReason));
//        finishFakeTestCase();
//    }

}
