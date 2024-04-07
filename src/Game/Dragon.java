package Game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;



public class Dragon {
    public static int xPosition;
    public static int yPosition;
    private int width = 700;
    private int height = 800;
    private ImageIcon dragonImage;
    public static int GRAVITY = 10;

    public Dragon() {
        this.xPosition = 100;
        this.yPosition = 350;
        this.dragonImage = new ImageIcon("src/images/Dragon.png");
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

    public static Icon getDragonImage() {
    	ImageIcon dragonImage = new ImageIcon("src/images/dragon.png");
		return dragonImage;
        
    }
    public static int flyup() {
    	yPosition -= GRAVITY;
    	return yPosition;
    	
    }
    public static int  flyDown() {
    	yPosition += GRAVITY;
    	return xPosition;
    }
    public int getDragonHeight() {
        if (dragonImage != null) { // dragonImageต้องไม่เป็นnull
            return dragonImage.getIconHeight();
        } else {
            return 0;
		} 	
	}
}