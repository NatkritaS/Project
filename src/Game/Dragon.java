package Game;

import javax.swing.*;

public class Dragon {
    public static int xPosition;
    public static int yPosition;
    private ImageIcon dragonImage;
    public static int GRAVITY = 20;
    private boolean invincible;
    private int invincibleCount;

    
    public Dragon() {
        this.xPosition = 100;
        this.yPosition = 350;
        this.dragonImage = new ImageIcon("src/images/Dragon.png");
        this.invincible = false; // เริ่มต้นมังกรไม่ได้เป็นอมตะ
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

    public static int flyDown() {
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
    
