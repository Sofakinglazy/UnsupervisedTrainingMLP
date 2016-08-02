package neuralnetwork;

import java.io.IOException;

import junit.framework.TestCase;

public class NeuralNetworkLoggerTest extends TestCase {

	public void testLoggerConstructor(){
		try {
			NeuralNetworkLogger logger = new NeuralNetworkLogger();
			logger.log("test");
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testLoggerWithNeuralNetwork(){
		try {
			NeuralNetworkLogger logger = new NeuralNetworkLogger("Layer.log");
			LayerFL firstLayer = new LayerFL(5, 2, 1d);
			logger.log(firstLayer.nodes[0].toString());
			firstLayer.setIntegerWeithts();
			logger.log(firstLayer.nodes[0].toString());
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
}
