package neuralnetwork;

import java.io.Serializable;

import loggerutils.LoggerUtils;

public class NeuralNetworkFL implements NeuralNetwork, Serializable {

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

//		 LoggerUtils.createLogger();
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
				if (currSampleIndex >= (numOfInputSet - 5)){
					calculateWeightsError();
				}
			}
			calculateWeightsErrorBetweenDiffIteration(lastTime);
			currIteration++;
			System.out.println("Current Iteration: " + currIteration);
		} while (currIteration < maxNumOfIterations);
//		printLayer(3);
		System.out.println("here");
	}

	private LayerFL[] cloneLayers() {
		LayerFL[] clone = new LayerFL[layers.length];
		for (int i = 0; i < layers.length; i++){
			clone[i] = layers[i].cloneLayer();
		}
		return clone;
	}
	
	private void calculateWeightsErrorBetweenDiffIteration(LayerFL[] lastTime){
		double error = 0d;
		for (int i = 1; i < numOfLayers; i++){
			for (int j = 0; j < layers[i].nodes.length; j++){
				for (int k = 0; k < layers[i].nodes[j].weights.length; k++){
					error += squareError(layers[i].nodes[j].weights[k], lastTime[i].nodes[j].weights[k]);
				}
			}
		}
		System.out.println("Weight Error between two iterations is: " + error);
	}

	private void calculateWeightsError() {
		double error = 0d;
		for (int i = 1; i < numOfLayers; i++){
			for (int j = 0; j < layers[i].nodes.length; j++){
				for (int k = 0; k < layers[i].nodes[j].weights.length; k++){
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
					// test
//					System.out.println("[" + i + "," + j + "," + k + "]: " + layers[i].nodes[j].weights[k]);

					double increase = 0d;
					// only when last time the corresponding node is active will
					// pass its bias to this one
					if (layers[i - 1].nodes[j].lastTime != null) {
						if (layers[i - 1].nodes[j].lastTime.active) {
							increase = layers[i - 1].nodes[k].output * increaseFactor;
						}
					}
					layers[i].nodes[j].weightsDiff[k] = increase - decayFactor * layers[i].nodes[j].weights[k];
					layers[i].nodes[j].weights[k] = layers[i].nodes[j].weights[k] + layers[i].nodes[j].weightsDiff[k];
				}
			}
			// test
			printLayer(i);
		}
	}

	private void feedForward() {
		for (int i = 0; i < layers[0].nodes.length; i++) {
			layers[0].nodes[i].output = layers[0].inputs[i];
		}
		layers[1].inputs = layers[0].inputs;
		for (int i = 1; i < layers.length; i++) {
			layers[i].feedForward();
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
	private void printLayer(int layerIndex){
		LayerFL layer = layers[layerIndex];
		for (int j = 0; j < layer.nodes.length; j++){
			for (int k = 0; k < layer.nodes[j].weights.length; k++){
				System.out.println("Print: [" + layerIndex + "," + j + "," + k + "]: " + layer.nodes[j].weights[k]);
			}
		}
	}

	@Override
	public double[][] testNetwork(double[][] testSamples) {
		// TODO Auto-generated method stub
		return null;
	}

}
