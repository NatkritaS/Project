package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SceneCutieghost {
    private ImageIcon background;
    private JLabel cutieghostLabel;
    private ArrayList<JLabel> fireLabels;
    private JFrame frame;
    private JPanel panel;
    private Random random;
    private Cutieghost cutie;
    private Character_page character;
    private JButton button_back;
    private JLabel score;
    private Scoreboard sb;
    private JLabel goldappleLabel;
    private ArrayList<JLabel> heartLabels;
    private int heartCount;

    protected static int Positiony = 350;
    protected static int Positionx = 400;
    private int maxScore; // เพิ่มตัวแปรเก็บคะแนนสูง0สุด

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
        score = new JLabel("Score:");
        score.setFont(new Font("Arial", Font.BOLD, 18));
        score.setForeground(Color.WHITE);
        score.setBounds(580, 10, 100, 20);
        panel.add(score);
        frame.add(panel);
        frame.setVisible(true);
        sb = new Scoreboard();
        panel.requestFocus();
        background = new ImageIcon("src/images/hell_background.png");
        fireLabels = new ArrayList<>();
        random = new Random();
        Random random = new Random();

        int topFireCount = random.nextInt(1000 - 2 + 1) + 2;
        for (int i = 0; i < topFireCount; i++) {
            JLabel fireLabelTop = new JLabel(new ImageIcon("src/images/top.png"));
            JLabel fireLabelLand = new JLabel(new ImageIcon("src/images/FireLand.png"));

            int landFireY = random.nextInt(301) + 350;
            int topFireX = landFireY - 700;

            fireLabelTop.setBounds(700 + i * 300, topFireX, 70, 400);
            fireLabelLand.setBounds(700 + i * 300, landFireY, 70, 400);

            fireLabels.add(fireLabelTop);
            panel.add(fireLabelTop);

            fireLabels.add(fireLabelLand);
            panel.add(fireLabelLand);
        }

        Positionx -= 50;
        goldappleLabel = new JLabel(new ImageIcon("src/images/goldapple.png"));
        goldappleLabel.setBounds(Positionx, Positiony, 50, 50);
        panel.add(goldappleLabel);
        moveGoldapple();

        cutie = new Cutieghost();
        cutieghostLabel = new JLabel(cutie.getCutieghostImage());
        cutieghostLabel.setBounds(cutie.getxPosition(), cutie.getyPosition(), 100, 100);
        panel.add(cutieghostLabel);
        frame.add(panel);
        frame.setVisible(true);
        panel.requestFocus();
        
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
                cutieghostLabel.setBounds(cutie.getxPosition(), Positiony, 100, cutie.getCutieghostHeight());
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
        heartCount = 3;
        for (int i = 0; i < heartCount; i++) {
            JLabel heartLabel = new JLabel(new ImageIcon("src/images/Heart.png"));
            heartLabel.setBounds(2 + i * 30, 20, 40, 50);
            panel.add(heartLabel);
            heartLabels.add(heartLabel);
        }
        
        // ตรวจสอบคะแนนสูงสุดเมื่อเริ่มต้นโปรแกรม
        maxScore = sb.getMaxScore();
    }
    
    private boolean fireMoving = true;
    
    private void moveFire() {
        if (!fireMoving) return;
        boolean cutieghostPassedFire = false;
        int lastFireX = 0;
        
        for (JLabel fireLabel : fireLabels) {
            int x = fireLabel.getX();
            if (x <= -200) {
                fireLabel.setVisible(false);
            } else {
                fireLabel.setLocation(x - 8, fireLabel.getY());
            }

            if (x > lastFireX && x + fireLabel.getWidth() <= cutieghostLabel.getX() && !cutieghostPassedFire) {
                cutieghostPassedFire = true;
                lastFireX = x;
            }
        }

        Rectangle cutieghostBounds = cutieghostLabel.getBounds();
        for (JLabel fireLabel : fireLabels) {
            Rectangle fireBounds = fireLabel.getBounds();
            if (cutieghostBounds.intersects(fireBounds)) {
                if (heartCount > 0 && cutie.getInvincibleCount() == 0) {
                    panel.remove(heartLabels.get(heartCount - 1)); // ลบหัวใจ
                    panel.revalidate();
                    panel.repaint();
                    heartCount--;
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
                if (heartCount == 0) {
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
                    Positionx -= 200;
                    goldappleLabel.setBounds(Positionx, Positiony, 40, 40);
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
        newGoldappleLabel.setBounds(frame.getWidth(), random.nextInt(panel.getHeight()), 40, 40);
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
                    Rectangle ghostBounds = cutieghostLabel.getBounds();
                    Rectangle goldappleBounds = newGoldappleLabel.getBounds();
                    if (ghostBounds.intersects(goldappleBounds)) {
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
        for (int i = 0; i < heartCount; i++) {
            JLabel heartLabel = heartLabels.get(i);
            heartLabel.setBounds(2 + i * 30, 20, 40, 50);
        }
    }

    private void gameOver() {
        frame.getContentPane().removeAll();
        frame.repaint();
        fireMoving = false;

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
