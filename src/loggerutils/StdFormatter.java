package loggerutils;

import java.util.logging.*;

public class StdFormatter extends SimpleFormatter{
	
	private static class StdOutErrLevel extends Level{
		protected StdOutErrLevel(String name, int value) {
			super(name, value);
		}
	}
	
	public final static Level STDERR = new StdOutErrLevel("STDERR", Level.SEVERE.intValue() + 53); 
	public final static Level STDOUT = new StdOutErrLevel("STDOUT", Level.WARNING.intValue() + 53); 
	private final String lineSeperator = System.getProperty("line.seperator");
	
	@Override
	public synchronized String format(LogRecord record) {
		if (!StdFormatter.STDERR.getName().equals(record.getLoggerName()) 
				&& !StdFormatter.STDOUT.getName().equals(record.getLoggerName())){
			return super.format(record);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(lineSeperator);
		String message = formatMessage(record);
		sb.append(record.getLevel().getLocalizedName());
		sb.append(": ");
		sb.append(message);
		return sb.toString();
	}
	
}
