package formatdata;

import java.io.*;

public class MNISTImageFile extends RandomAccessFile{
	private int count;
	private int rows;
	private int cols;
	private int curr;
	private String filename;
	
	public MNISTImageFile(String filename, String mode) throws IOException, FileNotFoundException{
		super(filename, mode);
		this.filename = filename;
		
		// The magic number should be 2051 
		if (this.readInt() != 2051){
			System.err.println("Image Files' magic number should be 2051.");
			System.exit(0);
		}
		
		curr = 0;
		count = readInt();
		rows = readInt();
		cols = readInt();
	}
	
	public int[][] data(){
		int[][] data = new int[rows][cols];
		for (int j = 0; j < cols; j++){
			for (int i = 0; i < rows; i++){
				try {
					data[i][j] = readUnsignedByte();
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
		setCurr(curr());
		return data;
	}
	
	public void nextImage(){
		if (curr < count){
			try {
				skipBytes(rows * cols);
				curr++;
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
	
	public void prevImage(){
		if (curr > 0){
			try {
				seek(getFilePointer() - rows * cols);
				curr--;
			} catch (IOException e) {
				System.err.println(e);
			} 
		}
	}
	
	public void setCurr(int curr){
		if (curr > 0 && curr <= count){
			try {
				seek(16 + (rows * cols * (curr-1)));
				this.curr = curr;
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
	
	public int curr(){return curr;}
	public String getFilename(){return filename;}
	public int getRows(){return rows;}
	public int getCols(){return cols;}
	
}
