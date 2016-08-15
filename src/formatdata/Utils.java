package formatdata;

import java.io.IOException;
import java.util.*;

public class Utils {

//	public static final String CURRENT_PATH = Paths.get("").toAbsolutePath().toString();
	public static final String IMAGE_PATH = "./Data/train-images-idx3-ubyte";
	public static final String LABEL_PATH = "./Data/train-labels-idx1-ubyte";
	public static final String TEST_IMAGE_PATH = "./Data/t10k-images-idx3-ubyte";
	public static final String TEST_LABEL_PATH = "./Data/t10k-labels-idx1-ubyte";
	
	public static final double FULL_VALUE_PIXEL = 255d;

	public static Map<Integer, int[][]> getImagesWithLabel(int label, String mode) {
		if (label > 9 && label < 0) {
			System.err.println("The label has to be from 0 to 9.");
			System.exit(0);
		}

		String imagePath = IMAGE_PATH;
		String labelPath = LABEL_PATH;

		if (mode.equals("test")) {
			imagePath = TEST_IMAGE_PATH;
			labelPath = TEST_LABEL_PATH;
		}

		Set<Integer> index = null;
		Map<Integer, int[][]> imagesWithSameLabel = null;

		try {
			MNISTLabelFile MNISTLabel = new MNISTLabelFile(labelPath, "r");
			index = new HashSet<Integer>();
			for (int i = 1; i < MNISTLabel.getCount(); i++) {
				MNISTLabel.setCurr(i);
				if (MNISTLabel.label() == label)
					index.add(i);
			}
			MNISTLabel.close();

			MNISTImageFile MNISTImage = new MNISTImageFile(imagePath, "r");
			int[][] dat = null;
			imagesWithSameLabel = new HashMap<Integer, int[][]>(index.size());
			int key = 0; // key starts from 0
			for (Integer i : index) {
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

	public static Map<Integer, int[][]> getImagesWithLabel(int label) {
		return getImagesWithLabel(label, "");
	}

	// Format image data to match the inputs of neural network
	public static double[][] formatImagesWithSameLabelForNeuralNetwork(int label, String mode) {
		Map<Integer, int[][]> map = getImagesWithLabel(label, mode);
		Set<Integer> keys = map.keySet();
		double[][] images = new double[keys.size()][];
		for (Integer i : keys) {
			images[i] = formatImagesToRowData(map.get(i));
//			System.out.println("Finish extracting No." + i + " image data.");
		}
		return images;
	}

	public static double[][] formatImagesWithSameLabelForNeuralNetwork(int label) {
		return formatImagesWithSameLabelForNeuralNetwork(label, "");
	}

	public static double[][] formatImagesForNeuralNetwork(String mode) {
		String imagePath = IMAGE_PATH;

		if (mode.equals("test")) {
			imagePath = TEST_IMAGE_PATH;
		}

		double[][] data = null;
		try {
			MNISTImageFile MNISTImage = new MNISTImageFile(imagePath, "r");
			int count = MNISTImage.getCount();
			data = new double[count][];
			for (int i = 1; i <= count; i++) {
				MNISTImage.setCurr(i);
				double[] rowImage = formatImagesToRowData(MNISTImage.data());
				data[i - 1] = rowImage;
				System.out.println("Finish extracting No." + i + " image data.");
			}
			MNISTImage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	public static double[][] formatImagesForNeuralNetwork() {
		return formatImagesForNeuralNetwork("");
	}

	public static double[][] formatLabelsForNeuralNetwork(String mode) {
		String labelPath = LABEL_PATH;

		if (mode.equals("test")) {
			labelPath = TEST_LABEL_PATH;
		}

		double[][] label = null;
		try {
			MNISTLabelFile MNISTLabel = new MNISTLabelFile(labelPath, "r");
			int count = MNISTLabel.getCount();
			label = new double[count][];
			for (int i = 1; i <= count; i++) {
				MNISTLabel.setCurr(i);
				label[i - 1] = new double[10];
				label[i - 1] = formatNeuralNetworkOutputWithLabel(MNISTLabel.label());
				System.out.println("Finish extracting No." + i + " image label.");
			}
			MNISTLabel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return label;
	}

	public static double[][] formatLabelsForNeuralNetwork() {
		return formatLabelsForNeuralNetwork("");
	}

	private static double[] formatImagesToRowData(int[][] image) {
		int rows = image.length;
		int cols = image[0].length;
		int size = rows * cols;
		double[] rowImage = new double[size];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rowImage[i * cols + j] = normalise((double) image[i][j]);
			}
		}
		return rowImage;
	}
	
	private static double[] formatNeuralNetworkOutputWithLabel(int label){
		double[] output = new double[10];
		for (int i = 0; i < 10; i++){
			if (i == label){
				output[i] = 1d;
			}
		}
		return output;
	}
	
	private static double normalise(double original){
		return original/FULL_VALUE_PIXEL;
	}
	
	public static double[][] verticalCombine(double[][] array1, double[][] array2) throws ParasNotMatchException {
		if (array1[0].length != array2[0].length) {
			throw new ParasNotMatchException("Two arrays have different rows.");
		}
		int rows = array1.length + array2.length;
		double[][] array = new double[rows][];
		for (int i = 0; i < rows; i++) {
			array[i] = new double[array1[0].length];
			if (i < array1.length) {
				for (int j = 0; j < array1[0].length; j++) {
					array[i][j] = array1[i][j];
				}
			} else {
				for (int j = 0; j < array2[0].length; j++) {
					array[i][j] = array2[i-array1.length][j];
				}
			}
		}
		return array;
	}
	
	public static double[][] horizontalCombine(double[][] array1, double[][] array2) throws ParasNotMatchException{
		if (array1.length != array2.length){
			throw new ParasNotMatchException("Two arrays have different rows.");
		}
		int cols = array1[0].length + array2[0].length;
		double[][] array = new double[array1.length][];
		for (int i = 0; i < array.length; i++){
			array[i] = new double[cols];
			for (int j = 0; j < cols; j++){
				if (j < array1[0].length){
					array[i][j] = array1[i][j];
				} else {
					array[i][j] = array2[i][j-array1[0].length];
				}
			}
		}
		return array;
	}
	
	public static String arrayToString(double[][] array, String symbol){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++){
			for (int j = 0; j < array[i].length; j++){
				sb.append(array[i][j]);
				sb.append(symbol);
			}
			sb.append("\n");
//			System.out.println("Finished " + i + " row.");
		}
		System.out.println("Done translating array to a string.");
		return sb.toString();
	}
	
	public static String arrayToString(double[][] array){
		return arrayToString(array, ",");
	}
	
	public static double[][] importDatasets(String mode) {
		double[][] images = null;
		double[][] labels = null;
		if(mode.isEmpty()){
			images = Utils.formatImagesForNeuralNetwork();
			labels = Utils.formatLabelsForNeuralNetwork();
		}
		else {
			images = Utils.formatImagesForNeuralNetwork(mode);
			labels = Utils.formatLabelsForNeuralNetwork(mode);
		}
		
		double[][] datasets = null;
		try {
			datasets = Utils.horizontalCombine(images, labels);
		} catch (ParasNotMatchException e) {
			e.printStackTrace();
		}
		
		return datasets;
	}
	
	public static double[][] importDatasets(){
		return importDatasets("");
	}

	@SuppressWarnings("unused")
	private static void test(double[][] array) {
		System.out.println(arrayToString(array, ", "));
	}
}
