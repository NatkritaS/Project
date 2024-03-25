package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Scene {
	private JLabel screen;
    private ImageIcon backgrounds;
    private JFrame frame;
    private JPanel panel;
    private int index;

    public Scene() {
        frame = new JFrame();
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        setBackground();
        Dragon d = new Dragon();
        JLabel dragonLabel = new JLabel(d.getDragonImage());
        dragonLabel.setBounds(d.getxPosition(), d.getyPosition(), 100 , 100);
        panel.add(dragonLabel);
        panel.setVisible(true);
        
        frame.add(panel);
        frame.setVisible(true);
        
        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}

           
            public void keyPressed(KeyEvent e) {
                d.CheckUpDown(e);
                dragonLabel.setBounds(d.getxPosition(), d.getyPosition(), 200, 100);
                panel.repaint();
            }
            public void keyReleased(KeyEvent e) {}
        });
    }
    
      
        
    private void setBackground() {
        screen = new JLabel();
        ImageIcon backgrounds = new ImageIcon("src/images/background_sunny.png");
        screen.setBackground(null);
        //screen.setIcon(backgrounds);
        screen.setBounds(0, 0, 700 , 800);
        panel.add(screen);
        
        

        
    }
}

	
	

      
	
        

        
        
        
        
  
        
  

	

