package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class SceneDragon {
    private ImageIcon background;
    private JLabel dragonLabel;
    private ArrayList<JLabel> WoodLabels;
    private JFrame frame;
    private JPanel panel;
    private Random random;
    private Dragon dragon;
    private Character_page character;
    private JButton button_back;
    private JLabel score;
    private Scoreboard sb;
    private JLabel fireballLabel;
    private int fireballCount = 0;
    private ArrayList<JLabel> heartLabels;
    private int heartcount;

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
        score = new JLabel("Score:");
  
        score.setFont(new Font("src/font/superpixel.ttf", Font.BOLD, 18));
        
        score.setBounds(580, 10, 100, 20);
        panel.add(score);
        frame.add(panel);
        frame.setVisible(true);
        sb = new Scoreboard();
        panel.requestFocus();
        background = new ImageIcon("src/images/background_sunny.png");
        WoodLabels = new ArrayList<>();
        random = new Random();
        Random rand = new Random();

        int totalWoodCount = rand.nextInt(1000 - 2 + 1) + 2;
        for (int i = 0; i < totalWoodCount; i++) {
            JLabel WoodLabelTop = new JLabel(new ImageIcon("src/images/WoodTop.png"));
            JLabel WoodLabelLand = new JLabel(new ImageIcon("src/images/WoodLand.png"));

            int LandWoodY = rand.nextInt(401) + 300;
            int TopWoodX = LandWoodY - 700;

            WoodLabelTop.setBounds(700 + i * 500, TopWoodX, 100, 500);
            WoodLabelLand.setBounds(700 + i * 500, LandWoodY, 100, 500);

            WoodLabels.add(WoodLabelTop);
            WoodLabels.add(WoodLabelLand);

            panel.add(WoodLabelTop);
            panel.add(WoodLabelLand);
        }

        Positionx = -50;
        fireballLabel = new JLabel(new ImageIcon("src/images/fireball.png"));
        fireballLabel.setBounds(Positionx, Positiony, 50, 50);
        panel.add(fireballLabel);
        moveFireball();

        dragon = new Dragon();
        dragonLabel = new JLabel(dragon.getDragonImage());
        dragonLabel.setBounds(dragon.getxPosition(), dragon.getyPosition(), 130, 100);
        panel.add(dragonLabel);
        frame.add(panel);
        frame.setVisible(true);
        panel.requestFocus();

        Thread moveWoodThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    moveWood();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveWoodThread.start();

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
                sb.getScore();
                character = new Character_page(frame);
                woodMoving = false;
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

    private boolean woodMoving = true;

    private void moveWood() {
        if (!woodMoving) return;
        boolean dragonPassedWood = false;
        int lastWoodX = 0;

        for (JLabel WoodLabel : WoodLabels) {
            int x = WoodLabel.getX();
            if (x <= -200) {
                WoodLabel.setVisible(false);
            } else {
                WoodLabel.setLocation(x - 8, WoodLabel.getY());
            }

            if (x > lastWoodX && x + WoodLabel.getWidth() <= dragonLabel.getX() && !dragonPassedWood) {
                dragonPassedWood = true;
                lastWoodX = x;
            }
        }

        Rectangle dragonBounds = dragonLabel.getBounds();
        for (JLabel WoodLabel : WoodLabels) {
            Rectangle woodBounds = WoodLabel.getBounds();
            if (dragonBounds.intersects(woodBounds)) {
                // ชนกับไม้
            	if (heartcount > 0 && dragon.getInvincibleCount() == 0) {
            	    panel.remove(heartLabels.get(heartcount - 1)); // ลบหัวใจ
            	    panel.revalidate();
            	    panel.repaint();
            	    heartcount--;
            	    updateHeartPositions();
            	    
            	    dragon.setInvincibleCount(2); // ทำให้มังกรเป็นอมตะเป็นเวลา 2 วินาที
            	    Timer invincibleTimer = new Timer(2000, new ActionListener() {
            	        @Override
            	        public void actionPerformed(ActionEvent e) {
            	            dragon.setInvincibleCount(0); // ทำให้มังกรไม่อมตะหลังจากเวลาผ่านไป
            	        }
            	    });
            	    invincibleTimer.setRepeats(false); // ตั้งให้ Timer ทำงานเพียงครั้งเดียว
            	    invincibleTimer.start(); // เริ่มต้นการนับเวลา
            	} 
            	if (heartcount == 0) {
            	    gameOver(); 
            	    //ลองเอาเงื่อนไขตรงนี้ออกนะ
            	}
       

            }
        }
    }

    private void moveFireball() {
        Thread moveFireballThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    Positionx -= 5;
                    fireballLabel.setBounds(Positionx, Positiony, 100, 70);
                    if (random.nextInt(100) < 2) {
                        addNewFireball();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveFireballThread.start();
    }

    private void addNewFireball() {
        JLabel newFireballLabel = new JLabel(new ImageIcon("src/images/fireball.png"));
        newFireballLabel.setBounds(frame.getWidth(), random.nextInt(panel.getHeight()), 50, 70);
        panel.add(newFireballLabel);

        Thread moveSingleFireballThread = new Thread(() -> {
            int xVelocity = -5;
            int yVelocity = 0;
            while (newFireballLabel.getX() > -50) {
                try {
                    Thread.sleep(30);
                    int newX = newFireballLabel.getX() + xVelocity;
                    int newY = newFireballLabel.getY() + yVelocity;
                    newFireballLabel.setLocation(newX, newY);
                    // เช็คการชนกับมังกร
                    Rectangle dragonBounds = dragonLabel.getBounds();
                    Rectangle fireballBounds = newFireballLabel.getBounds();
                    if (dragonBounds.intersects(fireballBounds)) {
                        panel.remove(newFireballLabel);
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
            if (newFireballLabel.getX() < -20) {
                panel.remove(newFireballLabel);
                panel.revalidate();
                panel.repaint();
            }
        });
        moveSingleFireballThread.start();
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
        woodMoving = false;
        
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