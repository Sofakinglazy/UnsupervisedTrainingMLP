package neuralnetwork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigIO {

	private static InputStream inputStream;
	private static Properties prop; 
	
	public static Properties importPropValues(String filename) throws IOException{
		prop = new Properties();
		
		inputStream = new FileInputStream(filename);
		try{
			if (inputStream != null){
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Properties file '" + filename + "' not found.");
			}
			
		} catch (FileNotFoundException e){
			System.err.println("FileNotFoundExceptron: "+ e);
		} finally {
			inputStream.close();
		}
		
		return prop;
	}

}
