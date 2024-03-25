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
        frame.setVisible(true);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        setBackground(); 

        
      
		
    }
      
        
    private void setBackground() {
        screen = new JLabel();
        ImageIcon backgrounds = new ImageIcon("src/images/background_sunny.png");
        screen.setIcon(backgrounds);
        screen.setBounds(0, 0, 700 , 800);
        panel.add(screen, BorderLayout.CENTER);
        

        frame.add(panel);
    }
}

	
	

      
	
        

        
        
        
        
  
        
  

	

