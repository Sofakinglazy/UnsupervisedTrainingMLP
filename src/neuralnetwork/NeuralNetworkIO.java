package neuralnetwork;

import java.io.*;

public class NeuralNetworkIO {
	public static final String PATH = "ann.ser";
	
	public static FileOutputStream fos = null;
	public static FileInputStream fis = null;
	public static ObjectOutputStream oos = null;
	public static ObjectInputStream ois = null;
	
	public static void saveNeuralNetwork(NeuralNetworkBP nn){
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
	
	public static NeuralNetworkBP loadNeuralNetwork(){
		NeuralNetworkBP nn = null;
		try {
			fis = new FileInputStream(PATH);
			ois = new ObjectInputStream(fis);
			nn = (NeuralNetworkBP) ois.readObject();
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
