package formatdata;

import java.io.IOException;
import java.util.*;

public class Utils {
	
	public static final String IMAGE_PATH = "/Users/yangxiao/Dropbox/Southampton learning material/Dissertation/Data/train-images-idx3-ubyte";
	public static final String LABEL_PATH = "/Users/yangxiao/Dropbox/Southampton learning material/Dissertation/Data/train-labels-idx1-ubyte";
	public static final String TEST_IMAGE_PATH = "/Users/yangxiao/Dropbox/Southampton learning material/Dissertation/Data/t10k-images-idx3-ubyte";
	public static final String TEST_LABEL_PATH = "/Users/yangxiao/Dropbox/Southampton learning material/Dissertation/Data/t10k-labels-idx1-ubyte";
	
	public static Map<Integer, int[][]> getImagesWithLabel(int label, String mode){
		if (label > 9 && label < 0) {
			System.err.println("The label has to be from 0 to 9.");
			System.exit(0);
		}
		
		String imagePath = IMAGE_PATH;
		String labelPath = LABEL_PATH;
		
		if (mode.equals("test")){
			imagePath = TEST_IMAGE_PATH;
			labelPath = TEST_LABEL_PATH;
		}
		
		Set<Integer> index = null;
		Map<Integer, int[][]> imagesWithSameLabel = null;
		
		try {
			MNISTLabelFile MNISTLabel = new MNISTLabelFile(labelPath, "r");
			index = new HashSet<Integer>();
			for (int i = 1; i < MNISTLabel.getCount(); i++){
				MNISTLabel.setCurr(i);
				if (MNISTLabel.label() == label)
					index.add(i);
			}
			MNISTLabel.close();
			
			MNISTImageFile MNISTImage = new MNISTImageFile(imagePath, "r");
			int[][] dat = null;
			imagesWithSameLabel = new HashMap<Integer, int[][]>(index.size());
			int key = 0; // key starts from 0 
			for (Integer i : index){
				MNISTImage.setCurr(i);
				dat = MNISTImage.data();
				imagesWithSameLabel.put(key, dat);
				key++;
			}
			MNISTImage.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return imagesWithSameLabel;
	}
	
	public static Map<Integer, int[][]> getImagesWithLabel(int label){
		return getImagesWithLabel(label, "");
	}
	
	// Format image data to match the inputs of neural network 
	public static int[][] formatImagesForNeuralNetwork(int label, String mode){
		Map<Integer, int[][]> map = getImagesWithLabel(label, mode);
		Set<Integer> keys = map.keySet();
		int[][] images = new int[keys.size()][];
		int[] image = null;
		for (Integer i : keys){
			image = formatImagesToRowData(map.get(i));
			images[i] = image;
		}
		return images;
	}
	public static int[][] formatImagesForNeuralNetwork(int label){
		return formatImagesForNeuralNetwork(label, "");
	}
	
	public static int[] formatImagesToRowData(int[][] image){
		int rows = image.length;
		int cols = image[0].length;
		int size = rows * cols;
		int[] rowImage = new int[size];
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				rowImage[i * cols + j] = image[i][j];
			}
		}
		return rowImage;
	}
}
