package Game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Lobby {
    private JFrame frame;
    private JLabel screen;
    private JPanel panel;
    private JButton button1;
    private JButton button_howto;
    private JButton button_exit;
    private  Character_page characterPage;
	protected Howtoplay Howtoplay;

    public Lobby(JFrame f) {
        ImageIcon background = new ImageIcon("src\\images\\background_lobby.png");
        ImageIcon start1 = new ImageIcon("src\\images\\play_button.png");
        ImageIcon how = new ImageIcon("src\\images\\how_button.png");
        ImageIcon exit = new ImageIcon("src\\images\\exit_button.png");
        frame = f;
        frame.setTitle("AnimalAdventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("src/images/IconGame.png"); 
        Image image = icon.getImage();
        frame.setIconImage(image);
        frame.setResizable(false);
        frame.setSize(700, 800);

        panel = new JPanel();
        panel.setLayout(null);

        button1 = new JButton();
        button1.setIcon(start1);

        button1.setContentAreaFilled(false);
        button1.setBorderPainted(false); // ทำให้กรอบตรงปุ่มหายไป
        button1.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
        button1.setFocusPainted(false);
        button1.setOpaque(false);
        button1.setBounds(250, 320, 200, 50);
        panel.add(button1);

        button_howto = new JButton();
        button_howto.setIcon(how);
        button_howto.setContentAreaFilled(false);
        button_howto.setBorderPainted(false); // ทำให้กรอบปุ่มหายไป
        button_howto.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
        button_howto.setFocusPainted(false);
        button_howto.setOpaque(false);
        button_howto.setBounds(250, 380, 200, 50);
        panel.add(button_howto);


        button_exit = new JButton();
        button_exit.setIcon(exit);
        button_exit.setContentAreaFilled(false);
        button_exit.setBorderPainted(false); // ทำให้กรอบปุ่มหายไป
        button_exit.setContentAreaFilled(false); // ทำให้พื้นหลังตรงขอบๆปุ่มหาย
        button_exit.setFocusPainted(false);
        button_exit.setOpaque(false);
        button_exit.setBounds(250, 440, 200, 50);
        panel.add(button_exit);

        screen = new JLabel();
        screen.setIcon(background);
        screen.setBounds(0, 0, 700, 800);
        panel.add(screen);

        frame.add(panel);
        frame.setVisible(true);
        
        //set button to character_page
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.getContentPane().removeAll();
            	frame.repaint();
                characterPage = new Character_page(frame);
                
                
            }
        });
        button_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            	System.exit(0);
            	
        
    }
});
        button_howto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.getContentPane().removeAll();
            	frame.repaint();
                Howtoplay= new Howtoplay(frame);
                
                
            }
        });
    }
}
    