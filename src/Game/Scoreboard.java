package Game;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Scoreboard {
	public static int COUNT = 0;
	public static int SCORE = 0;
	public static int MAX_SCORE = 0;
	private JFrame frame ;
	private JLabel screen;
	private JLabel scoreboard;
	
	
	
	public Scoreboard() {
		this.scoreboard = scoreboard;
		COUNT = 0;
		SCORE = 0;
		MAX_SCORE = 0;
		}
	
	{
	
	frame = new JFrame();
	frame.setSize(700, 800);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(false);
	frame.setVisible(false); 

	JPanel panel = new JPanel();
    panel.setLayout(null); 
	ImageIcon BG_Scoreboard = new ImageIcon("src/images/Scoreboard.png");
	
	screen = new JLabel();
    screen.setIcon(BG_Scoreboard);
    screen.setBounds(0, 0, 700, 800);
    panel.add(screen);
      
    frame.add(panel);
    frame.setVisible(false);
    
	}
	//public void setScore(int score) {
	//	SCORE = score ;
	//}
	
    public int getScore() {
    	return SCORE;
    }
    public void ResetScore() {
    	SCORE = 0;
    	MAX_SCORE =0;
    }
    public int CountScore() {
    	return COUNT++;
    }
    public void Max_Score() {
		if (SCORE >= MAX_SCORE) {
			MAX_SCORE = SCORE;
    }
    }
	public int getMax_Score() {
		return MAX_SCORE;
	}
	public void printScore() {
		SCORE+=COUNT;
		scoreboard.setText("Score: " + SCORE);
		
	}
	


}





