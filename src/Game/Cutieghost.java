package Game;

import javax.swing.Icon;
import javax.swing.ImageIcon;
 
public class Cutieghost extends Dragon {
	    public static int xPosition;
	    public static int yPosition;
	    private ImageIcon cutieimage;
	    public static int GRAVITY = 20;
	    private boolean invincible;
	    private int invincibleCount;
 
	    public Cutieghost() {
	        this.xPosition = 100;
	        this.yPosition = 350;
	        this.cutieimage = new ImageIcon("src/images/CutieGhost.png");
	        this.invincible = false;
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
	    	ImageIcon cutieimage = new ImageIcon("src/images/CutieGhost.png");
			return cutieimage;
	        
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
	    public boolean isInvincible() {
	        return invincible;
	    }

	    public void setInvincible(boolean invincible) {
	        this.invincible = invincible;
	    }
	    
	    public int getInvincibleCount() {
	        return invincibleCount;
	    }

	    public void setInvincibleCount(int count) {
	        this.invincibleCount = count;
	    }
	}