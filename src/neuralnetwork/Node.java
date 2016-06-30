package neuralnetwork;

import java.io.Serializable;

public class Node implements Serializable{
	
	public double output;
	public double[] weights;
	public double bias;
	public double[] weightsDiff;
	public double biasDiff;
	public double error;
	
	public Node(int numOfInputs){
		weights = new double[numOfInputs];
		weightsDiff = new double[numOfInputs];
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
		s += "\nWeightsDiff: ";
		for (int i = 0; i < weights.length; i++){
			s = s + weightsDiff[i] + " ";
		}
		s = s + "\nOutput: " + output + 
				"\nBias: " + bias + 
				"\nError: " + error;
		return s;
	}
}

