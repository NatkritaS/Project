package Game;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Scoreboard {
    public static int COUNT = 0;
    public static int SCORE = 0;
    public static int MAX_SCORE = 0;
    private JFrame frame;
    private JLabel screen;
    private JLabel scoreboard;

    public int getScore() {
        return SCORE;
    }

    public void ResetScore() {
        SCORE = 0;
        MAX_SCORE = 0;
    }

    public int CountScore() {
        return SCORE++;
    }

    public void Max_Score() {
        if (SCORE >= MAX_SCORE) {
            MAX_SCORE = SCORE;
        }
    }

    public int getMax_Score() {
        return MAX_SCORE;
    }

    public void resetScore() {
        SCORE = 0;
        MAX_SCORE = 0;
    }

    public int Eat() {
        return SCORE += 10;
    }
}
