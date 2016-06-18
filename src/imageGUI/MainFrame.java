package imageGUI;

import javax.swing.*;

public class MainFrame extends JFrame{
	
	private ImageViewer imageViewer;
	
	public MainFrame(){
		super("ImageViewer");
		
		imageViewer = new ImageViewer();
		
		getContentPane().add(imageViewer);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
