package loggerutils;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

import javax.annotation.Resource;
import javax.rmi.CORBA.Util;

import formatdata.Utils;

public class LoggerUtils {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static boolean loggerCreated = false;

	public static void createLogger() {
		if (loggerCreated)
			return;
		loggerCreated = true;
		FileHandler fileHandler = null;
		final Logger parentLogger = Logger.getAnonymousLogger().getParent();
		final Handler[] handlers = parentLogger.getHandlers();
		for (int i = 0; i < handlers.length; i++) {
			final Handler handler = handlers[i];
			if (handler instanceof ConsoleHandler) {
				parentLogger.removeHandler(handler);
			}
		}
		try {
			final String logDirectoryPath = getLogDirectoryPath();
			final File logDirectory = new File(logDirectoryPath);
			logDirectory.mkdirs();
			if (logDirectory.isDirectory()) {
				final String pathPattern = logDirectoryPath + ".log";
				fileHandler = new FileHandler(pathPattern);
				fileHandler.setFormatter(new StdFormatter());
				parentLogger.addHandler(fileHandler);
			}
			final ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new StdFormatter());
			if (System.getProperty("java.util.logging.config.file", null) == null) {
				fileHandler.setLevel(Level.INFO);
				consoleHandler.setLevel(Level.INFO);
			}
			parentLogger.addHandler(consoleHandler);
			LoggingOutputStream los;
			Logger logger = Logger.getLogger(StdFormatter.STDOUT.getName());
			los = new LoggingOutputStream(logger, StdFormatter.STDOUT);
			System.setOut(new PrintStream(los, true));
			logger = Logger.getLogger(StdFormatter.STDERR.getName());
			los = new LoggingOutputStream(logger, StdFormatter.STDERR);
			System.setErr(new PrintStream(los, true));
		} catch (Exception e) {
			LoggerUtils.warn("Error in creating logging file handler", e);
		}

	}

	public static void warn(final String string, final Throwable e) {
		LOGGER.log(Level.WARNING, string, e);
	}

	public static void warn(final Throwable e) {
		LoggerUtils.warn("", e);
	}

	public static void info(final String string) {
		LOGGER.log(Level.INFO, string);
	}

	public static void severe(final String message) {
		LOGGER.log(Level.SEVERE, message);
	}

	public static void severe(final String comment, final Throwable e) {
		if (e instanceof SecurityException || e.getCause() instanceof SecurityException)
			warn(comment, e);
		else
			LOGGER.log(Level.SEVERE, comment, e);
	}

	public static String getLogDirectoryPath() {
		final String currentTime = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
		final String logDirectory = Utils.CURRENT_PATH + currentTime + ".log";
		return logDirectory;
	}

}
