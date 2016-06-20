package neuralnetwork;

public class Node {
	
	public double output;
	public double[] weights;
	public double bias;
	public double[] weightsDiff;
	public double biasDiff;
	public double error;
	
	public Node(int numOfNodes){
		weights = new double[numOfNodes];
		weightsDiff = new double[numOfNodes];
		initWeightsAndBias();
	}

	private void initWeightsAndBias() {
		bias = generateSmallRandomNumber();
		biasDiff = 0;
		
		for (int i = 0; i < weights.length; i++){
			weights[i] = generateSmallRandomNumber();
			weightsDiff[i] = 0;
		}
	}

	private double generateSmallRandomNumber() {
		return 2 * Math.random() - 1;
	}
	
	public String toString(){
		String s = "#NODE#\n";
		s += "Weights: ";
		for (int i = 0; i < weights.length; i++){
			s = s + weights[i] + " ";
		}
		s += "\n WeightsDiff: ";
		for (int i = 0; i < weights.length; i++){
			s = s + weightsDiff[i] + " ";
		}
		s = s + "\n Output: " + output + 
				"\n Bias: " + bias + 
				"\n Error: " + error;
		return s;
	}
}

