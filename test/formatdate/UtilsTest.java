package formatdate;

import java.util.Map;
import java.util.Set;

import formatdata.ParasNotMatchException;
import formatdata.Utils;
import junit.framework.TestCase;

public class UtilsTest extends TestCase {

	public void testGetImagesWithSameLabel() {
		Map<Integer, int[][]> imagesWithSameLabels = Utils.getImagesWithLabel(1, "test");
		System.out.println(imagesWithSameLabels.size());
	}

	public void testFormatImagesForNeuralNetworkWithSameLabel() {
		int label = 1;
		Map<Integer, int[][]> imagesWithSameLabels = Utils.getImagesWithLabel(label, "test");
		Set<Integer> keys = imagesWithSameLabels.keySet();

		double[][] images = Utils.formatImagesWithSameLabelForNeuralNetwork(label, "test");

		// test the samples number
		assertEquals(images.length, keys.size());

		// test the size of each image
		int[][] image = imagesWithSameLabels.get(0);
		int size = image.length * image[0].length;
		assertEquals(images[0].length, size);

	}

	public void testFormatImagesToRowData() {
		int[][] image = assignInputSamples();

//		int[] rowImage = Utils.formatImagesToRowData(image);
//
//		assertEquals(rowImage.length, image[0].length * image.length);
	}

	private int[][] assignInputSamples() {
		int[][] inputSamples = new int[3][];
		inputSamples[0] = new int[3];
		inputSamples[1] = new int[3];
		inputSamples[2] = new int[3];
		for (int i = 0; i < 3; i++) {
			inputSamples[i] = new int[3];
			for (int j = 0; j < inputSamples[i].length; j++) {
				if (i == j) {
					inputSamples[i][j] = 1;
				} else {
					inputSamples[i][j] = 0;
				}
			}
		}
		return inputSamples;
	}
	
	public void testFormatImagesForNeuralNetwork(){
		double[][] input = Utils.formatImagesForNeuralNetwork("test");
		System.out.println(input.length);
		System.out.println(input[0].length);
	}
	
	public void testCombineTwoArrays(){
		double[][] array1 = createNewMatrix(1);
		double[][] array2 = createNewMatrix(2);
		double[][] array = null;
		try {
			array = Utils.verticalCombine(array1, array2);
		} catch (ParasNotMatchException e) {
			e.printStackTrace();
		}
		assertEquals(6, array.length);
		assertEquals(3, array[0].length);
	}

	private double[][] createNewMatrix(double value) {
		double[][] array = new double[3][];
		for (int i = 0; i < array.length; i++){
			array[i] = new double[3];
			for (int j = 0; j < array[0].length; j++){
				array[i][j] = value;
			}
		}
		return array;
	}
	
	public void testHorizontalCombineTwoArray() {
		double[][] array1 = createNewMatrix(1);
		double[][] array2 = createNewMatrix(2);
		try {
			double[][] array = Utils.horizontalCombine(array1, array2);
			assertEquals(3, array.length);
			assertEquals(6, array[0].length);
		} catch (ParasNotMatchException e) {
			e.printStackTrace();
		}
		
	}
}
