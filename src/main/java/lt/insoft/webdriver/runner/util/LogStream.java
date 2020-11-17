package lt.insoft.webdriver.runner.util;

import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogStream extends PrintStream {
	private static final Log LOG = LogFactory.getLog(LogStream.class);
	private static final LogStream INSTANCE = new LogStream();

	public static LogStream getInstance() {
		return INSTANCE;
	}

	private LogStream() {
		super(System.out);
	}

	@Override
	public void println(Object x) {
		LOG.info(x);
	}

	@Override
	public void println(String x) {
		LOG.info(x);
	}

}