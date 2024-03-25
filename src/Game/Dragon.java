package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;



public class Dragon {
    private int xPosition;
    private int yPosition;
    private int width = 700;
    private int height = 800;
    private ImageIcon dragonImage;
    private int gravity = 10;

    public Dragon() {
        this.xPosition = 100;
        this.yPosition = 350;
        
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
    public int flyup() {
    	yPosition -= gravity;
    	return xPosition;
    }
    public int flyDown() {
    	yPosition += gravity;
    	return yPosition;
    }
    public void CheckUpDown(KeyEvent k) {
    	int press = k.getKeyCode();
    	if ( press == KeyEvent.VK_UP) {
    		flyup();
    	}
    	else if ( press == KeyEvent.VK_DOWN) {
    		flyDown();
    		
    	}
    }

		
	}

