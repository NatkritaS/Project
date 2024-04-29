package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SceneCutieghost {
	private ImageIcon background;
    private JLabel cutieghostLabel;
    private ArrayList<JLabel> FireLabels;
    private JFrame frame;
    private JPanel panel;
    private Random random;
    private Cutieghost cutie;
    private Character_page character;
    private JButton button_back;
    private JLabel score;
    private Scoreboard sb;
    private Lobby lobby;
    private JLabel goldappleLabel;
    private int goldappleCount = 0;
    private ArrayList<JLabel> heartLabels;
    private int heartcount;
   
    protected static int Positiony = 350;
    protected static int Positionx = 400;

    public SceneCutieghost(JFrame J) {
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
        score.setForeground(Color.WHITE);
        panel.add(score);
        frame.add(panel);
        frame.setVisible(true);
        sb = new Scoreboard();
        panel.requestFocus();
        background = new ImageIcon("src/images/hell_background.png");
        FireLabels = new ArrayList<>();
        random = new Random();
        Random random = new Random();
        
        int topFireCount = random.nextInt(1000)+2;
        for (int i = 0; i < topFireCount; i++) {
            JLabel FireLabelTop = new JLabel(new ImageIcon("src/images/top.png"));
            JLabel FireLabelLand = new JLabel(new ImageIcon("src/images/FireLand.png"));
            
            int LandFireY = random.nextInt(301) + 350;
            int TopFireX = LandFireY - 700;
            
            FireLabelTop.setBounds(700 + i * 300, TopFireX, 70, 400);
            FireLabelLand.setBounds(700 + i * 300, LandFireY, 70, 385);
            
            FireLabels.add(FireLabelTop);
            panel.add(FireLabelTop);
 
            FireLabels.add(FireLabelLand);
            panel.add(FireLabelLand);
        }

        Positionx -= 50;
        goldappleLabel = new JLabel(new ImageIcon("src/images/goldapple.png"));
        goldappleLabel.setBounds(Positionx, Positiony, 50, 50);
        panel.add(goldappleLabel);
        moveGoldapple();

        cutie = new Cutieghost();
        cutieghostLabel = new JLabel(cutie.getCutieghostImage());
        cutieghostLabel.setBounds(cutie.getxPosition(), cutie.getyPosition(), 130, 100);
        panel.add(cutieghostLabel);
        frame.add(panel);
        frame.setVisible(true);
        panel.requestFocus();
        // AI แก้ไข
        Thread moveFireThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    moveFire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveFireThread.start();

        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int key1 = panel.getHeight() - cutie.getCutieghostHeight();
                if (key == KeyEvent.VK_UP) {
                    if (Positiony - Cutieghost.GRAVITY >= 0) {
                        Positiony -= Cutieghost.GRAVITY;
                    }
                    Cutieghost.flyup();
                } else if (key == KeyEvent.VK_DOWN) {
                    if (Positiony + Cutieghost.GRAVITY <= key1) {
                        Positiony += Cutieghost.GRAVITY;
                    }
                    Cutieghost.flyDown();
                }
                cutieghostLabel.setBounds(cutie.getxPosition(), Positiony, 130, cutie.getCutieghostHeight());
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
                fireMoving = false;
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
    
    private boolean fireMoving = true;
    
    private void moveFire() {
    	if (!fireMoving) return;
        boolean CutieghostPassedFire = false;
        int lastFireX = 0;
        
        for (JLabel FireLabel : FireLabels) {
            int x = FireLabel.getX();
            if (x <= -200) {
                FireLabel.setVisible(false);
            } else {
                FireLabel.setLocation(x - 8, FireLabel.getY());
            }

            if (x > lastFireX && x + FireLabel.getWidth() <= cutieghostLabel.getX() && !CutieghostPassedFire) {
            	CutieghostPassedFire = true;
                lastFireX = x;
            }
        }

        if (CutieghostPassedFire) {
            int addedScore = sb.CountScore();
            score.setText("Score: " + addedScore);
        }

        Rectangle cutieghostBounds = cutieghostLabel.getBounds();
        for (JLabel FireLabel : FireLabels) {
            Rectangle fireBounds = FireLabel.getBounds();
            if (cutieghostBounds.intersects(fireBounds)) {
            	// ชนกับไฟ
            	if (heartcount > 0 && cutie.getInvincibleCount() == 0) {
            	    panel.remove(heartLabels.get(heartcount - 1)); // ลบหัวใจ
            	    panel.revalidate();
            	    panel.repaint();
            	    heartcount--;
            	    updateHeartPositions();
            	    
            	    cutie.setInvincibleCount(2); // ทำให้ผีเป็นอมตะเป็นเวลา 2 วินาที
            	    Timer invincibleTimer = new Timer(2000, new ActionListener() {
            	        @Override
            	        public void actionPerformed(ActionEvent e) {
            	        	cutie.setInvincibleCount(0); // ทำให้ผีไม่อมตะหลังจากเวลาผ่านไป
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
	
    private void moveGoldapple() {
    	Thread moveFireballThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    Positionx -= 5;
                    goldappleLabel.setBounds(Positionx, Positiony, 250, 100);
                    if (random.nextInt(100) < 2) {
                        addNewGoldapple();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveFireballThread.start();
    }

        private void addNewGoldapple() {
            JLabel newGoldappleLabel = new JLabel(new ImageIcon("src/images/goldapple.png"));
            newGoldappleLabel.setBounds(frame.getWidth(), random.nextInt(panel.getHeight()), 250, 100);
            panel.add(newGoldappleLabel);
            
            Thread moveSingleGoldappleThread = new Thread(() -> {
            	 int xVelocity = -5; 
                 int yVelocity = 0; 
                while (newGoldappleLabel.getX() > -50) {
                    try {
                        Thread.sleep(30);
                        int newX = newGoldappleLabel.getX() + xVelocity;
                        int newY = newGoldappleLabel.getY() + yVelocity;
                        newGoldappleLabel.setLocation(newX, newY); 
                        // เช็คการชนกับผี
                        Rectangle appleBounds = cutieghostLabel.getBounds();
                        Rectangle goldappleBounds = newGoldappleLabel.getBounds();
                        if (appleBounds.intersects(goldappleBounds)) {
                            panel.remove(newGoldappleLabel);
                            panel.revalidate();
                            panel.repaint();
                            sb.getScore();
                            int addedScore = sb.CountScore(); // เพิ่มคะแนน
                            score.setText("Score: " + addedScore); // แสดงคะแนนที่มีใหม่
                            break;
                        } 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (newGoldappleLabel.getX() < -20) {
                    panel.remove(newGoldappleLabel);
                    panel.revalidate();
                    panel.repaint();
                }
            });
            moveSingleGoldappleThread.start();
        }
        
        private void updateHeartPositions() {
        	for (int i = 0; i < heartcount; i++) {
                JLabel heartLabel = heartLabels.get(i);
                heartLabel.setBounds(2 + i * 30, 20, 40, 50);
            }
	}
        
    private void gameOver() {
    	frame.getContentPane().removeAll();
        frame.repaint();
        sb.resetScore(); // เรียกใช้เมทอด resetScore() เพื่อรีเซ็ตคะแนนทุกครั้งที่เกมเสร็จสิ้น
        fireMoving = false;
        panel.setLayout(new BorderLayout());
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.getContentPane().removeAll();
        frame.repaint();
        sb.resetScore();
        fireMoving = false;
        panel.setLayout(new BorderLayout());
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        panel.setLayout(null);

        int addedScore = sb.CountScore();
        JLabel scoreMessage = new JLabel("Your final score is: " + sb.CountScore());
        scoreMessage.setFont(new Font("Arial", Font.BOLD, 24));
        scoreMessage.setForeground(Color.BLACK);
        scoreMessage.setBounds(250, 200, 500, 400);
        panel.add(scoreMessage);

        ImageIcon BG_Scoreboard = new ImageIcon("src/images/Scoreboard.png");
        JLabel screen = new JLabel();
        screen.setIcon(BG_Scoreboard);
        screen.setBounds(0, 0, 700, 800);
        panel.add(screen, BorderLayout.CENTER);

        ImageIcon back = new ImageIcon("src\\images\\back_button.png");
        button_back = new JButton();
        button_back.setIcon(back);
        button_back.setBorderPainted(false);
        button_back.setContentAreaFilled(false);
        button_back.setFocusPainted(false);
        button_back.setOpaque(false);
        button_back.setBounds(0, 5, 95, 20);
        panel.add(button_back);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        panel.requestFocus();

        button_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                
                character = new Character_page(frame);
                fireMoving = false;
                sb.resetScore();
            }
        });
    }
}