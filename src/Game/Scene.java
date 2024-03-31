package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
 
public class Scene {
    protected static int Positiony = 0;
    
	private JFrame frame;
    private JPanel panel;
    private JLabel dragonLabel;
    private ImageIcon background;
	protected ImageIcon rock;
	
	
 
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
   
        frame.addKeyListener(new KeyAdapter() {
        	
        	private JLabel rockLabel;

        	public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int key1 = panel.getHeight() - d.getDragonHeight(); // คำนวณความสูงสูงสุดที่มังกรสามารถอยู่ได้
                if (key == KeyEvent.VK_UP) {
                    if (Positiony - Dragon.GRAVITY >= 0) {
                        Positiony -= Dragon.GRAVITY;
                    }
                    Dragon.flyup();
                } else if (key == KeyEvent.VK_DOWN) {
                    if (Positiony + Dragon.GRAVITY <= key1) {
                        Positiony += Dragon.GRAVITY;
                    }
                    Dragon.flyDown();
                }
        	    dragonLabel.setBounds(d.getxPosition(), Positiony, 130, 100);
        	    panel.revalidate();
        	    panel.repaint();
        	  
        	}
        });
    }
}