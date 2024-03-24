package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Scene {


    private JLabel screen;
    private ArrayList<ImageIcon> backgrounds;
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

        backgrounds = new ArrayList<>();
        backgrounds.add(new ImageIcon("src/images/background_rainning.png"));
        backgrounds.add(new ImageIcon("src/images/background_rainning2.png"));
        backgrounds.add(new ImageIcon("src/images/background_rainning3.png"));
        backgrounds.add(new ImageIcon("src/images/background_sunny.png"));
        backgrounds.add(new ImageIcon("src/images/background_sunny2.png"));
        backgrounds.add(new ImageIcon("src/images/background_sunny3.png"));
        
        setBackground(); 
    }
      
        
    private void setBackground() {
        screen = new JLabel();
        screen.setBounds(0, 0, 700 * backgrounds.size(), 800);
        panel.add(screen, BorderLayout.CENTER);

        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                 // ตรวจการกดปุ่ม ->
                    if (index < backgrounds.size() - 1) {
                        index++; 
                    } else {
                        index = 0; 
                    }
                    screen.setIcon(backgrounds.get(index)); 
                }
            }
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });

        screen.setIcon(backgrounds.get(index));

        frame.add(panel);
    }
}
      
	
        

        
        
        
        
  
        
  

	

