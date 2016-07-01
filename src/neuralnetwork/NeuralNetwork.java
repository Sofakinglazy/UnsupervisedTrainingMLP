package neuralnetwork;

public interface NeuralNetwork {

	public void trainNetwork();
	public double[][] testNetwork(double[][] testSamples);
}
