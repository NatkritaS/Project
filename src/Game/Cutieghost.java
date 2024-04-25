package Game;

import javax.swing.Icon;
import javax.swing.ImageIcon;
 
public class Cutieghost extends Dragon {
	    public static int xPosition;
	    public static int yPosition;
	    private int width = 700;
	    private int height = 800;
	    private ImageIcon cutieimage;
	    public static int GRAVITY = 10;
 
	    public Cutieghost() {
	        this.xPosition = 100;
	        this.yPosition = 350;
	        this.cutieimage = new ImageIcon("src/images/CutieGhost.png");
	        int width = cutieimage.getIconWidth();
	        int height =cutieimage.getIconHeight();
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
 
	    public static Icon getCutieghostImage() {
	    	ImageIcon image = new ImageIcon("src/images/CutieGhost.png");
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
	    
	    public int getCutieghostHeight() {
	        if (cutieimage != null) { // imageต้องไม่เป็นnull
	            return cutieimage.getIconHeight();
	        } else {
	            return 0;
			}
	    	
	    }
	}