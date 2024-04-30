package Game;

public class Scoreboard {
    public int COUNT = 0;
    public int SCORE = 0;
    public int MAX_SCORE = 0;

    public int getScore() {
        return SCORE;
    }

    public void resetScore() {
        SCORE = 0;
    }

    public int CountScore() {
        SCORE++;
        return SCORE;
    }

    
}
