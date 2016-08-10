package neuralnetwork;

import java.io.*;

public class NeuralNetworkIO {
	public static String PATH = "";
	
	public static FileOutputStream fos = null;
	public static FileInputStream fis = null;
	public static ObjectOutputStream oos = null;
	public static ObjectInputStream ois = null;
	
	public static void saveNeuralNetwork(NeuralNetwork nn, String path){
		PATH = path;
		try {
			fos = new FileOutputStream(PATH);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(nn);
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
	
	public static NeuralNetwork loadNeuralNetwork(){
		NeuralNetwork nn = null;
		try {
			fis = new FileInputStream(PATH);
			ois = new ObjectInputStream(fis);
			nn = (NeuralNetwork) ois.readObject();
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
	
}
