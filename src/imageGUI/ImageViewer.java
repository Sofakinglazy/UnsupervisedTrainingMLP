package imageGUI;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.swing.*;

import formatdata.MNISTImageFile;

public class ImageViewer extends JPanel{

	public static final int WIDTH = 28;
	public static final int HEIGHT = 28;
	public static final int SCALE = 6;

	
	private BufferedImage image;
	private double[][] data;
	
	public ImageViewer(){
		super();
		attainMNISTImage();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	}
	
	private void attainMNISTImage() {
		getMNISTImage();
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                float c = (float) data[j][i];
                g.setColor(new Color(c, c, c));
                g.fillRect(i, j, 1, 1);
            }
        }
	}

	private void getMNISTImage() {
		try {
			MNISTImageFile MNISTImage = new MNISTImageFile(Utils.IMAGE_PATH, "r");
			MNISTImage.setCurr(0);
			int[][] dat = MNISTImage.data();
			data = new double[WIDTH][HEIGHT];
			for (int i = 0; i < WIDTH; i++){
				for (int j = 0; j < HEIGHT; j++){
	                data[j][i] = (double) dat[j][i] / 255;
	                System.out.println(data[i][j]);
				}
			}
			MNISTImage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.scale(6, 6);
        g2d.drawImage(image, 0, 0, this);
	}
	
	
}
