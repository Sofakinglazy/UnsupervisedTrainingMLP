package neuralnetwork;

import java.io.Serializable;
import java.util.Scanner;

import formatdata.FileIO;
import loggerutils.LoggerUtils;

public class NeuralNetworkBP implements NeuralNetwork, Serializable{

	private static final long serialVersionUID = 1763320417566459008L;
	
	private double learningRate;// user-defined
	public double[][] actualOutput;
	public double[][] inputs;
	public double[][] expectedOutput;
	private double minError;
	private int numOfLayers;
	private int currInputIndex;
	private int numOfInputSets;
	private long maxNumOfIterations;
	private double overalError;
	private double momentum;// user-defined

	public Layer[] layers;
	public static String PATH = "./result/LearningRateBPM0.5/";
	private StringBuilder sb;
	private boolean stopTraining;
	
	public NeuralNetworkBP(
			int[] numOfNodes,
			double[][] inputSamples,
			double[][] outputSamples,
			double learningRate, 
			double momentum,
			double minError, 
			long maxNumOfIterations){
		numOfLayers = numOfNodes.length;
		numOfInputSets = inputSamples.length;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.minError = minError;
		this.maxNumOfIterations = maxNumOfIterations;
		
		layers = new Layer[numOfLayers];
		layers[0] = new Layer(numOfNodes[0], numOfNodes[0]); // The input layer
		for (int i = 1; i < numOfLayers; i++){
			layers[i] = new Layer(numOfNodes[i-1], numOfNodes[i]);
		} // The hidden layers and output layer
		
		inputs = new double[numOfInputSets][layers[0].nodes.length];
		expectedOutput = new double[numOfInputSets][layers[numOfLayers - 1].nodes.length];
		actualOutput = new double[numOfInputSets][layers[numOfLayers - 1].nodes.length];
		
		for (int i = 0; i < numOfInputSets; i++){
			for (int j = 0; j < layers[0].nodes.length; j++){
				inputs[i][j] = inputSamples[i][j];
			}
		}
		for (int i = 0; i < numOfInputSets; i++){
			for (int j = 0; j < layers[numOfLayers - 1].nodes.length; j++){
				expectedOutput[i][j] = outputSamples[i][j];
			}
		}
		
//		LoggerUtils.createLogger();
		sb = new StringBuilder();
		stopTraining = false;
		new Thread(new Runnable() {
			public void run() {
				Scanner sc = new Scanner(System.in);
				String c = sc.nextLine();
				if (c.equals("c")){
					stopTraining = true;
				}
			}
		}).start();
	}
	
	public void feedForward(){
		// Input layer's output is equal to its input
		for (int i = 0; i < layers[0].nodes.length; i++){
			layers[0].nodes[i].output = layers[0].inputs[i];
		}
		layers[1].inputs = layers[0].inputs;
		for (int i = 1; i < numOfLayers; i++){
			layers[i].feedForward();
			if (i != numOfLayers - 1){
				layers[i+1].inputs = layers[i].getOutputs();
			}
		}
	}
	
	public void updateWeights(){
		calculateSignalError();
		backpropagateError();
	}

	private void calculateSignalError() {
		int outputLayerIndex = numOfLayers - 1;
		double sum = 0d;
		for (int i = 0; i < layers[outputLayerIndex].nodes.length; i++){
			layers[outputLayerIndex].nodes[i].error = (expectedOutput[currInputIndex][i] - layers[outputLayerIndex].nodes[i].output)
														* layers[outputLayerIndex].nodes[i].output 
														* (1d - layers[outputLayerIndex].nodes[i].output);
		}
		
		for (int i = numOfLayers - 2; i > 0; i--){
			for (int j = 0; j < layers[i].nodes.length; j++){
				sum = 0d;
				for (int k = 0; k < layers[i+1].nodes.length; k++){
					sum = sum + layers[i+1].nodes[k].weights[j] * layers[i+1].nodes[k].error;
				}
				layers[i].nodes[j].error = layers[i].nodes[j].output * (1d - layers[i].nodes[j].output) * sum;
			}
		}
	}
	
