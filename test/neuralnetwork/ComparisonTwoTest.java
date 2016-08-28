package neuralnetwork;

import formatdata.FileIO;
import formatdata.Utils;
import junit.framework.TestCase;

public class ComparisonTwoTest extends TestCase {

	public void testComparisonTwoForBP() {
		double learningRate = 0.5d;
		long maxNumOfIterations = 1000;
		double minError = 1E-4d;
		double momentum = 0d;
		int[] numOfNodes = {784, 100, 100, 10};

		NeuralNetworkBP.PATH += String.format("readDatabp(%.1fl%.1fm%di).txt", learningRate, momentum, maxNumOfIterations);
				
		// Real data
		double[][] inputs =  Utils.formatImagesForNeuralNetwork("test"); // 0 -> inputs 
		double[][] outputs = Utils.formatLabelsForNeuralNetwork("test"); // 1 -> outputs 

		NeuralNetwork nn = new NeuralNetworkBP(numOfNodes, inputs, outputs, learningRate, momentum, minError,
				maxNumOfIterations);

		nn.trainNetwork();
		
		NeuralNetworkIO.saveNeuralNetwork(nn, "./network/bp(train)0.5l0.0m1000i.nn");
//		nn = NeuralNetworkIO.loadNeuralNetwork("./network/bp(train)0.5l0.0m100i.nn");
//
//		inputs = Utils.formatImagesForNeuralNetwork("test");
//		outputs = Utils.formatLabelsForNeuralNetwork("test");
		double[][] actualOutputs = nn.testNetwork(inputs);

		int correct = verify(actualOutputs, outputs);
		
		System.out.println(correct);

		double rate = correct / (double) actualOutputs.length * 100;

		System.out.printf("The correct rate for BP: %.2f%% \n", rate);
		FileIO.writeToFile(String.format("The correct rate for BP: %.2f%% \n", rate), NeuralNetworkBP.PATH, true);

	}
	
	private int verify(double[][] testOutputs, double[][] outputs) {
		int correct = 0;
		if (testOutputs.length != outputs.length) {
			System.err.println("Two matrixes don't have same length.");
			return correct;
		}
		for (int i = 0; i < testOutputs.length; i++) {
			if (testOutputs[i].length != outputs[i].length) {
				System.err.println("Two matrixes don't have same length.");
				return correct;
			}
		}
		
		//find the first one and consider it as the the current class
//		for (int i = 0; i < testOutputs.length; i++){
//			
//		}

		boolean wrong = false;
		for (int i = 0; i < testOutputs.length; i++) {
			for (int j = 0; j < testOutputs[i].length; j++) {
				testOutputs[i][j] = Math.round(testOutputs[i][j]);
				outputs[i][j] = Math.round(outputs[i][j]);
				if (testOutputs[i][j] != outputs[i][j])
					wrong = true;
			}
			if (!wrong)
				correct++;
			wrong = false;
		}

		return correct;
	}
}
