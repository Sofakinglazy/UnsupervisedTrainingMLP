package neuralnetwork;

import java.io.Serializable;
import java.text.DecimalFormat;

import formatdata.FileIO;
import loggerutils.LoggerUtils;

public class NeuralNetworkFL implements NeuralNetwork, Serializable {

	private static final long serialVersionUID = -6024997329847175600L;

	public static String PATH = "./result/realDatafl.txt";

	private double increaseFactor;
	private double decayFactor;
	private double[] fixedBias;
	private double[][] inputs;
	private double[][] expectedOutput;
	private double[][] actualOutput;
	private int numOfLayers;
	private int numOfInputSet;
	private long maxNumOfIterations;
	private int currSampleIndex;

	private LayerFL[] layers;
	private StringBuilder sb;

	public NeuralNetworkFL(int[] numOfNodes, double[][] inputSamples, double[][] outputSamples, long maxNumOfIterations,
			double increaseFactor, double decayFactor, double[] fixedBias) {
		if (numOfNodes.length != 4) {
			System.err.println("This network only has 4 layers.");
			System.exit(0);
		}
		if (numOfNodes[1] != numOfNodes[2]) {
			System.err.println("The nodes number in hidden layer one must be equal to hidden layer one.");
			System.exit(0);
		}
		if (increaseFactor < decayFactor) {
			System.err.println("Increase factor must be greater than decay factor for force learning.");
			System.exit(0);
		}

		this.numOfInputSet = inputSamples.length;
		this.numOfLayers = numOfNodes.length;
		this.maxNumOfIterations = maxNumOfIterations;
		this.increaseFactor = increaseFactor;
		this.decayFactor = decayFactor;
		this.fixedBias = fixedBias;

		inputs = new double[numOfInputSet][numOfNodes[0]];
		expectedOutput = new double[numOfInputSet][numOfNodes[numOfNodes.length - 1]];
		actualOutput = new double[numOfInputSet][numOfNodes[numOfNodes.length - 1]];

		for (int i = 0; i < inputSamples.length; i++) {
			for (int j = 0; j < inputSamples[i].length; j++) {
				inputs[i][j] = inputSamples[i][j];
			}
		}

		for (int i = 0; i < outputSamples.length; i++) {
			for (int j = 0; j < outputSamples[i].length; j++) {
				expectedOutput[i][j] = outputSamples[i][j];
			}
		}

		layers = new LayerFL[numOfNodes.length];
		layers[0] = new LayerFL(numOfNodes[0], numOfNodes[0], 0d);// input layer
		for (int i = 1; i < numOfNodes.length; i++) {
			layers[i] = new LayerFL(numOfNodes[i - 1], numOfNodes[i], fixedBias[i - 1]);
		}
		setIntegerWeightsOfInputToFirstHiddenLayer();

//		LoggerUtils.createLogger();
		sb = new StringBuilder();
	}

	@Override
	public void trainNetwork() {
		int currIteration = 0;
		do {
			LayerFL[] lastTime = cloneLayers();
			for (currSampleIndex = 0; currSampleIndex < numOfInputSet; currSampleIndex++) {
				// assign inputs
				for (int i = 0; i < inputs[currSampleIndex].length; i++) {
					layers[0].inputs[i] = inputs[currSampleIndex][i];
				}
				feedForward();
				updateWeights();
				// output the last five changes in weights
//				 if (currSampleIndex >= (numOfInputSet - 5)){
//				 calculateWeightsError();
//				 }
			}
			calculateWeightsErrorBetweenDiffIteration(lastTime);
			currIteration++;
			System.out.println("Current Iteration: " + currIteration);
		} while (currIteration < maxNumOfIterations);
		// printLayer(3);
		FileIO.writeToFile(sb.toString(), PATH);
		
	}

	private LayerFL[] cloneLayers() {
		LayerFL[] clone = new LayerFL[layers.length];
		for (int i = 0; i < layers.length; i++) {
			clone[i] = layers[i].cloneLayer();
		}
		return clone;
	}

	private void calculateWeightsErrorBetweenDiffIteration(LayerFL[] lastTime) {
		double error = 0d;
		for (int i = 1; i < numOfLayers; i++) {
			for (int j = 0; j < layers[i].nodes.length; j++) {
				for (int k = 0; k < layers[i].nodes[j].weights.length; k++) {
					error += squareError(layers[i].nodes[j].weights[k], lastTime[i].nodes[j].weights[k]);
				}
			}
		}
		System.out.println("Weight Error between two iterations is: " + error);
		sb.append(error);
		sb.append("\n");
	}

	private void calculateWeightsError() {
		double error = 0d;
		for (int i = 1; i < numOfLayers; i++) {
			for (int j = 0; j < layers[i].nodes.length; j++) {
				for (int k = 0; k < layers[i].nodes[j].weights.length; k++) {
					error += squareError(layers[i].nodes[j].weights[k], layers[i].nodes[j].lastTime.weights[k]);
				}
			}
		}
		System.out.println("Weight Error is: " + error);
	}

	private double squareError(double a, double b) {
		return 0.5d * (Math.pow(a - b, 2));
	}

