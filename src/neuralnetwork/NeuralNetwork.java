package neuralnetwork;

public class NeuralNetwork {

	private double learningRate;// user-defined
	public double[][] inputs;
	private double[][] expectedOutput;
	public double[][] actualOutput;
	private double overalError;
	private double minError;// user-defined
	private int numOfLayers;// user-defined
	private int currInputIndex;// user-defined
	private int numOfInputSets;
	private long maxNumOfIterations;// user-defined
	private double momentum;

	private Layer[] layers;

	public NeuralNetwork(
			int[] numOfNodes,
			double[][] inputSamples,
			double[][] outputSamples,
			double learningRate, 
			double momentum,
			double minError, 
			long maxNumOfIterations) {
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
				actualOutput[i][j] = outputSamples[i][j];
			}
		}
	}
	
	public void feedForward(){
		// Input layer's output is equal to its input
		for (int i = 0; i < layers[0].nodes.length; i++){
			layers[1].nodes[i].output = layers[0].inputs[i];
		}
		
		for (int i = 1; i < numOfLayers; i++){
			layers[i].feedForward();
			if (i != numOfLayers - 1){
				layers[i + 1].inputs = layers[i].getOutputs();
			}
		}
	}
	
	public void updateWeights(){
		calculateSignalError();
		backpropagateError();
	}

	private void calculateSignalError() {
		int outputLayerIndex = numOfLayers - 1;
		double sum = 0;
		for (int i = 0; i < layers[outputLayerIndex].nodes.length; i++){
			layers[outputLayerIndex].nodes[i].error = (actualOutput[currInputIndex][i] - layers[outputLayerIndex].nodes[i].output)
														* layers[outputLayerIndex].nodes[i].output 
														* (1 - layers[outputLayerIndex].nodes[i].output);
		}
		
		for (int i = numOfLayers - 2; i > 0; i--){
			for (int j = 0; j < layers[i].nodes.length; j++){
				sum = 0;
				for (int k = 0; k < layers[i+1].nodes.length; k++){
					sum = sum + layers[i+1].nodes[k].weights[j] * layers[i+1].nodes[k].error;
				}
				layers[i].nodes[j].error = layers[i].nodes[j].output * (1 - layers[i].nodes[j].output) * sum;
			}
		}
	}
	
	private void backpropagateError() {
		for (int i = numOfLayers-1; i > 0; i--){
			for (int j = 0; j < layers[i].nodes.length; j++){
				// update bias difference
				layers[i].nodes[j].biasDiff = learningRate * layers[i].nodes[j].error + layers[i].nodes[j].biasDiff;
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
	
	private void calculateOveralError(){
		overalError = 0;
		for (int i = 0; i < numOfInputSets; i++){
			for (int j = 0; j < layers[numOfLayers-1].nodes.length; j++){
				overalError = overalError + 0.5 * (Math.pow(expectedOutput[i][j] - actualOutput[i][j], 2));
			}
		}
	}
	
	public void trainNetwork(){
		int currIteration = 0;
		do{
			for (currInputIndex = 0; currInputIndex < numOfInputSets; currIteration++){
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
				currIteration++;
				calculateOveralError();
			}
		} while ((overalError > minError) && (currIteration < maxNumOfIterations));
	}
}
