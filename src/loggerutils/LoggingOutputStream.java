package loggerutils;

import java.io.*;
import java.util.logging.*;

public class LoggingOutputStream extends ByteArrayOutputStream{

	private final Level level;
	private final String lineSeparator;
	private final Logger logger;
	
	public LoggingOutputStream(final Logger logger, final Level level){
		super();
		this.logger = logger;
		this.level = level;
		lineSeparator = System.getProperty("line.separator");
	}

	@Override
	public void flush() throws IOException {
		String record;
		synchronized (this){
			super.flush();
			record = this.toString();
			super.reset();
		}
		if (record.length() == 0 || record.equals(lineSeparator)){
			return;
		}
		logger.logp(level, "", "", record);
	}
	
	
}
