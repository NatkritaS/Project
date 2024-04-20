package Game;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
 
public class SceneDragon {
    private ImageIcon background;
    private JLabel dragonLabel;
    private ArrayList<JLabel> rockLabels;
    private JFrame frame;
    private JPanel panel;
    private Random random;
    private Dragon dragon;
    private Character_page character;
    private JButton button_back;
    private JLabel score;
    private Scoreboard sb;
    
    protected static int Positiony = 350;
    protected static int Positionx = 400;
 
    public SceneDragon(JFrame J) {
        frame = J;
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
   
 
        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        panel.setLayout(null);
        score = new JLabel("Score:" );
        Font font = new Font("src/font/superpixel.ttf", Font.BOLD, 18);
        score.setFont(font);
        score.setBounds(600, 10, 100, 20);
        panel.add(score);
        frame.add(panel);
 
        frame.setVisible(true);
        sb = new Scoreboard();
        panel.requestFocus();
        
        
        background = new ImageIcon("src/images/background_sunny.png");
 
        rockLabels = new ArrayList<>();
        random = new Random();
        Random random = new Random();
        int a, b;
        do {
            a = random.nextInt(401) - 200; 
            b = random.nextInt(301) + a + 500;
        } while (Math.abs(a - b) < 500);
        for (int i = 0; i < 2; i++) {
        	JLabel rockLabelTop = new JLabel(new ImageIcon("src/images/topRock.png"));
            JLabel rockLabelLand = new JLabel(new ImageIcon("src/images/landRock.png"));
            rockLabelTop.setBounds(700, a, 280, 285);
            rockLabelLand.setBounds(700, b, 280, 305);

            rockLabels.add(rockLabelLand);
            rockLabels.add(rockLabelTop);

            panel.add(rockLabelLand);
            panel.add(rockLabelTop);
        }
 
        dragon = new Dragon();
        dragonLabel = new JLabel(dragon.getDragonImage());
        dragonLabel.setBounds(dragon.getxPosition(), dragon.getyPosition(), 130, 100);
        panel.add(dragonLabel);
        frame.add(panel);
        frame.setVisible(true);
 
        panel.requestFocus();
        // AI แก้ไข
        Thread moveRocksThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    moveRocks();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveRocksThread.start();
 
        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int key1 = panel.getHeight() - dragon.getDragonHeight();
                
 
                if (key == KeyEvent.VK_UP) {
                    if (Positiony - Dragon.GRAVITY >= 0) {
                        Positiony -= Dragon.GRAVITY;
                    }
                    Dragon.flyup();
                } else if (key == KeyEvent.VK_DOWN) {
                    if (Positiony + Dragon.GRAVITY <= key1) {
                        Positiony += Dragon.GRAVITY;
                    }
                    Dragon.flyDown();
                }
                	
              
                
 
                dragonLabel.setBounds(dragon.getxPosition(), Positiony, 130, dragon.getDragonHeight());
                panel.revalidate();
                panel.repaint();
            }
        });
 
        ImageIcon back = new ImageIcon("src\\images\\back_button.png");
        button_back = new JButton();
        button_back.setIcon(back);
        button_back.setBorderPainted(false);
        button_back.setContentAreaFilled(false);
        button_back.setFocusPainted(false);
        button_back.setOpaque(false);
        button_back.setBounds(0, 5, 95, 20);
        panel.add(button_back);
 
        button_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                character = new Character_page(frame);
            }
        });
    }
 
    private void moveRocks() {
        boolean dragonPassedRock = false;
        for (JLabel rockLabel : rockLabels) {
            int x = rockLabel.getX();
            if (x <= -200) {
                rockLabel.setLocation(frame.getWidth(), rockLabel.getY());
            } else {
                rockLabel.setLocation(x - 8, rockLabel.getY());
            }
            
            if (x + rockLabel.getWidth() == dragonLabel.getX()) {
                dragonPassedRock = true;
            }
        }
        if (dragonPassedRock) {
            // สุ่มตำแหน่งใหม่สำหรับหินทุกตัว
            for (JLabel rockLabel : rockLabels) {
                int currentY = rockLabel.getY();
                int newRockY;
                boolean overlapping;
                do {
                    newRockY = random.nextInt(panel.getHeight() - rockLabel.getHeight() - 50) + 25;
                    overlapping = false;
                    for (JLabel otherRockLabel : rockLabels) {
                        if (otherRockLabel != rockLabel && Math.abs(otherRockLabel.getY() - newRockY) == 500) {
                            overlapping = true;
                            break;
                        }
                    }
                } while (overlapping); 

                rockLabel.setLocation(rockLabel.getX(), newRockY);
            }
        }
 
        if (dragonPassedRock) {
        	int addedScore = sb.CountScore();
        	sb.Max_Score();
            score.setText("Score: " + addedScore);
        }
 
        // มังกรชนหินมั้ย
        Rectangle dragonBounds = dragonLabel.getBounds();
        for (JLabel rockLabel : rockLabels) {
            Rectangle rockBounds = rockLabel.getBounds();
            if (dragonBounds.intersects(rockBounds)) {
                gameOver();
                return;
            }
        }
   }
        
 
	private void gameOver() {
		   JOptionPane.showMessageDialog(frame, "Game Over");
	        System.exit(0);
	}
}
 