package formatdate;

import java.io.FileNotFoundException;
import java.io.IOException;

import formatdata.MNISTImageFile;
import junit.framework.TestCase;

public class MNISTImageFileTest extends TestCase {
	
	private final String IMAGE_PATH = "/Users/yangxiao/Dropbox/Southampton learning material/Dissertation/Data/train-images-idx3-ubyte";
	
	public void testToString(){
		try {
			MNISTImageFile image = new MNISTImageFile(IMAGE_PATH, "r");
			System.out.println(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void testExtractBytesofFirstImage(){
		try {
			MNISTImageFile image = new MNISTImageFile(IMAGE_PATH, "r");
			image.setCurr(1);
			
			
			System.out.println(image.getFilePointer());
			
			int[][] data = image.data();
			
			assertEquals(28, data[1].length);
			
			String s = "";
			for (int i = 0; i < image.getRows(); i++){
				if (i > 0)
					s += "\n";
				for (int j = 0; j < image.getCols(); j++){
					s += data[i][j];
					s += " ";
				}
			}
			
			System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
