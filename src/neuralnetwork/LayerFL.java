package neuralnetwork;

import java.io.Serializable;

public class LayerFL extends Layer implements Serializable{
	
	public LayerFL(int numOfInputs, int numOfNodes){
		super(numOfInputs, numOfNodes);
	}
	
	public LayerFL(int numOfInputs, int numOfNodes, double fixedBias){
		super(numOfInputs, numOfNodes);
		for (int i = 0; i < numOfNodes; i++){
			nodes[i] = new Node(numOfInputs, fixedBias);
		}
	}
	
	public void feedForward(){
		for (int i = 0; i < nodes.length; i++){
			sum = nodes[i].bias;
			for (int j = 0; j < nodes[i].weights.length; j++){
				sum = sum + inputs[j] * nodes[i].weights[j];
			}
			nodes[i].output = sum > 0 ? 1 : 0;
		}
	}
}