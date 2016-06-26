package neuralnetwork;

import junit.framework.TestCase;

public class NeuralNetworkTest extends TestCase {
	
	public void testConstructor(){
		double learningRate = 0.1d;
		int numOfInputs = 3;
		int numOfOutputs = 1;
		int numOfLayers = 3;
		int[] numOfHiddenNeurons ={2, 2};
		long maxNumOfIteration = 10000;
		double minError = 0.0000001;
	}
	
	public void testErrorBackPropagation(){
		
	}
}
