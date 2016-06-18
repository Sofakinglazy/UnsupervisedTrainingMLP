package formatdata;

import java.io.*;

public class MNISTLabelFile extends RandomAccessFile{
	private int count;
	private int curr;
	private String filename;
	
	public MNISTLabelFile(String filename, String mode) throws IOException, FileNotFoundException{
		super(filename, mode);
		this.filename = filename;
		
		// The magic number should be 2049
		if (readInt() != 2049){
			System.err.println("Label Files' magic number should be 2051.");
			System.exit(0);
		}
		
		curr = 0;
		count = readInt();
	}
	
	public int label(){
		int label = 0;
		try {
			label = readUnsignedByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setCurr(curr);
		return label;
	}
	
	public void nextLabel(){
		if (curr <= count){
			try {
				skipBytes(1);
				curr++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void prevLabel(){
		if (curr > 0){
			try {
				seek(getFilePointer() - 1);
				curr--;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setCurr(int curr) {
		if (curr > 0 && curr <= count){
			try {
				seek(8 + curr - 1);
				this.curr = curr;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println(curr + " is not in the range 0 to " + count);
		}
	}
	
	public String toString(){
		String s = "";
		s = s + "The path of the file: " + filename + "\n"
				  + "status: " + curr + "/" + count; 
		return s;
	}
	
	public String status(){
		return "status: " + curr + "/" + count; 
	}

	public int getCount() {return count;}

	public int getCurr() {return curr;}

	public String getFilename() {return filename;}
	
	
}
