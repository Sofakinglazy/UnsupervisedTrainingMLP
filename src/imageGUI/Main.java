package imageGUI;

import java.util.Scanner;

import neuralnetwork.NeuralNetwork;
import neuralnetwork.NeuralNetworkBP;
import neuralnetwork.NeuralNetworkIO;
import neuralnetwork.NeuralNetworkTest;

public class Main {

	public static void main(String[] args) {
		ImageViewer.currImage = 6;
		MainFrame mainFrame = new MainFrame();
		
		
		// demo for BP network 
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Want to enter to demo of BP network? (Y/N)");
//		while (true){
//			if(sc.nextLine().equals("Y"))
//				break;
//		}
//		NeuralNetwork nn = demoBpNetwork();
//		
//		System.out.println("Want to save the network? (Y/N)");
//		while (true){
//			if(sc.nextLine().equals("Y"))
//				break;
//		}
//		NeuralNetworkIO.saveNeuralNetwork(nn, "bp.nn");
//		
//		System.out.println("Want to load the network? (Y/N)");
//		while (true){
//			if(sc.nextLine().equals("Y"))
//				break;
//		}
//		NeuralNetwork nn1 = NeuralNetworkIO.loadNeuralNetwork("bp.nn");
//		
//		System.out.println("Want to test the network? (Y/N)");
//		while (true){
//			if(sc.nextLine().equals("Y"))
//				break;
//		}
//		double[][] outputs = nn1.testNetwork(NeuralNetworkTest.assignInputSamples());
//		printOutputs(outputs);
	}

	private static void printOutputs(double[][] outputs) {
		for (int i = 0; i < outputs.length; i++){
			for (int j = 0; j < outputs[i].length; j++){
				System.out.println(outputs[i][j]);
			}
		}
	}

	private static NeuralNetwork demoBpNetwork() {
		double learningRate = 0.5d;
		long maxNumOfIterations = 10000;
		double minError = 1E-4d;
		double momentum = 0.1d;
		int[] numOfNodes = {3, 7, 1};

		double[][] inputSamples = NeuralNetworkTest.assignInputSamples(); // {1, 0, 0; 0, 1, 0; 0, 0, 1}
		double[][] outputSamples = NeuralNetworkTest.assignOutputSamples(); // {1; 0; 0}
		
		NeuralNetwork nn = new NeuralNetworkBP(numOfNodes, inputSamples, outputSamples, 
								learningRate, momentum, minError, maxNumOfIterations);
		
		nn.trainNetwork();
		
		return nn;
	}
	
	
}
