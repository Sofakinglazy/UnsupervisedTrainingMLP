package formatdate;

import java.util.Map;
import java.util.Set;

import formatdata.Utils;
import junit.framework.TestCase;

public class UtilsTest extends TestCase {

	public void testGetImagesWithSameLabel() {
		Map<Integer, int[][]> imagesWithSameLabels = Utils.getImagesWithLabel(1, "test");
		System.out.println(imagesWithSameLabels.size());
	}

	public void testFormatImagesForNeuralNetwork() {
		int label = 1;
		Map<Integer, int[][]> imagesWithSameLabels = Utils.getImagesWithLabel(label, "test");
		Set<Integer> keys = imagesWithSameLabels.keySet();

		int[][] images = Utils.formatImagesForNeuralNetwork(label, "test");

		// test the samples number
		assertEquals(images.length, keys.size());

		// test the size of each image
		int[][] image = imagesWithSameLabels.get(0);
		int size = image.length * image[0].length;
		assertEquals(images[0].length, size);

	}

	public void testFormatImagesToRowData() {
		int[][] image = assignInputSamples();

		int[] rowImage = Utils.formatImagesToRowData(image);

		assertEquals(rowImage.length, image[0].length * image.length);
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
}
