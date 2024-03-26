package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
 
public class Scene {
    private JFrame frame;
    private JPanel panel;
    private JLabel dragonLabel;
    private ImageIcon background;
 
    public Scene() {
        frame = new JFrame();
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
 
        panel = new JPanel() {
        	// เมดตอดนี้เอไอแก้ให้
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        
        panel.setLayout(null);
 
        background = new ImageIcon("src/images/background_sunny.png");
 
        Dragon d = new Dragon();
        dragonLabel = new JLabel(d.getDragonImage());
        dragonLabel.setBounds(d.getxPosition(), d.getyPosition(), 130 , 100);
        panel.add(dragonLabel);
 
        frame.add(panel);
        frame.setVisible(true);
 
        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            
            public void keyPressed(KeyEvent e) {
                d.CheckUpDown(e);
                dragonLabel.setBounds(d.getxPosition(), d.getyPosition(), 130, 100);
                panel.repaint();
            }
 
            public void keyReleased(KeyEvent e) {}
        });
    }
}
 