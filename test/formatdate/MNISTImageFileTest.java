package formatdate;

import java.io.FileNotFoundException;
import java.io.IOException;

import formatdata.MNISTImageFile;
import junit.framework.TestCase;

public class MNISTImageFileTest extends TestCase {
	
	public static final String IMAGE_PATH = "/Users/yangxiao/Dropbox/Southampton learning material/Dissertation/Data/train-images-idx3-ubyte";
	
	public void testToString(){
		try {
			MNISTImageFile image = new MNISTImageFile(IMAGE_PATH, "r");
			image.setCurr(1);
			System.out.println(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
