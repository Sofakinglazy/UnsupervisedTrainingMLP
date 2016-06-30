package neuralnetwork;

import junit.framework.TestCase;

public class NeuralNetworkTest extends TestCase {
	
	public void testConstructor(){
		double learningRate = 0.5d;
		long maxNumOfIterations = 10000;
		double minError = 1E-4d;
		double momentum = 0.1d;
		int[] numOfNodes = {3, 3, 1};
		
		double[][] inputSamples = assignInputSamples(); // {1, 0, 0; 0, 1, 0; 0, 0, 1}
		double[][] outputSamples = assignOutputSamples(); // {1; 0; 0}
		
		NeuralNetwork nn = new NeuralNetwork(numOfNodes, inputSamples, outputSamples, 
								learningRate, momentum, minError, maxNumOfIterations);
		
		nn.trainNetwork();
		
		double[][] outputs = nn.testNetwork(inputSamples);
		String s = "";
		for (int i = 0; i < outputs.length; i++){
			for(int j = 0; j < outputs[i].length; j++){
				s = s + outputs[i][j] + " ";
			}
			s = s + "\n";
		}
		System.out.println(s);
	}

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
		for (int i = 0; i < 3; i++){
			inputSamples[i] = new double[3];
			for (int j = 0; j < inputSamples[i].length; j++){
				if (i == j) {
					inputSamples[i][j] = 1d;
				} else {
					inputSamples[i][j] = 0d;
				}
			}
		}
		return inputSamples;
	}
	
	public void testCalculateOveralError(){
		double[][] expectedOutput = assignOutputSamples(); // {1; 0; 0}
		double[][] actualOutput = new double[3][];
		actualOutput[0] = new double[1];
		actualOutput[1] = new double[1];
		actualOutput[2] = new double[1];
		actualOutput[0][0] = 0.991713994384467d;
		actualOutput[1][0] = 2.7522721713103422E-5d;
		actualOutput[2][0] = 3.5641847511922614E-5d;
		
		double error = calculateOveralError(expectedOutput, actualOutput);
//		System.out.println("error: " + error);
	}

	private double calculateOveralError(double[][] expectedOutput, double[][] actualOutput) {
		double overalError = 0d; 
		for (int i = 0; i < expectedOutput.length; i++){
			for (int j = 0; j < expectedOutput[i].length; j++){
				overalError = overalError + 0.5d * (Math.pow(expectedOutput[i][j] - actualOutput[i][j], 2));
			}
		}

		return overalError;
	}
	
	public void testNeuralNetworkIO(){
		double learningRate = 0.5d;
		long maxNumOfIterations = 10000;
		double minError = 1E-4d;
		double momentum = 0.1d;
		int[] numOfNodes = {3, 7, 1};
		
		double[][] inputSamples = assignInputSamples(); // {1, 0, 0; 0, 1, 0; 0, 0, 1}
		double[][] outputSamples = assignOutputSamples(); // {1; 0; 0}
		
		NeuralNetwork nn = new NeuralNetwork(numOfNodes, inputSamples, outputSamples, 
								learningRate, momentum, minError, maxNumOfIterations);
		
//		nn.trainNetwork();
//		
//		NeuralNetworkIO.saveNeuralNetwork(nn);
//		
//		NeuralNetwork nn1 = NeuralNetworkIO.loadNeuralNetwork();
//		
//		System.out.println(nn1);
	}
}
