package neuralnetwork;

import formatdata.Utils;
import junit.framework.TestCase;

public class NeuralNetworkFLTest extends TestCase {

	private boolean active = false;
	private double increaseFactor = 0.5d;
	private double decayFactor = 0.2d;

	private double[][] assignOutputSamples() {
		double[][] outputSamples = new double[3][];
		outputSamples[0] = new double[1];
		outputSamples[1] = new double[1];
		outputSamples[2] = new double[1];
		outputSamples[0][0] = 1d;
		outputSamples[1][0] = 0d;
		outputSamples[2][0] = 0d;
		return outputSamples;
	}

	private double[][] assignInputSamples() {
		double[][] inputSamples = new double[3][];
		inputSamples[0] = new double[3];
		inputSamples[1] = new double[3];
		inputSamples[2] = new double[3];
		for (int i = 0; i < 3; i++) {
			inputSamples[i] = new double[3];
			for (int j = 0; j < inputSamples[i].length; j++) {
				if (i == j) {
					inputSamples[i][j] = 1d;
				} else {
					inputSamples[i][j] = 0d;
				}
			}
		}
		return inputSamples;
	}

	public void testForceLearningRules() {
		double originalWeight = 0.123456123d;
		double newWeight = updateWeight(originalWeight);
		// System.out.println(newWeight);

		active = true;
		newWeight = updateWeight(originalWeight);
		// System.out.println(newWeight);
	}

	private double updateWeight(double originalWeight) {
		double newWeight;
		if (active) {
			newWeight = originalWeight + increaseFactor * originalWeight - decayFactor * originalWeight;
		} else {
			newWeight = originalWeight - decayFactor * originalWeight;
		}
		return newWeight;
	}

	public void testTrainNetwork() {
		double increaseFactor = 0.5d;
		double decayFactor = 0.2d;
		long maxNumOfIterations = 10000;
		int[] numOfNodes = { 3, 4, 4, 1 };
		double[] fixedBias = { 0.5d, 0.8d, 0.5d };

		double[][] inputSamples = assignInputSamples(); // {1, 0, 0; 0, 1, 0; 0,
														// 0,
														// 1}
		double[][] outputSamples = assignOutputSamples(); // {1; 0; 0}
		NeuralNetworkFL nn = new NeuralNetworkFL(numOfNodes, inputSamples, outputSamples, maxNumOfIterations,
				increaseFactor, decayFactor, fixedBias);
//		nn.trainNetwork();
	}

	public void testTrainNetworkWithRealData() {
		double[][] imageWithLabelOne = Utils.formatImagesWithSameLabelForNeuralNetwork(1, "test");
		double[][] imageWithLabelTwo = Utils.formatImagesWithSameLabelForNeuralNetwork(2, "test");

		System.out.printf("label_one_imgae:[%d,%d]\n", imageWithLabelOne.length, imageWithLabelOne[0].length);
		System.out.printf("label_two_imgae:[%d,%d]\n", imageWithLabelTwo.length, imageWithLabelTwo[0].length);

		double increaseFactor = 0.5d;
		double decayFactor = 0.2d;
		long maxNumOfIterations = 5;
		int[] numOfNodes = { 784, 50, 50, 10 };
		double[] fixedBias = { 0.5d, 0.8d, 0.5d };

		double[][] inputSamples = Utils.combine(imageWithLabelOne, imageWithLabelTwo);
		double[][] output1 = assignOutputs(imageWithLabelOne.length, 10, 0);
		double[][] output2 = assignOutputs(imageWithLabelTwo.length, 10, 1);
		double[][] outputSamples = Utils.combine(output1, output2);
		

		NeuralNetworkFL nn = new NeuralNetworkFL(numOfNodes, inputSamples, outputSamples, maxNumOfIterations,
				increaseFactor, decayFactor, fixedBias);
		
		nn.trainNetwork();
	}

	private double[][] assignOutputs(int rows, int cols, int index) {
		double[][] outputs = new double[rows][];
		for (int i = 0; i < rows; i++){
			outputs[i] = new double[cols];
			for (int j = 0; j < cols; j++){
				if (index == j)
					outputs[i][j] = 1;
				else 
					outputs[i][j] = 0; 
			}
		}
		return outputs;
	}
	
}
