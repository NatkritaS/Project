package Game;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Character_page {
	Icon BG_Charac ;
	Lobby l = new Lobby();
	JFrame f = new JFrame();
	JLabel screen = new JLabel();

	public Character_page() {
		f.setBounds(250, 320, 200, 50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		screen.setIcon(BG_Charac );
		screen.setBounds(0, 0, 700, 800);
		f.add(screen);
		f.setVisible(true);
		
		ImageIcon choose_dragon = new ImageIcon("src\\Image\\choose_dragon.png");
		ImageIcon choose_chubbybird = new ImageIcon("src\\Image\\choose_chubbybird.png");
		ImageIcon choose_cutieghost = new ImageIcon("src\\Image\\choose_cutieghost.png");
		
	}
}
