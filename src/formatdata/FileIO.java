package formatdata;

import java.io.*;

public class FileIO {

	private static String PATH = "";
	
	private static FileOutputStream fos = null;
	private static FileInputStream fis = null;
	private static File file = null;

	public static void writeToFile(String s, String path, boolean append) {
		PATH = path;
		try {
			file = new File(path);
			fos = new FileOutputStream(file, append);

			if (!file.exists()) {
				file.createNewFile();
			}

			byte[] bytes = s.getBytes();

			fos.write(bytes);
			fos.flush();
			
			System.out.println("Successfully write the file.");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeToFile(String s) {
		writeToFile(s, "default.txt", false);
	}
	
	public static void writeToFile(String s, String path) {
		writeToFile(s, path, false);
	}
	
	public static void writeToFile(String s, boolean append) {
		writeToFile(s, "default.txt", append);
	}
	
	public static String readFromFile(String path, String encoding){
		StringBuilder sb = new StringBuilder();
		try {
			file = new File(path);
			fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, encoding));
			String line;
			
			while ((line = br.readLine()) != null){
				sb.append(line);
				sb.append("\n");
			}
			
			sb.deleteCharAt(sb.toString().length()-1);// delete the last "\n"
			
			System.out.println("Successfully read the file.");
			
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public static String readFromFile(String path){
		return readFromFile(path, "UTF-8");
	}
	
	public static String readFromFile(){
		return readFromFile(PATH, "UTF-8");
	}
}
