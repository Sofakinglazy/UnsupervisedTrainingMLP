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
		initRandomWeights();
		initRamdonBias();
	}
	
	public Node(int numOfInputs, double fixedBias){
		weights = new double[numOfInputs];
		weightsDiff = new double[numOfInputs];
		initRandomWeights();
		bias = fixedBias;
	}
	
	private void initRandomWeights(){
		for (int i = 0; i < weights.length; i++){
			weights[i] = generateSmallRandomNumber();
			weightsDiff[i] = 0;
		}
	}
	
	private void initRamdonBias(){
		bias = generateSmallRandomNumber();
		biasDiff = 0;
	}

	private double generateSmallRandomNumber() {
		return 2 * Math.random() - 1;
	}
	
	// set weights as -1, 0, 1 
	public void setIntegerWeithts(){
		for (int i = 0; i < weights.length; i++){
			weights[i] = Math.round(generateSmallRandomNumber()); // generate -1, 0, 1
			weightsDiff[i] = 0;
		}
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

