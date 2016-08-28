package neuralnetwork;

import java.io.*;

public class NeuralNetworkIO {
	private static String PATH = "";
	
	private static FileOutputStream fos = null;
	private static FileInputStream fis = null;
	private static ObjectOutputStream oos = null;
	private static ObjectInputStream ois = null;
	
	public static void saveNeuralNetwork(NeuralNetwork nn, String path){
		PATH = path;
		try {
			fos = new FileOutputStream(PATH);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(nn);
			System.out.println("Successfully save the network!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oos != null){
				try {
					fos.close();
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static NeuralNetwork loadNeuralNetwork(String path){
		NeuralNetwork nn = null;
		try {
			fis = new FileInputStream(path);
			ois = new ObjectInputStream(fis);
			nn = (NeuralNetwork) ois.readObject();
			System.out.println("Successfully load the network!");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ois != null){
				try {
					fis.close();
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return nn;
	}
	
	public static NeuralNetwork loadNeuralNetwork(){
		return loadNeuralNetwork(PATH);
	}
	
}