	private void updateWeights() {
		// Shouldn't update the input layer and first hidden layer
		// PS: shouldn't update the responding layer
		for (int i = 2; i < numOfLayers - 1; i++) {
			// Update each node
			for (int j = 0; j < layers[i].nodes.length; j++) {
				// Update each weight
				for (int k = 0; k < layers[i].nodes[j].weights.length; k++) {
					double increase = 0d;
					// only when last time the corresponding node is active will
					// pass its bias to this one
					if (layers[i - 1].nodes[k].lastTime != null) {
						if (layers[i - 1].nodes[k].lastTime.active) {
							increase = layers[i - 1].nodes[k].output * increaseFactor;
						}
					}
					layers[i].nodes[j].weightsDiff[k] = increase - decayFactor * layers[i].nodes[j].weights[k];
					layers[i].nodes[j].weights[k] = layers[i].nodes[j].weights[k] + layers[i].nodes[j].weightsDiff[k];
				}
			}
			// test
			// printLayer(i);
		}
	}

	private void feedForward() {
		for (int i = 0; i < layers[0].nodes.length; i++) {
			layers[0].nodes[i].output = layers[0].inputs[i];
		}
		layers[1].inputs = layers[0].inputs;
		for (int i = 1; i < layers.length; i++) {
			layers[i].feedForward();
			test(i, 2, 1);
			if (i != layers.length - 1) {
				layers[i + 1].inputs = layers[i].getOutputs();
			}
		}
	}

	// set the weights from input to first hidden layer as -1, 0, 1
	private void setIntegerWeightsOfInputToFirstHiddenLayer() {
		layers[1].setIntegerWeithts();
	}

	// print out the weights of selected layer
	public void printLayer(int layerIndex) {
		LayerFL layer = layers[layerIndex];
		for (int j = 0; j < layer.nodes.length; j++) {
			for (int k = 0; k < layer.nodes[j].weights.length; k++) {
				System.out.println("Print: [" + layerIndex + "," + j + "," + k + "]: " + layer.nodes[j].weights[k]);
			}
		}
	}

	@Override
	public double[][] testNetwork(double[][] testSamples) {
		double[][] outputs = new double[testSamples.length][];

		for (int i = 0; i < testSamples.length; i++) {
			outputs[i] = new double[expectedOutput[0].length];
			outputs[i] = feedForward(testSamples[i]);
		}

		return outputs;
	}

	private double[] feedForward(double[] inputs) {
		// Input layer's output is equal to its input
		for (int i = 0; i < layers[0].nodes.length; i++) {
			layers[0].nodes[i].output = inputs[i];
		}

		layers[1].inputs = inputs;
		for (int i = 1; i < numOfLayers; i++) {
			if (i != numOfLayers - 1) {
				layers[i].feedForward();
				layers[i + 1].inputs = layers[i].getOutputs();
			} else
				// the last layer shouldn't limit sum with step function
				layers[i].feedForward(true);
		}

		return layers[numOfLayers - 1].getOutputs();
	}

	private void test(int layerIndex, int onlyShowedLayer) {
		if (layerIndex != onlyShowedLayer)
			return;
		StringBuilder sb0 = new StringBuilder();
		sb0.append("Layer " + layerIndex);
		sb0.append("[Weighted Sum: ");
		int length = 20;
		if (layers[layerIndex].nodes.length < length)
			length = layers[layerIndex].nodes.length;
		for (int i = 0; i < length; i++) {
			if (layerIndex == 2) {
				String s = String.format("%.2f", layers[layerIndex].nodes[i].temp);
				sb0.append(s);
			} else {
				sb0.append((int) layers[layerIndex].nodes[i].output);
			}
			sb0.append(" ");
		}
		sb0.append("]");
		System.out.println(sb0.toString());
	}

	private void test(int layerIndex, int onlyShowedLayer, int weightIndex) {
		if (layerIndex != onlyShowedLayer)
			return;
		StringBuilder sb0 = new StringBuilder();
//		sb0.append("Layer " + layerIndex);
//		sb0.append(", Neutron " + weightIndex);
		sb0.append(": ");
		// only showed length 
		int length = 20;
		if (layers[layerIndex].nodes.length < length)
			length = layers[layerIndex].nodes.length;
		for (int i = 0; i < length; i++) {
			String s = String.format("%.2f", layers[layerIndex].nodes[i].weights[weightIndex]);
			sb0.append(s);
			sb0.append(" ");
			
		}
		sb0.append("\n");
//		System.out.print(sb0.toString());
		
		String path = String.format("./result/realData(%d,%d,%d).txt", layerIndex, onlyShowedLayer, weightIndex);
		FileIO.writeToFile(sb0.toString(), path, true);
	}

	private void test(int layerIndex, int onlyShowedLayer, boolean allWeight) {
		if (layerIndex != onlyShowedLayer)
			return;
		StringBuilder sb0 = new StringBuilder();
		sb0.append("Layer " + layerIndex);
		sb0.append("[Weight all nonzero:");
		int length = 20;
		if (layers[layerIndex].nodes.length < length)
			length = layers[layerIndex].nodes.length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < layers[layerIndex].nodes[i].weights.length; j++) {
				if (layers[layerIndex].nodes[i].weights[j] != 0.0d) {
					// String s =
					// String.valueOf(layers[layerIndex].nodes[i].weights[j]);
					String s = String.format("%.2f", layers[layerIndex].nodes[i].weights[j]);
					sb0.append(s);
					sb0.append(" ");
				}
			}
		}
		sb0.append("]");
		System.out.println(sb0.toString());
	}
}
