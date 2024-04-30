package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SceneChubby {
    private ImageIcon background;
    private JLabel chubbyLabel;
    private ArrayList<JLabel> bubbleLabels;
    private JFrame frame;
    private JPanel panel;
    private Random random;
    private Character_page character;
    private JButton button_back;
    private Chubby chubby;
    private JLabel score;
    private Scoreboard sb;
    private JLabel goldfishLabel;
    private int goldfishCount = 0;
    private ArrayList<JLabel> heartLabels;
    private int heartcount;

    
    protected static int Positiony = 350;
    protected static int Positionx = 400;

    public SceneChubby(JFrame J) {
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
        score = new JLabel("Score:");
        score.setFont(new Font("src/font/superpixel.ttf", Font.BOLD, 18));
        score.setBounds(600, 10, 100, 20);
        panel.add(score);
        frame.add(panel);
        frame.setVisible(true);
        sb = new Scoreboard();
        panel.requestFocus();
        background = new ImageIcon("src/images/Underthesea_background.png");
        bubbleLabels = new ArrayList<>();
        random = new Random();
        Random rand = new Random();
        
        int totalBubbleCount = rand.nextInt(1000 - 2 + 1) + 2;
        for (int i = 0; i < totalBubbleCount; i++) {
            JLabel bubbleLabelTop = new JLabel(new ImageIcon("src/images/bubbleTop.png"));
            JLabel bubbleLabelLand = new JLabel(new ImageIcon("src/images/bubbleLand.png"));
            
            int landBubbleY = rand.nextInt(401) + 300;
            int topBubbleX = landBubbleY - 700;
            
            bubbleLabelTop.setBounds(700 + i * 500, topBubbleX, 100, 460);
            bubbleLabelLand.setBounds(700 + i * 500, landBubbleY, 100, 445);
            
            bubbleLabels.add(bubbleLabelTop);
            bubbleLabels.add(bubbleLabelLand);
            
            panel.add(bubbleLabelTop);
            panel.add(bubbleLabelLand);
        }

        Positionx = -50;
        goldfishLabel = new JLabel(new ImageIcon("src/images/goldfish.png"));
        goldfishLabel.setBounds(Positionx, Positiony, 50, 50);
        panel.add(goldfishLabel);
        moveGoldfish();

        chubby = new Chubby();
        chubbyLabel = new JLabel(chubby.getChubbyBirdImage());
        chubbyLabel.setBounds(chubby.getxPosition(), chubby.getyPosition(), 130, 100);
        panel.add(chubbyLabel);
        frame.add(panel);
        frame.setVisible(true);
        panel.requestFocus();

        Thread moveBubbleThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    moveBubble();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveBubbleThread.start();

        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int key1 = panel.getHeight() - chubby.getChubbyBirdHeight();
                if (key == KeyEvent.VK_UP) {
                    if (Positiony - Chubby.GRAVITY >= 0) {
                        Positiony -= Chubby.GRAVITY;
                    }
                    Chubby.flyup();
                } else if (key == KeyEvent.VK_DOWN) {
                    if (Positiony + Chubby.GRAVITY <= key1) {
                        Positiony += Chubby.GRAVITY;
                    }
                    Chubby.flyDown();
                }
                chubbyLabel.setBounds(chubby.getxPosition(), Positiony, 130, chubby.getChubbyBirdHeight());
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
                sb.getScore();
                character = new Character_page(frame);
                BubbleMoving = false;
            }
        });
        
        heartLabels = new ArrayList<>();
        heartcount = 3;
        for (int i = 0; i < heartcount; i++) {
            JLabel heartLabel = new JLabel(new ImageIcon("src/images/Heart.png"));
            heartLabel.setBounds(2 + i * 30, 20, 40, 50);
            panel.add(heartLabel);
            heartLabels.add(heartLabel);
        }
    }

    private boolean BubbleMoving = true;

    private void moveBubble() {
    	if (!BubbleMoving) return;
        boolean chubbyPassedBubble = false;
        int lastBubbleX = 0;

        for (JLabel bubbleLabel : bubbleLabels) {
            int x = bubbleLabel.getX();
            if (x <= -200) {
                bubbleLabel.setVisible(false);
            } else {
                bubbleLabel.setLocation(x - 8, bubbleLabel.getY());
            }

            if (x > lastBubbleX && x + bubbleLabel.getWidth() <= chubbyLabel.getX() && !chubbyPassedBubble) {
                chubbyPassedBubble = true;
                lastBubbleX = x;
            }
        }

        if (chubbyPassedBubble) {
            int addedScore = sb.CountScore();
            score.setText("Score: " + addedScore);
        }

        Rectangle chubbyBounds = chubbyLabel.getBounds();
        for (JLabel bubbleLabel : bubbleLabels) {
            Rectangle bubbleBounds = bubbleLabel.getBounds();
            if (chubbyBounds.intersects(bubbleBounds)) {
                //ชนกับฟองสบู่
            	if (heartcount > 0 && chubby.getInvincibleCount() == 0) {
            	    panel.remove(heartLabels.get(heartcount - 1)); // ลบหัวใจ
            	    panel.revalidate();
            	    panel.repaint();
            	    heartcount--;
            	    updateHeartPositions();
            	    
            	    chubby.setInvincibleCount(2); // ทำให้มังกรเป็นอมตะเป็นเวลา 2 วินาที
            	    Timer invincibleTimer = new Timer(2000, new ActionListener() {
            	        @Override
            	        public void actionPerformed(ActionEvent e) {
            	        	chubby.setInvincibleCount(0); // ทำให้มังกรไม่อมตะหลังจากเวลาผ่านไป
            	        }
            	    });
            	    invincibleTimer.setRepeats(false); // ตั้งให้ Timer ทำงานเพียงครั้งเดียว
            	    invincibleTimer.start(); // เริ่มต้นการนับเวลา
            	} 
            	if (heartcount == 0) {
            	    gameOver(); 
            	    
            	}
            	
            	
            }
        }
    }
    
	private void moveGoldfish() {
        Thread moveGoldfishThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    Positionx -= 5;
                    goldfishLabel.setBounds(Positionx, Positiony, 90, 70);
                    if (random.nextInt(100) < 2) {
                        addNewGoldfish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveGoldfishThread.start();
    }

    private void addNewGoldfish() {
        JLabel newGoldfishLabel = new JLabel(new ImageIcon("src/images/Goldfish.png"));
        newGoldfishLabel.setBounds(frame.getWidth(), random.nextInt(panel.getHeight()), 90, 70);
        panel.add(newGoldfishLabel);
        
        Thread moveSingleGoldfishThread = new Thread(() -> {
            int xVelocity = -5;
            int yVelocity = 0;
            while (newGoldfishLabel.getX() > -50) {
                try {
                    Thread.sleep(30);
                    int newX = newGoldfishLabel.getX() + xVelocity;
                    int newY = newGoldfishLabel.getY() + yVelocity;
                    newGoldfishLabel.setLocation(newX, newY);
                    //เช็คการชนกับนกอ้วน
                    Rectangle chubbyBounds = chubbyLabel.getBounds();
                    Rectangle goldfishBounds = newGoldfishLabel.getBounds();
                    if (chubbyBounds.intersects(goldfishBounds)) {
                        panel.remove(newGoldfishLabel);
                        panel.revalidate();
                        panel.repaint();
                        int addedScore = sb.CountScore(); // เพิ่มคะแนน
                        score.setText("Score: " + addedScore); // แสดงคะแนนที่มีใหม่
                        
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            }
            if (newGoldfishLabel.getX() < -20) {
                panel.remove(newGoldfishLabel);
                panel.revalidate();
                panel.repaint();
            }
        });
        moveSingleGoldfishThread.start();
        
    }
    
	private void updateHeartPositions() {
		for (int i = 0; i < goldfishCount; i++) {
            JLabel heartLabel = heartLabels.get(i);
            heartLabel.setBounds(2 + i * 30, 20, 40, 50);
        }
    }			

    private void gameOver() {
    	frame.getContentPane().removeAll();
        frame.repaint();
        BubbleMoving = false;
        
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(null);
        // คะแนนสุดท้าย
        int finalScore = sb.getScore();
        JLabel scoreMessage = new JLabel("Final Score: " + finalScore);
        scoreMessage.setFont(new Font("Arial", Font.BOLD, 54));
        scoreMessage.setForeground(new Color(40, 53, 147));
        scoreMessage.setBounds(190, 230, 500, 400);
        gameOverPanel.add(scoreMessage);

        ImageIcon BG_Scoreboard = new ImageIcon("src/images/Scoreboard.png");
        JLabel screen = new JLabel();
        screen.setIcon(BG_Scoreboard);
        screen.setBounds(0, 0, 700, 800);
        gameOverPanel.add(screen, BorderLayout.CENTER);
        
        ImageIcon back = new ImageIcon("src\\images\\back_button.png");
        JButton backButton = new JButton();
        backButton.setIcon(back);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setBounds(0, 5, 95, 20);
        
        gameOverPanel.add(backButton);
        frame.add(backButton);
        backButton.setVisible(true);
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                character = new Character_page(frame);
            }
        });


        frame.getContentPane().add(gameOverPanel);
        frame.setVisible(true);
        gameOverPanel.requestFocus();
    }
}