	private void backpropagateError() {
		for (int i = numOfLayers-1; i > 0; i--){
			for (int j = 0; j < layers[i].nodes.length; j++){
				// update bias difference
				layers[i].nodes[j].biasDiff = learningRate * layers[i].nodes[j].error + momentum * layers[i].nodes[j].biasDiff;
				// update bias 
				layers[i].nodes[j].bias = layers[i].nodes[j].bias + layers[i].nodes[j].biasDiff;
				// update weights 
				for (int k = 0; k < layers[i].inputs.length; k++){
					// calculate the weights difference between node k and j
					layers[i].nodes[j].weightsDiff[k] = learningRate * layers[i].nodes[j].error * layers[i-1].nodes[k].output
														+ momentum * layers[i].nodes[j].weightsDiff[k];
					// calculate the weights between node k and j
					layers[i].nodes[j].weights[k] = layers[i].nodes[j].weights[k] + layers[i].nodes[j].weightsDiff[k];
				}
			}
		}
	}
	
	public void calculateOveralError(){
		overalError = 0;
		for (int i = 0; i < numOfInputSets; i++){
			for (int j = 0; j < layers[numOfLayers-1].nodes.length; j++){
				overalError = overalError + 0.5d * (Math.pow(expectedOutput[i][j] - actualOutput[i][j], 2));
			}
		}
		
		System.out.println("Current Overal Error is: " + overalError);
		sb.append(overalError);
		sb.append("\n");
	}
	
	public void trainNetwork(){
		int currIteration = 0;
		do{
			for (currInputIndex = 0; currInputIndex < numOfInputSets; currInputIndex++){
				// assign inputs 
				for (int i = 0; i < layers[0].nodes.length; i++){
					layers[0].inputs[i] = inputs[currInputIndex][i];
				}
				feedForward();
				// assign outputs
				for (int i = 0; i < layers[numOfLayers-1].nodes.length; i++){
					actualOutput[currInputIndex][i] = layers[numOfLayers-1].nodes[i].output;
				}
				updateWeights();
			}
			if (stopTraining){
				System.out.println("user stops training!");
				break;
			}
//			test(); 
			currIteration++;
			calculateOveralError();
		} while ((overalError > minError) && (currIteration < maxNumOfIterations));
//		FileIO.writeToFile(sb.toString(), PATH);
	}

	@SuppressWarnings("unused")
	private void test() {
		// test
		String s = "";
		for (int i = 0; i < actualOutput.length; i++){
			for (int j = 0; j < actualOutput[i].length; j++){
				s = s + actualOutput[i][j] + " ";
			}
			s = s + "\n";
		}
		System.out.println(s);
	}
	
	@Override
	public double[][] testNetwork(double[][] testSamples){
		double[][] outputs = new double[testSamples.length][];
		
		for (int i = 0; i < testSamples.length; i++){
			outputs[i] = new double[expectedOutput[0].length];
			outputs[i] = feedForward(testSamples[i]);
		}
		
		return outputs;
	}
	
	private double[] feedForward(double[] inputs) {
		// Input layer's output is equal to its input
		for (int i = 0; i < layers[0].nodes.length; i++){
			layers[0].nodes[i].output = inputs[i];
		}
		
		layers[1].inputs = inputs;
		for (int i = 1; i < numOfLayers; i++){
			layers[i].feedForward();
			if (i != numOfLayers - 1){
				// the last output was zero!!!!
				layers[i+1].inputs = layers[i].getOutputs();
			}
		}
		
		return layers[numOfLayers-1].getOutputs();
	}

	public String toString(){
		String s = "";
		s = s + "#NeuralNetwork#\n" +
				"Learning rate: " + learningRate + "\n" +
				"Max Literations: " + maxNumOfIterations + "\n" +
				"Momentum: " + momentum + "\n" + 
				"Min Error: " + minError + "\n" + 
				"Layers:\n";
		for (int i = 0; i < numOfLayers; i++) System.out.println(layers[i]); 
		return s;
	}

	@Override
	public void printLayer(int layerIndex) {
		Layer layer = layers[layerIndex];
		for (int j = 0; j < layer.nodes.length; j++){
			for (int k = 0; k < layer.nodes[j].weights.length; k++){
				System.out.println("Print: [" + layerIndex + "," + j + "," + k + "]: " + layer.nodes[j].weights[k]);
			}
		}
	}
}
