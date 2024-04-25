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
    private JLabel fireballLabel;
    private int fireballCount = 0;
    

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

        int TopFireY = 0; 
        int LandFireY = panel.getHeight() - 305; 

        int topFireCount = random.nextInt(1000)+2;
        for (int i = 0; i < topFireCount; i++) {
            JLabel FireLabelTop = new JLabel(new ImageIcon("src/images/Firetop.png"));
            int ranposTop = random.nextInt(351);
            FireLabelTop.setBounds(700 + i * 350, TopFireY, 100, ranposTop); 
            FireLabels.add(FireLabelTop);
            panel.add(FireLabelTop);
        }

        int landFireCount = random.nextInt(1000) +2;
        for (int i = 0; i < landFireCount; i++) {
            JLabel FireLabelLand = new JLabel(new ImageIcon("src/images/Fireland.png"));
            int ranposLand = 500+random.nextInt(101);
            FireLabelLand.setBounds(700 + i * 350, LandFireY, 100, ranposLand); 
            FireLabels.add(FireLabelLand);
            panel.add(FireLabelLand);
        }

        //Positionx = -50;
        //fireballLabel = new JLabel(new ImageIcon("src/images/fireball.png"));
        //fireballLabel.setBounds(Positionx, Positiony, 50, 50);
        //panel.add(fireballLabel);
        //moveFireball();

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
                    moveFireball();
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
            }
        });
    }

    private void moveFire() {
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
            //sb.Max_Score();
            score.setText("Score: " + addedScore);
        }

        Rectangle dragonBounds = cutieghostLabel.getBounds();
        for (JLabel FireLabel : FireLabels) {
            Rectangle fireBounds = FireLabel.getBounds();
            if (fireBounds.intersects(fireBounds)) {
                gameOver();
                return;
            }
        }
    }

    private void moveFireball() {
        Thread moveFireballThread = new Thread(() -> {
        	while (true) {
                try {
                    Thread.sleep(20);
                    Positionx -= 5;
                    fireballLabel.setBounds(Positionx, Positiony, 250, 100);
                    if (Positionx < -50) {
                        Positionx = frame.getWidth();
                    }
                    
                    // สุ่มเพิ่ม fireball ใหม่
                    if (random.nextInt(100) < 4) { // สุ่มใหม่โดยมีโอกาส 5% ทุกครั้ง
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
            newFireballLabel.setBounds(frame.getWidth(), random.nextInt(panel.getHeight()), 250, 100);
            panel.add(newFireballLabel);
            
            Thread moveSingleFireballThread = new Thread(() -> {
            	 int xVelocity = -5; // ความเร็วในแนวแกน x (เคลื่อนที่ไปทางซ้าย)
                 int yVelocity = 0; // ความเร็วในแนวแกน y (ไม่เคลื่อนที่ในแนวนี้)
                while (newFireballLabel.getX() > -50) {
                    try {
                        Thread.sleep(20);
                        int newX = newFireballLabel.getX() + xVelocity;
                        int newY = newFireballLabel.getY() + yVelocity;
                        newFireballLabel.setLocation(newX, newY);
                     // ถ้าลูกไฟออกนอกหน้าต่าง
                        // เช็คการชนกับมังกร
                        Rectangle fireBounds = cutieghostLabel.getBounds();
                        Rectangle fireballBounds = newFireballLabel.getBounds();
                        if (fireBounds.intersects(fireballBounds)) {
                            // ถ้ามังกรชนกับลูกไฟ ก็ลบลูกไฟนั้นออกจาก panel และเพิ่มคะแนน
                            panel.remove(newFireballLabel);
                            panel.revalidate();
                            panel.repaint();
                            fireballCount++; // เพิ่มค่า fireballCount เมื่อมังกรเก็บลูกไฟได้
                            int addedScore = sb.CountScore() + fireballCount; // เพิ่มคะแนนใน scoreboard โดยรวมกับค่า fireballCount
                            score.setText("Score: " + addedScore); // แสดงคะแนนใหม่บน score
                            // หยุดลูกไฟเคลื่อนไหว
                            break;
                        } else {
                            // ถ้ามังกรไม่ชนกับลูกไฟ ก็ลบลูกไฟนั้นออกจาก panel โดยไม่เพิ่มคะแนน
                            panel.remove(newFireballLabel);
                            panel.revalidate();
                            panel.repaint();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            moveSingleFireballThread.start();
        }
    private void gameOver() {
        JOptionPane.showMessageDialog(frame, "Game Over");
        System.exit(0);
    }
}
