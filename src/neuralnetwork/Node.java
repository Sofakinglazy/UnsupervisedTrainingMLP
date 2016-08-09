package neuralnetwork;

import java.io.Serializable;
import java.util.Arrays;

public class Node implements Serializable{
	
	public double output;
	public double[] weights;
	public double bias;
	public double[] weightsDiff;
	public double biasDiff;
	public double error;
	
	public boolean active;
	public Node lastTime;
	
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
	
	public Node(Node node){
		output = node.output;
		weights = node.weights;
		weightsDiff = node.weightsDiff;
		bias = node.bias;
		biasDiff = node.biasDiff;
		error = node.error;
		active = node.active;
		lastTime = node.lastTime;
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
	
	public void saveAsLastTime(){
		lastTime = new Node(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(bias);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(biasDiff);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(error);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lastTime == null) ? 0 : lastTime.hashCode());
		temp = Double.doubleToLongBits(output);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(weights);
		result = prime * result + Arrays.hashCode(weightsDiff);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (active != other.active)
			return false;
		if (Double.doubleToLongBits(bias) != Double.doubleToLongBits(other.bias))
			return false;
		if (Double.doubleToLongBits(biasDiff) != Double.doubleToLongBits(other.biasDiff))
			return false;
		if (Double.doubleToLongBits(error) != Double.doubleToLongBits(other.error))
			return false;
		if (lastTime == null) {
			if (other.lastTime != null)
				return false;
		} else if (!lastTime.equals(other.lastTime))
			return false;
		if (Double.doubleToLongBits(output) != Double.doubleToLongBits(other.output))
			return false;
		if (!Arrays.equals(weights, other.weights))
			return false;
		if (!Arrays.equals(weightsDiff, other.weightsDiff))
			return false;
		return true;
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

