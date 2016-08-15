package formatdate;

import java.io.File;
import java.util.Scanner;

import formatdata.FileIO;
import formatdata.Utils;
import junit.framework.TestCase;

public class FileIOTest extends TestCase {

	public void testFileIO() {
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
		String context = null;

		// path = "./dataset/awesomeMan.txt";
		// file = new File(path);
		//
		// context = FileIO.readFromFile(path); // fail
		// assertNull(context);

		path = "./data/awesomeMan.txt";
		file = new File(path);

		context = FileIO.readFromFile(path); // succeed
		assertNotNull(context);
		assertEquals(s, context);
	}

	public void testFileIOwithRealData() {
		// training data
		double[][] array = Utils.importDatasets();
		String s = Utils.arrayToString(array);
		String path = "./Data/normaliseData/train.txt";
		File file = new File(path);
		FileIO.writeToFile(s, path);
		assertTrue(file.exists());

		// testing data
		array = Utils.importDatasets("test");
		s = Utils.arrayToString(array);
		path = "./Data/normaliseData/test.txt";

		FileIO.writeToFile(s, path);
		assertTrue(file.exists());
	}
}
