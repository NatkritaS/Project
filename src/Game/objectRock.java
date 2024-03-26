package Game;

import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import javax.swing.*;

public class objectRock extends JFrame {
	private ArrayList<ImageIcon> rock;
	private ImageIcon topRock , landRock;
	private int width = 700;
	private int height = 800;
	private JPanel panel;
	
	public objectRock() {
		this.topRock = new ImageIcon("src/images/topRock.png");
		this.landRock = new ImageIcon("src/images/landRock.png");
	}
	public void Random() {
		Random r = new Random() ;
		rock.add(topRock);
		rock.add(landRock);
		int randomPos = r.nextInt(rock.size());
		ImageIcon randomRock = rock.get(randomPos);
		
		 JLabel label = new JLabel(randomRock);
		 getContentPane().add(label, BorderLayout.CENTER);
		 panel = new JPanel() {
	        	// เมดตอดนี้เอไอแก้ให้
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                if (rock != null) {
	                	for (ImageIcon r : rock) {
	                		   g.drawImage(r.getImage(), 0, 0, getWidth(), getHeight(), null);
	                		   
	                		
	                	}
	                }
	            }
	        };
	        panel.add(label);
	        setVisible(true);
		
		
		
	}
}
	
	
	
	


