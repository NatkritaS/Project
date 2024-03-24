package Game;

import javax.swing.ImageIcon;

import javax.swing.*;

public class Character_page {
	
	private JFrame frame;
    private JLabel screen;
    private JPanel panel;
	private JButton dragon_choose;
	private JButton chubbybird_choose;
	private JButton cutieghost_choose;

	
	public Character_page() {
		frame = new JFrame();
		frame.setSize(700, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true); // เพิ่มบรรทัดนี้เพื่อแสดง JFrame

		panel = new JPanel();
        panel.setLayout(null);
        
        
        
        ImageIcon choose_dragon = new ImageIcon("D:\\java\\animalAdventure\\src\\images\\choose_dragon.png");
		dragon_choose = new JButton();
		dragon_choose.setIcon(choose_dragon);
		dragon_choose.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
		dragon_choose.setFocusPainted(false);
		dragon_choose.setOpaque(false);
		dragon_choose.setBounds(20, 250, 200, 250);
        panel.add(dragon_choose);
        
        ImageIcon choose_chubbybird = new ImageIcon("D:\\java\\animalAdventure\\src\\images\\choose_chubbybird.png");
        chubbybird_choose = new JButton();
        chubbybird_choose.setIcon(choose_chubbybird);
        chubbybird_choose.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
		chubbybird_choose.setFocusPainted(false);
		chubbybird_choose.setOpaque(false);
		chubbybird_choose.setBounds(245, 250, 210, 250);
        panel.add(chubbybird_choose);
        
        ImageIcon choose_cutieghost = new ImageIcon("D:\\java\\animalAdventure\\src\\images\\choose_cutieghost.png");
        cutieghost_choose = new JButton();
        cutieghost_choose.setIcon(choose_cutieghost);
        cutieghost_choose.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
        cutieghost_choose.setFocusPainted(false);
        cutieghost_choose.setOpaque(false);
		cutieghost_choose.setBounds(470, 250, 200, 250);
        panel.add(cutieghost_choose);
        
        ImageIcon BG_Charac = new ImageIcon("D:\\java\\animalAdventure\\src\\images\\background_character.png");
        screen = new JLabel();
        screen.setIcon(BG_Charac);
        screen.setBounds(0, 0, 700, 800);
        panel.add(screen);
        frame.add(panel);
	}
}	
