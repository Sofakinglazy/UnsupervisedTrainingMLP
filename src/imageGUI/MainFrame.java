package imageGUI;

import javax.swing.*;

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
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
