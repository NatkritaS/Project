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
        score.setBounds(550, 10, 100, 20);
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
        int a, b;
        a = random.nextInt(panel.getWidth() - 280-285);
        do {
            b = random.nextInt(panel.getWidth() - 200-305) + a + 500;
        } while (Math.abs(a - b) < 500);

       


        int totalFireCount = random.nextInt(1000 - 2 + 1) + 2;
        for (int i = 0; i < totalFireCount; i++) {
            JLabel FireLabelTop = new JLabel(new ImageIcon("src/images/top.png"));
            JLabel FireLabelLand = new JLabel(new ImageIcon("src/images/FireLand.png"));
            
            int LandbubbleY = random.nextInt(401) + 300;
            int TopbubbleX = LandbubbleY - 700; 
            
            FireLabelTop.setBounds(700 + i * 350, TopbubbleX, 50, 450);
            FireLabelLand.setBounds(700 + i * 350, LandbubbleY, 70, 450);
          
            FireLabels.add(FireLabelTop);
            panel.add(FireLabelTop);
            
            FireLabels.add(FireLabelLand);
            panel.add(FireLabelLand);
       
        }

        Positionx -= 2;
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
                sb.ResetScore();
                character = new Character_page(frame);
                FireMoving = false;
            }
        });
    }
    private boolean FireMoving = true;
    private void moveFire() {
    	if (!FireMoving) return;
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
                gameOver();
                return;
            }
        }
    }
    

    private void moveGoldapple() {
        Thread moveGoldappleThread = new Thread(() -> {
        	
        	while (true) {
                try {
                    Thread.sleep(20);
                    Positionx -= 5;
                    goldappleLabel.setBounds(Positionx, Positiony, 250, 100);
                    if (Positionx < -50) {
                        Positionx = frame.getWidth();
                    }
                    
                    if (random.nextInt(100) < 4) { 
                    	addNewGoldapple();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveGoldappleThread.start();
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
                        Thread.sleep(20);
                        int newX = newGoldappleLabel.getX() + xVelocity;
                        int newY = newGoldappleLabel.getY() + yVelocity;
                        newGoldappleLabel.setLocation(newX, newY); 
                        // เช็คการชนกับมังกร
                        Rectangle appleBounds = cutieghostLabel.getBounds();
                        Rectangle goldappleBounds = newGoldappleLabel.getBounds();
                        if (appleBounds.intersects(goldappleBounds)) {
                            // ถ้ามังกรชนกับลูกไฟ ก็ลบลูกไฟนั้นออกจาก panel และเพิ่มคะแนน
                            panel.remove(newGoldappleLabel);
                            panel.revalidate();
                            panel.repaint();
                            goldappleCount++; // เพิ่มค่า fireballCount เมื่อมังกรเก็บลูกไฟได้
                            int addedScore = sb.CountScore() + goldappleCount; 
                            score.setText("Score: " + addedScore); 
                            break;
                        } else {
                            panel.remove(newGoldappleLabel);
                            panel.revalidate();
                            panel.repaint();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            moveSingleGoldappleThread.start();
        }
    private void gameOver() {
        JOptionPane.showMessageDialog(frame, "Game Over");
        System.exit(0);
    }
}