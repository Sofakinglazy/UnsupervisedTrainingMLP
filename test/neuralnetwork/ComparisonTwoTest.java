package neuralnetwork;

import java.util.ArrayList;
import java.util.List;

import formatdata.FileIO;
import formatdata.Utils;
import junit.framework.TestCase;

public class ComparisonTwoTest extends TestCase {

	public void ComparisonTwoForBP() {
		double learningRate = 0.5d;
		long maxNumOfIterations = 1000;
		double minError = 1E-4d;
		double momentum = 0.3d;
		int[] numOfNodes = { 784, 100, 100, 10 };

		NeuralNetworkBP.PATH += String.format("readDatabp(%.1fl%.1fm%di).txt", learningRate, momentum,
				maxNumOfIterations);

		// Real data
		double[][] inputs = Utils.formatImagesForNeuralNetwork();
		double[][] outputs = Utils.formatLabelsForNeuralNetwork();

		NeuralNetwork nn = new NeuralNetworkBP(numOfNodes, inputs, outputs, learningRate, momentum, minError,
				maxNumOfIterations);

//		nn.trainNetwork();

//		NeuralNetworkIO.saveNeuralNetwork(nn, "./network/bp(train)0.5l0.3m1000i.nn");
//		nn = NeuralNetworkIO.loadNeuralNetwork("./network/Weakbp(train)0.5l0.0m1000i.nn");
		//
		inputs = Utils.formatImagesForNeuralNetwork("test");
		outputs = Utils.formatLabelsForNeuralNetwork("test");
		double[][] actualOutputs = nn.testNetwork(inputs);

		int correct = verify(actualOutputs, outputs);

		System.out.println(correct);

		double rate = correct / (double) actualOutputs.length * 100;

		System.out.printf("The correct rate for BP: %.2f%% \n", rate);
		FileIO.writeToFile(String.format("The correct rate for BP: %.2f%% \n", rate), NeuralNetworkBP.PATH, true);

	}

	public void ComparisonOneForFL() {
		long maxNumOfIterations = 1;
		int[] numOfNodes = { 784, 100, 100, 10 };
		double increaseFactor = 0.5d;
		double decayFactor = 0.2d;
		double[] fixedBias = { 0.5d, 0.5d, 0d };

		double[][] inputs = Utils.formatImagesForNeuralNetwork("test");
		double[][] outputs = Utils.formatLabelsForNeuralNetwork("test");

		NeuralNetwork nn = new NeuralNetworkFL(numOfNodes, inputs, outputs, maxNumOfIterations, increaseFactor,
				decayFactor, fixedBias);

		nn.trainNetwork();

		double[][] testOutputs = nn.testNetwork(inputs);

		int correct = verify(testOutputs, outputs);

		double rate = correct / (double) testOutputs.length * 100;

		System.out.printf("The correct rate for BP: %.2f%% \n", rate);
		FileIO.writeToFile(String.format("The correct rate for BP: %.2f%% \n", rate), NeuralNetworkFL.PATH, true);
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

	public void RandomAssignment() {
		double[][] outputs = Utils.formatLabelsForNeuralNetwork("test");
		List<Integer> labels = new ArrayList<>();
		String path = "./result/realDataRandom.txt";

		for (int i = 0; i < outputs.length; i++) {
			for (int j = 0; j < outputs[i].length; j++) {
				if (outputs[i][j] == 1.0d) {
					labels.add(j);
				}
			}
		}

		for (int j = 0; j < 10; j++) {
			List<Integer> rand = randomiseLabelList(outputs.length);

			int correct = 0;
			for (int i = 0; i < labels.size(); i++) {
				if (labels.get(i) == rand.get(i)) {
					correct++;
				}
			}

			double rate = correct / (double) outputs.length * 100;
			System.out.printf("The correct rate for RandomAssign: %.2f%% \n", rate);
			FileIO.writeToFile(rate + "\n", path, true);
		}
	}

	private List<Integer> randomiseLabelList(int length) {
		List<Integer> rand = new ArrayList<>(length);
		for (int i = 0; i < length; i++) {
			rand.add((int) Math.round(Math.random() * 9d));
		}
		return rand;
	}
}
