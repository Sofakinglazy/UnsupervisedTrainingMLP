package loggerutils;

import junit.framework.TestCase;

public class LoggerUtilsTest extends TestCase {

	public void testGetLogDirectoryPath(){
		final String logDirectoryPath = LoggerUtils.getLogDirectoryPath();
		System.out.println(logDirectoryPath);
		System.out.println(LoggerUtils.getPathPattern(logDirectoryPath));
	}
	
	public void testCreateLogger(){
		LoggerUtils.createLogger();
	}
}
