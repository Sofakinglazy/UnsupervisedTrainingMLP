package neuralnetwork;

public interface NeuralNetwork {

	public void trainNetwork();
	public double[][] testNetwork(double[][] testSamples);
	public void printLayer(int layerIndex);
}
