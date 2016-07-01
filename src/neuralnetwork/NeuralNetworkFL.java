package neuralnetwork;

import java.io.Serializable;

public class NeuralNetworkFL implements NeuralNetwork, Serializable{
	
	private double increaseFactor;
	private double decreaseFactor;
	private double fixedBias;
	private double[][] inputs;
	private double[][] expectedOutput;
	private double[][] actualOutput; 
	private int numOfInputSet;
	private int currSampleIndex;
	private long maxNumOfIterations;
	
	private LayerFL[] layers;

	public NeuralNetworkFL(int[] numOfLayers, 
						double[][] inputSamples,
						double[][] outputSameples, 
						long maxNumOfIterations, 
						double increaseFactor, 
						double decreaseFactor,
						double fixedBias){
		this.numOfInputSet = inputSamples.length;
		this.maxNumOfIterations = maxNumOfIterations;
		this.increaseFactor = increaseFactor;
		this.decreaseFactor = decreaseFactor;
		this.fixedBias = fixedBias;
		
		inputs = new double[numOfInputSet][numOfLayers[0]];
		expectedOutput = new double[numOfInputSet][numOfLayers[numOfLayers.length-1]];
		actualOutput = new double[numOfInputSet][numOfLayers[numOfLayers.length-1]];
		
		for (int i = 0; i < inputSamples.length; i++){
			for (int j = 0; j < inputSamples[i].length; j++){
				inputs[i][j] = inputSamples[i][j];
			}
		}
		
		for (int i = 0; i < outputSameples.length; i++){
			for (int j = 0; j < outputSameples[i].length; j++){
				expectedOutput[i][j] = outputSameples[i][j];
			}
		}
		
		layers = new LayerFL[numOfLayers.length];
		layers[0] = new LayerFL(numOfLayers[0], numOfLayers[0]);// input layer
		for (int i = 1; i < numOfLayers.length; i++){
			layers[i] = new LayerFL(numOfLayers[i-1], numOfLayers[i], fixedBias); 
		} // hidden layer and output layer (bias are the same)
		
	}

	@Override
	public void trainNetwork() {
		int currIteration = 0;
		do {
			for (currSampleIndex = 0; currSampleIndex < numOfInputSet; currSampleIndex++){
				for (int i = 0; i < inputs[currSampleIndex].length; i++){
					layers[0].inputs[i] = inputs[currSampleIndex][i];
				}
				feedForward();
				
			}
			currIteration++;
		} while (currIteration > maxNumOfIterations);
	}

	private void feedForward() {
		for (int i = 0; i < layers[0].nodes.length; i++){
			layers[0].nodes[i].output = layers[0].inputs[i];
		}
		layers[1].inputs = layers[0].inputs;
		setIntegerWeightsOfInputToFirstHiddenLayer();
		for (int i = 1; i < layers.length; i++){
			layers[i].feedForward();
			if (i != layers.length-1){
				layers[i+1].inputs = layers[i].getOutputs();
			}
		}
	}

	// set the weights from input to first hidden layer as -1, 0, 1
	private void setIntegerWeightsOfInputToFirstHiddenLayer() {
		for (int i = 0; i < layers[1].nodes.length; i++){
			layers[1].nodes[i].setIntegerWeithts();
		}
	}

	@Override
	public double[][] testNetwork(double[][] testSamples) {
		// TODO Auto-generated method stub
		return null;
	}

}