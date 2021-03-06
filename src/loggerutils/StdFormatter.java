package loggerutils;

import java.util.logging.*;

public class StdFormatter extends SimpleFormatter{
	
	private static class StdOutErrLevel extends Level{
		private static final long serialVersionUID = -2208231121167636762L;

		protected StdOutErrLevel(String name, int value) {
			super(name, value);
		}
	}
	
	public final static Level STDERR = new StdOutErrLevel("StdErr", Level.SEVERE.intValue() + 53); 
	public final static Level STDOUT = new StdOutErrLevel("StdOut", Level.WARNING.intValue() + 53); 
	private final String lineSeperator = System.getProperty("line.separator");
	
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
