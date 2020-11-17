package lt.insoft.webdriver.runner.util;

import org.junit.runner.Description;
import org.springframework.stereotype.Component;

import ru.yandex.qatools.allure.events.TestCasePendingEvent;
import ru.yandex.qatools.allure.junit.AllureRunListener;

@Component
public class ConcurrentAllureRunListener extends AllureRunListener {

	@Override
	public synchronized String getSuiteUid(Description description) {

		return super.getSuiteUid(description);
	}
	
    public void testIgnored(Description description, String ignoreReason) {
        startFakeTestCase(description);
        getLifecycle().fire(new TestCasePendingEvent().withMessage(ignoreReason));
        finishFakeTestCase();
    }

}
