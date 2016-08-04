package loggerutils;

public class LoggerTest {

	public void testCreateLogger(){
		LoggerUtils.createLogger();
		System.out.println(LoggerUtils.getLogDirectoryPath());
	}
	
	
}
