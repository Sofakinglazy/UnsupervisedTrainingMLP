package neuralnetwork;

import java.io.Serializable;

public class LayerFL extends Layer implements Serializable {

	private static final long serialVersionUID = -5111677653374563966L;
	private double fixedBias;

	public LayerFL(int numOfInputs, int numOfNodes) {
		super(numOfInputs, numOfNodes);
	}

	public LayerFL(int numOfInputs, int numOfNodes, double fixedBias) {
		super(numOfInputs, numOfNodes);
		this.fixedBias = fixedBias;
		for (int i = 0; i < numOfNodes; i++) {
			nodes[i] = new Node(numOfInputs, fixedBias);
		}
	}

	public void setIntegerWeithts() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].setIntegerWeithts();
		}
	}

	public double getFixedBias() {
		return fixedBias;
	}

	public void feedForward() {
		feedForward(false);
	}

	public void feedForward(boolean noOutputFunc) {
		// save the current status before update
		saveAsLastTime();
		for (int i = 0; i < nodes.length; i++) {
			sum = 0;
			for (int j = 0; j < nodes[i].weights.length; j++) {
				// check if the direct connected node is active
				// if yes, give the corresponding nodes a bias
				if (i == j)
					sum = sum + nodes[i].bias;
				sum = sum + inputs[j] * nodes[i].weights[j];
			}
			nodes[i].temp = sum;
			nodes[i].output = sum > 0 ? 1 : 0;
			nodes[i].active = sum > 0 ? true : false;
			if (noOutputFunc) {
				nodes[i].output = sum;
			}
		}
	}
	
	@SuppressWarnings("unused")
	private double stepFunction(double input){
		return input > 0 ? 1d : 0d;
	}

	private void saveAsLastTime() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].saveAsLastTime();
		}
	}

	public LayerFL cloneLayer() {
		LayerFL clone = new LayerFL(inputs.length, nodes.length);
		for (int i = 0; i < nodes.length; i++) {
			clone.nodes[i] = nodes[i].cloneNode();
		}
		return clone;
	}
	
}
