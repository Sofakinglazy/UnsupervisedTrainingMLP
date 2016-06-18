package formatdate;

import java.io.IOException;

import formatdata.MNISTLabelFile;
import junit.framework.TestCase;

public class MNISTImageLabelTest extends TestCase {
	public static final String LABEL_PATH = "/Users/yangxiao/Dropbox/Southampton learning material/Dissertation/Data/train-labels-idx1-ubyte";
	
	public void testToString(){
		
		try {
			MNISTLabelFile label = new MNISTLabelFile(LABEL_PATH, "r");
			System.out.println(label);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void testGetFirstLabel(){
		try {
			MNISTLabelFile labels = new MNISTLabelFile(LABEL_PATH, "r");
			
			assertEquals(60000, labels.getCount());
			
			for (int i = 1; i < 5; i++){
				labels.setCurr(i);
				int label = labels.label();
				System.out.println(label);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
