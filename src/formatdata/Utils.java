package formatdata;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Utils {

	public static final String CURRENT_PATH = Paths.get("").toAbsolutePath().toString();
	public static final String IMAGE_PATH = CURRENT_PATH + "/Data/train-images-idx3-ubyte";
	public static final String LABEL_PATH = CURRENT_PATH + "/Data/train-labels-idx1-ubyte";
	public static final String TEST_IMAGE_PATH = CURRENT_PATH + "/Data/t10k-images-idx3-ubyte";
	public static final String TEST_LABEL_PATH = CURRENT_PATH + "/Data/t10k-labels-idx1-ubyte";
	
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
			System.out.println("Finish extracting No." + i + " image data.");
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
}
