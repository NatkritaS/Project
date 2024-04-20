package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Howtoplay {
	private  Lobby lobby;
	private JFrame frame;
	private JLabel screen;
	private JPanel panel;
	private JButton button_back;

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
	    
	    
	    
	    ImageIcon back = new ImageIcon("src\\images\\back_button.png");
        button_back = new JButton();
        button_back.setIcon(back);
        button_back.setBorderPainted(false); // ทำให้กรอบตรงปุ่มหายไป
        button_back.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
        button_back.setFocusPainted(false);
        button_back.setOpaque(false);
        button_back.setBounds(590 ,5 ,95, 20);
        
        panel.add(button_back);
        panel.add(screen);
       
        frame.add(panel);
	    frame.setVisible(true);

	    
	    button_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.getContentPane().removeAll();
            	frame.repaint();
                lobby = new Lobby(frame);
            }
        });
		
	}
	
	
    
}
