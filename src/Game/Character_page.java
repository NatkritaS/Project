package Game;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Character_page {
	Icon BG_Charac ;
	Lobby l = new Lobby();
	JFrame f = new JFrame();
	JLabel screen = new JLabel();
	JButton button_dragon = new JButton();
	JPanel p = new JPanel();

	public Character_page() {
		f.setBounds(250, 320, 200, 50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		ImageIcon choose_dragon = new ImageIcon("src\\Image\\choose_dragon.png");
		ImageIcon choose_chubbybird = new ImageIcon("src\\Image\\choose_chubbybird.png");
		ImageIcon choose_cutieghost = new ImageIcon("src\\Image\\choose_cutieghost.png");
		
		button_dragon = new JButton();
        button_dragon.setIcon(choose_dragon);
        button_dragon.setBorderPainted(true); // ทำให้กรอบปุ่มหายไป
        button_dragon.setContentAreaFilled(true); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
        button_dragon.setFocusPainted(false);
        button_dragon.setOpaque(false);
        button_dragon.setBounds(250, 440, 200, 50);
        p.add( button_dragon);
		
        screen.setIcon(BG_Charac );
		screen.setBounds(0, 0, 700, 800);
		f.add(screen);
		f.add(p);
		f.setVisible(true);
        
	}
}
