package lt.insoft.webdriver.runner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Component;

import com.test.commons.Constants;

import ru.yandex.qatools.commons.model.Environment;
import ru.yandex.qatools.commons.model.Parameter;

@Component
public class XmlBuilder {

	public void buildEnvironmentDescription() throws JAXBException, IOException {
		ru.yandex.qatools.commons.model.ObjectFactory factory = new ru.yandex.qatools.commons.model.ObjectFactory();
		Environment environment = new Environment();
		List<Parameter> parameters = new ArrayList<Parameter>();

		Parameter browser = new Parameter();
		browser.setName("Browser");
		browser.setValue(Constants.BROWSER);
		browser.setKey("Browser");
		parameters.add(browser);

		Parameter threadCount = new Parameter();
		threadCount.setName("Thread count");
		threadCount.setValue(Integer.toString(Constants.THREAD_COUNT));
		threadCount.setKey("Thread count");
		parameters.add(threadCount);

		Parameter appUrl = new Parameter();
		appUrl.setName("App url");
		appUrl.setValue(Constants.DEFAULT_PORTAL_URL);
		appUrl.setKey("App url");
		parameters.add(appUrl);

		environment.withParameter(parameters);

		JAXBContext context = JAXBContext.newInstance(environment.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(new File(Constants.REPORT_FOLDER + "/environment.xml")), Charset.forName("UTF-8"));
		try {
			marshaller.marshal(factory.createEnvironment(environment), writer);
		} finally {
			writer.close();
		}
	}

}
