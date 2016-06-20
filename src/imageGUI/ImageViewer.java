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
		data = new double[WIDTH][HEIGHT];
		attainMNISTImage();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	}
	
	private void attainMNISTImage() {
		normalise();
		
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

	private void normalise() {
		try {
			MNISTImageFile MNISTImage = new MNISTImageFile(Utils.IMAGE_PATH, "r");
			MNISTImage.setCurr(2);
			int[][] dat = MNISTImage.data();
			for (int i = 0; i < WIDTH; i++){
				for (int j = 0; j < HEIGHT; j++){
	                data[j][i] = (double) dat[j][i] / 255;
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
        g2d.scale(SCALE, SCALE);
        g2d.drawImage(image, 0, 0, this);
	}
	
}
