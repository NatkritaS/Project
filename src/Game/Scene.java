package Game;

import javax.swing.*;
import java.awt.*;

public class Scene {


    private JLabel screen;
    private ImageIcon backgrounds;
    private JFrame frame;
    private JPanel panel;

    public Scene() {
    	  frame = new JFrame();
          frame.setSize(700, 800);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setResizable(false);
          frame.setVisible(true);

          panel = new JPanel();
          panel.setLayout(new BorderLayout());

          backgrounds = new ImageIcon(getClass().getResource("/images/background_rainning.png"));
          screen = new JLabel(backgrounds);
          screen.setBounds(0, 0, 700, 800);
          panel.add(screen, BorderLayout.CENTER);

          frame.add(panel);
      
        
    }
}