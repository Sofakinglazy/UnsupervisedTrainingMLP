package neuralnetwork;

import formatdata.FileIO;
import formatdata.Utils;
import junit.framework.TestCase;

public class ComparisonThreeTest extends TestCase {

	// Get the correct rate of the bp network and repeat for 10 times 
	public void TenTimes(){
		for (int i = 0; i < 10; i++){
//			ComparisonThreeForBP();
		}
	}
	
	public void testComparisonThreeForBP() {
		double learningRate = 0.5d;
		long maxNumOfIterations = 10;
		double minError = 1E-4d;
		double momentum = 0.3d;
		int[] numOfNodes = { 784, 100, 100, 10 };

		NeuralNetworkBP.PATH += String.format("readDatabp(%.1fl%.1fm%di)10.txt", learningRate, momentum,
				maxNumOfIterations);

		// Real data
		double[][] inputs = Utils.formatImagesForNeuralNetwork();
		double[][] outputs = Utils.formatLabelsForNeuralNetwork();

		for (int i = 0; i < 10; i++){
		NeuralNetwork nn = new NeuralNetworkBP(numOfNodes, inputs, outputs, learningRate, momentum, minError,
				maxNumOfIterations);

		nn.trainNetwork();

//		NeuralNetworkIO.saveNeuralNetwork(nn, "./network/bp(train)0.5l0.3m1000i.nn");
//		nn = NeuralNetworkIO.loadNeuralNetwork("./network/Weakbp(train)0.5l0.0m1000i.nn");
		
		inputs = Utils.formatImagesForNeuralNetwork("test");
		outputs = Utils.formatLabelsForNeuralNetwork("test");
		double[][] actualOutputs = nn.testNetwork(inputs);

		int correct = verify(actualOutputs, outputs);

		System.out.println(correct);

		double rate = correct / (double) actualOutputs.length * 100;

		System.out.printf("The correct rate for BP: %.2f%% \n", rate);
		FileIO.writeToFile(String.format("The correct rate for BP: %.2f%% \n", rate), NeuralNetworkBP.PATH, true);
		}
	}
	
	private int verify(double[][] actualOutputs, double[][] testOutputs) {
		int correct = 0;
		if (actualOutputs.length != testOutputs.length) {
			System.err.println("Two matrixes don't have same length.");
			return correct;
		}
		for (int i = 0; i < actualOutputs.length; i++) {
			if (actualOutputs[i].length != testOutputs[i].length) {
				System.err.println("Two matrixes don't have same length.");
				return correct;
			}
		}

		boolean wrong = false;
		for (int i = 0; i < actualOutputs.length; i++) {
			for (int j = 0; j < actualOutputs[i].length; j++) {
				actualOutputs[i][j] = Math.round(actualOutputs[i][j]);
				testOutputs[i][j] = Math.round(testOutputs[i][j]);
				if (actualOutputs[i][j] != testOutputs[i][j])
					wrong = true;
			}
			if (!wrong)
				correct++;
			wrong = false;
		}

		return correct;
	}
}
