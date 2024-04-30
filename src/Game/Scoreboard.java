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

    public int MaxScore() {
        if (SCORE >= MAX_SCORE) {
            MAX_SCORE = SCORE;
        }
        return MAX_SCORE;
    }

    public int getMaxScore() {
        return MAX_SCORE;
    }

    public int Eat() {
        SCORE += 10;
        MaxScore(); // เรียกเมทอด maxScore() เพื่อตรวจสอบและอัพเดทคะแนนสูงสุด
        return SCORE;
    }
}
