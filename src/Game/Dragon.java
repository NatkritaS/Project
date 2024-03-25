package Game;
import javax.swing.*;
import java.awt.*;



public class Dragon {
    private int xPosition;
    private int yPosition;
    private int width = 700;
    private int height = 800;
    private Image dragonImage;
    private int gravity = 5;

    public Dragon() {
        this.xPosition = 100;
        this.yPosition = 400;
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

    public Image getDragonImage() {
        ImageIcon dragon = new ImageIcon("src/images/dragon.png");
    	return dragonImage;
    }
    public int flyup() {
    	yPosition += gravity;
    	return yPosition;
    }
    public int flyDown() {
    	xPosition -= gravity;
    	return xPosition;
    }
}