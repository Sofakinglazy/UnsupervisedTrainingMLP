package formatdata;

import java.io.IOException;
import java.util.*;

public class Utils {
	
	public static final String IMAGE_PATH = "/Users/yangxiao/Dropbox/Southampton learning material/Dissertation/Data/train-images-idx3-ubyte";
	public static final String LABEL_PATH = "/Users/yangxiao/Dropbox/Southampton learning material/Dissertation/Data/train-labels-idx1-ubyte";
	
	public static Map<Integer, int[][]> getImagesWithLabel(int label){
		Set<Integer> index = null;
		Map<Integer, int[][]> imagesWithSameLabel = null;
		
		try {
			MNISTLabelFile MNISTLabel = new MNISTLabelFile(LABEL_PATH, "r");
			index = new HashSet<Integer>();
			for (int i = 0; i < MNISTLabel.getCount(); i++){
				MNISTLabel.setCurr(i);
				if (MNISTLabel.label() == label)
					index.add(i);
			}
			MNISTLabel.close();
			
			// test 
			System.out.println(index);
			
			MNISTImageFile MNISTImage = new MNISTImageFile(IMAGE_PATH, "r");
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
}
