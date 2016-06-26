package neuralnetwork;

public class Layer {
	
	private double sum; 
	public double[] inputs;
	public Node[] nodes;
	
	public Layer(int numOfInputs, int numOfNodes){
		inputs = new double[numOfInputs];
		nodes = new Node[numOfNodes];
		for (int i = 0; i < numOfNodes; i++){
			nodes[i] = new Node(numOfInputs);
		}
	}
	
	public void feedForward(){
		for (int i = 0; i < nodes.length; i++){
			sum = nodes[i].bias;
			for (int j = 0; j < nodes[i].weights.length; j++){
				sum += nodes[i].weights[j] * inputs[j];
			}
			nodes[i].output = sigmoid(sum);
		}
	}

	private double sigmoid(double sum) {
		return 1.0 / (1.0 + Math.exp(-sum));
	}
	
	public double[] getOutputs(){
		double[] outputs = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++){
			outputs[i] = nodes[i].output;
		}
		return outputs;
	}
	
	public String toString(){
		String s = "#LAYER#\n";
		s += "Inputs: ";
		for (int i = 0; i < inputs.length; i++){
			s = s + inputs[i] + " ";
		}
		s += "\nOutputs: ";
		double[] outputs = getOutputs();
		for (int i = 0; i < outputs.length; i++){
			s = s + outputs[i] + " ";
		}
		return s;
	}
	
	//Test
//	public void setInputs(double[] inputs){
//		if (this.inputs.length != inputs.length) System.err.println("The nunmber of inputs doesnt match.");
//		for (int i = 0; i < inputs.length; i++){
//			this.inputs[i] = inputs[i];
//		}
//	}
}
