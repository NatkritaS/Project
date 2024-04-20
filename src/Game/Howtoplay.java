package Game;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Howtoplay {
	private  Lobby lobby;
	private JFrame frame;
	private JLabel screen;
	private JPanel panel;

	public Howtoplay(JFrame j) {
		frame = j ;
		frame.setSize(700, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true); 

		JPanel panel = new JPanel();
	    panel.setLayout(null); 
		ImageIcon BG_Howto = new ImageIcon("src/images/Howtoplay_BG.png");
		
		screen = new JLabel();
	    screen.setIcon(BG_Howto);
	    screen.setBounds(0, 0, 700, 800);
	    panel.add(screen);
	      
	    frame.add(panel);
	    frame.setVisible(true);
		
	}
	
	
    
}
