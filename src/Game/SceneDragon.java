package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private Lobby lobby;
    private JLabel fireballLabel;
    private int fireballCount = 0;
    

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

        WoodLabels = new ArrayList<>();
        random = new Random();
        Random random = new Random();
        int a, b;
        a = random.nextInt(panel.getWidth() - 280-285);
        do {
            b = random.nextInt(panel.getWidth() - 200-305) + a + 500;
        } while (Math.abs(a - b) < 500);

        int TopWoodY = 0; 
        int LandWoodY = panel.getHeight() - 305; 

        int topWoodCount = random.nextInt(1000)+2;
        for (int i = 0; i < topWoodCount; i++) {
            JLabel WoodLabelTop = new JLabel(new ImageIcon("src/images/WoodTop.png"));
            int ranposTop = random.nextInt(351);
            WoodLabelTop.setBounds(700 + i * 350, TopWoodY, 100, ranposTop); 
            WoodLabels.add(WoodLabelTop);
            panel.add(WoodLabelTop);
        }

        int landWoodCount = random.nextInt(1000) +2;
        for (int i = 0; i < landWoodCount; i++) {
            JLabel WoodLabelLand = new JLabel(new ImageIcon("src/images/WoodLand.png"));
            int ranposLand = 500+random.nextInt(101);
            WoodLabelLand.setBounds(700 + i * 350, LandWoodY, 100, ranposLand); 
            WoodLabels.add(WoodLabelLand);
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
        // AI แก้ไข
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
                sb.ResetScore();
                character = new Character_page(frame);
            }
        });
    }

    private void moveWood() {
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

        if (dragonPassedWood) {
            int addedScore = sb.CountScore();
            //sb.Max_Score();
            score.setText("Score: " + addedScore);
        }

        Rectangle dragonBounds = dragonLabel.getBounds();
        for (JLabel WoodLabel : WoodLabels) {
            Rectangle rockBounds = WoodLabel.getBounds();
            if (dragonBounds.intersects(rockBounds)) {
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
                        Rectangle dragonBounds = dragonLabel.getBounds();
                        Rectangle fireballBounds = newFireballLabel.getBounds();
                        if (dragonBounds.intersects(fireballBounds)) {
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
