package formatdate;

import java.io.File;

import formatdata.FileIO;
import junit.framework.TestCase;

public class FileIOTest extends TestCase {

	public void testFileIO(){
		String s = "I am an awesome man.";
		String path = "awesomeMan.txt";
		File file = new File(path);
		
		assertFalse(file.exists());
		FileIO.writeToFile(s, path);
		assertTrue(file.exists());
		file.deleteOnExit();
		
		path = "./data/awesomeMan.txt";
		file = new File(path);
		
		assertFalse(file.exists());
		FileIO.writeToFile(s, path);
		assertTrue(file.exists());
		file.deleteOnExit();
		
		// read string 
		path = "./dataset/awesomeMan.txt";
		file = new File(path);
		
		String context = FileIO.readFromFile(path); // fail
		assertNull(context);
		
		path = "./data/awesomeMan.txt";
		file = new File(path);
		
		context = FileIO.readFromFile(path); // succeed
		assertNotNull(context);
		assertEquals(s, context);
	}
	
	
}
