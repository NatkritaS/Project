package Game;

import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Character_page {
	
	private JFrame frame;
    private JLabel screen;
    private JPanel panel;
	private JButton dragon_choose;
	private JButton chubbybird_choose;
	private JButton cutieghost_choose;
	private JButton button_back;

	
	public Character_page() {

		frame = new JFrame();
		frame.setSize(700, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true); // เพิ่มบรรทัดนี้เพื่อแสดง JFrame

		panel = new JPanel();
        panel.setLayout(null);
        
        
        
        ImageIcon choose_dragon = new ImageIcon("src\\images\\choose_dragon.png");
		dragon_choose = new JButton();
		dragon_choose.setIcon(choose_dragon);
		dragon_choose.setBorderPainted(false); // ทำให้กรอบตรงปุ่มหายไป
		dragon_choose.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
		dragon_choose.setFocusPainted(false);
		dragon_choose.setOpaque(false);
		dragon_choose.setBounds(20, 250, 200, 250);
        panel.add(dragon_choose);
        
        ImageIcon choose_chubbybird = new ImageIcon("src\\images\\choose_chubbybird.png");
        chubbybird_choose = new JButton();
        chubbybird_choose.setIcon(choose_chubbybird);
        chubbybird_choose.setBorderPainted(false); // ทำให้กรอบตรงปุ่มหายไป
        chubbybird_choose.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
		chubbybird_choose.setFocusPainted(false);
		chubbybird_choose.setOpaque(false);
		chubbybird_choose.setBounds(245, 250, 210, 250);
        panel.add(chubbybird_choose);
        
        ImageIcon choose_cutieghost = new ImageIcon("src\\images\\choose_cutieghost.png");
        cutieghost_choose = new JButton();
        cutieghost_choose.setIcon(choose_cutieghost);
        cutieghost_choose.setBorderPainted(false); // ทำให้กรอบตรงปุ่มหายไป
        cutieghost_choose.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
        cutieghost_choose.setFocusPainted(false);
        cutieghost_choose.setOpaque(false);
		cutieghost_choose.setBounds(470, 250, 200, 250);
        panel.add(cutieghost_choose);
        
        ImageIcon back = new ImageIcon("src\\images\\back_button.png");
        button_back = new JButton();
        button_back.setIcon(back);
        button_back.setBorderPainted(false); // ทำให้กรอบตรงปุ่มหายไป
        button_back.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
        button_back.setFocusPainted(false);
        button_back.setOpaque(false);
        button_back.setBounds(590 ,5 ,95, 20);
        panel.add(button_back);
        
        ImageIcon BG_Charac = new ImageIcon("src\\images\\background_character.png");
        
        screen = new JLabel();
        screen.setIcon(BG_Charac);
        screen.setBounds(0, 0, 700, 800);
        panel.add(screen);
        frame.add(panel);
        
        
        button_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // เปลี่ยนไปหน้าแรก
                Lobby l = new Lobby();
                frame.dispose(); // ปิดหน้าปัจจุบัน
            }
        });

	}
}	
