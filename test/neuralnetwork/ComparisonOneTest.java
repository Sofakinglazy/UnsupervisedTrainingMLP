package neuralnetwork;

import java.util.ArrayList;
import java.util.List;

import formatdata.FileIO;
import junit.framework.TestCase;

public class ComparisonOneTest extends TestCase {

	public void testComparisonOneForBP() {
		double learningRate = 0.5d;
		long maxNumOfIterations = 10000;
		double minError = 1E-4d;
		double momentum = 0.5d;
		int[] numOfNodes = { 10, 7, 1 };

		for (learningRate = 0.3; learningRate < 10; learningRate += 0.1) {
			NeuralNetworkBP.PATH += String.format("easybp(%.1fl%.1fm).txt", learningRate, momentum);

			double[][] inputs = assignInput();
			double[][] outputs = assignOutput();

			NeuralNetwork nn = new NeuralNetworkBP(numOfNodes, inputs, outputs, learningRate, momentum, minError,
					maxNumOfIterations);

			nn.trainNetwork();

			double[][] testOutputs = nn.testNetwork(inputs);

			int correct = verify(testOutputs, outputs);

			double rate = correct / (double) testOutputs.length * 100;

			System.out.printf("The correct rate for BP: %.2f%% \n", rate);
			FileIO.writeToFile(String.format("The correct rate for BP: %.2f%% \n", rate), NeuralNetworkBP.PATH, true);
			NeuralNetworkBP.PATH = "./result/LearningRateBPM0.5/";
		}
	}

	public void ComparisonOneForFL() {
		long maxNumOfIterations = 10000;
		int[] numOfNodes = { 10, 7, 7, 1 };
		double increaseFactor = 0.5d;
		double decayFactor = 0.1d;
		double[] fixedBias = { 0.5d, 0.5d, 0.5d };

		double[][] inputs = assignInput();
		double[][] outputs = assignOutput();

		NeuralNetwork nn = new NeuralNetworkFL(numOfNodes, inputs, outputs, maxNumOfIterations, increaseFactor,
				decayFactor, fixedBias);

		nn.trainNetwork();

		double[][] testOutputs = nn.testNetwork(inputs);

		int correct = verify(testOutputs, outputs);

		double rate = correct / (double) testOutputs.length * 100;

		System.out.printf("The correct rate for BP: %.2f%% \n", rate);
		FileIO.writeToFile(String.format("The correct rate for BP: %.2f%% \n", rate), NeuralNetworkFL.PATH, true);
	}

	public void RandomAssaignment() {
		double[][] inputs = assignInput();
		double[][] outputs = assignOutput();
		List<Integer> classOneIndex = new ArrayList<>();
		String path = "./result/easyRandom.txt";

		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < inputs.length; i++) {
				int index = (int) Math.round(Math.random());
				classOneIndex.add(index);
			}
			assertEquals(9, classOneIndex.size());
			int correct = 0;
			int count = 0;
			for (Integer index : classOneIndex) {
				if (index == (int) outputs[count][0]) {
					correct++;
				}
				count++;
			}
			double rate = (correct / (double) outputs.length) * 100;
			System.out.printf("The correct rate for random assignment: %.2f%% \n", rate);
			FileIO.writeToFile(rate + "\n", path, true);
			classOneIndex.clear();
		}
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

	private double[][] assignOutput() {
		int rows = 9;
		int cols = 1;
		double[][] outputs = new double[rows][];
		for (int i = 0; i < rows; i++) {
			outputs[i] = new double[cols];
			if (i < 4)
				outputs[i][0] = 0d;
			else
				outputs[i][0] = 1d;
		}

		// print(outputs);
		return outputs;
	}

	private double[][] assignInput() {
		int rows = 9;
		int cols = 10;
		int[] zeroIndex = { 2, 3 };
		double[][] inputs = new double[rows][];
		for (int i = 0; i < 4; i++) {
			inputs[i] = new double[cols];
			for (int j = 0; j < cols; j++) {
				if (j < 5 && j != zeroIndex[0] && j != zeroIndex[1]) {
					inputs[i][j] = 1d;
				} else {
					inputs[i][j] = 0d;
				}
			}
			for (int k = 0; k < zeroIndex.length; k++) {
				zeroIndex[k]++;
				if (zeroIndex[k] == 5)
					zeroIndex[k] = 0;
			}
		}

		zeroIndex[0] = 2;
		zeroIndex[0] = 3;
		for (int i = 4; i < 9; i++) {
			inputs[i] = new double[cols];
			for (int j = 0; j < cols; j++) {
				if (j < 5 || j == (zeroIndex[0] + 5) || j == (zeroIndex[1] + 5)) {
					inputs[i][j] = 0d;
				} else {
					inputs[i][j] = 1d;
				}
			}
			for (int k = 0; k < zeroIndex.length; k++) {
				zeroIndex[k]++;
				if (zeroIndex[k] == 5)
					zeroIndex[k] = 0;
			}
		}

		// print(inputs);
		return inputs;
	}

	private void print(double[][] inputs) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inputs.length; i++) {
			for (int j = 0; j < inputs[i].length; j++) {
				sb.append(Math.round(inputs[i][j]));
				sb.append(",");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
}
