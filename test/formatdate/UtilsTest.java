package formatdate;

import java.util.Map;

import formatdata.Utils;
import junit.framework.TestCase;

public class UtilsTest extends TestCase {
	
	public void testGetImagesWithSameLabel(){
		Map<Integer, int[][]> imagesWithSameLabels = Utils.getImagesWithLabel(1);
		System.out.println(imagesWithSameLabels.size());
	}

}
