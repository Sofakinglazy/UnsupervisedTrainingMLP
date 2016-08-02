package neuralnetwork;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class NeuralNetworkLogger {

	public static String LOG_PATH = "NeuralNetworkLog.log";

	private Logger logger;
	private FileHandler fh;
	private String path;

	public NeuralNetworkLogger() throws SecurityException, IOException {
		logger = Logger.getLogger("NeuralNetwork");
		path = LOG_PATH;
		fh = new FileHandler(path);
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());
	}

	public NeuralNetworkLogger(String path) throws SecurityException, IOException {
		logger = Logger.getLogger("NeuralNetwork");
		this.path = path;
		fh = new FileHandler(path);
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void log(String text) {
		logger.info(text);
	}
}
