package Game;

import javax.swing.Icon;
import javax.swing.ImageIcon;
 
public class Chubby extends Dragon {
	    public static int xPosition;
	    public static int yPosition;
	    private int width = 700;
	    private int height = 800;
	    private ImageIcon image;
	    public static int GRAVITY = 10;
 
	    public Chubby() {
	        this.xPosition = 100;
	        this.yPosition = 350;
	        this.image = new ImageIcon("src/images/ChubbyBird.png");
	        int width = image.getIconWidth();
	        int height =image.getIconHeight();
	    }
 
	    public void moveTo(int x, int y) {
	        xPosition = x;
	        yPosition = y;
	    }
 
	    public int getxPosition() {
	        return xPosition;
	    }
 
	    public int getyPosition() {
	        return yPosition;
	    }
 
	    public static Icon getChubbyBirdImage() {
	    	ImageIcon image = new ImageIcon("src/images/ChubbyBird.png");
			return image;
	        
	    }
	    public static int flyUp() {
	    	yPosition -= GRAVITY;
	    	return yPosition;
	    	
	    }
	    public static int flyDown() {
	    	yPosition += GRAVITY;
	    	return xPosition;
	    }
	    
	    public int getChubbyBirdHeight() {
	        if (image != null) { // imageต้องไม่เป็นnull
	            return image.getIconHeight();
	        } else {
	            return 0;
			}
	    	
	    }
	}