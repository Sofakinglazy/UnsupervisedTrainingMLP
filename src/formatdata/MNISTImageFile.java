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
		for (int i = 0; i < rows; i++){
			System.out.println();
			for (int j = 0; j < cols; j++){
				try {
					data[i][j] = readUnsignedByte();
					System.out.print(data[i][j] + " ");
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
		setCurr(curr);
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
		} else {
			System.err.println(curr + " is not in the range 0 to " + count);
		}
	}
	
	public String toString(){
		String s = "";
		s = s + "The path of the file: " + filename + "\n"
			  + "rows: " + rows + ", cols: " + cols + "\n"
			  + "status: " + curr + "/" + count; 
		return s;
	}
	
	public String status(){
		return "status: " + curr + "/" + count;
	}
	
	public int getCurr(){return curr;}
	public String getFilename(){return filename;}
	public int getRows(){return rows;}
	public int getCols(){return cols;}
	
	public long getPointer() throws IOException{
		return getFilePointer();
	}
}
