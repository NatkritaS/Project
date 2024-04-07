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
    protected static int Positiony = 0;

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

        background = new ImageIcon("src/images/background_sunny.png");

        rockLabels = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < 2; i++) {
        	for (int j = 0; j < 3; j++) {
            int x = random.nextInt(600);
            int y = random.nextInt(500);

            JLabel rockLabelTop = new JLabel(new ImageIcon("src/images/topRock.png"));
            JLabel rockLabelLand = new JLabel(new ImageIcon("src/images/landRock.png"));

            rockLabelLand.setBounds(0, 500 , 280, 305);
            rockLabelTop.setBounds(0, 0, 280, 285);

            rockLabels.add(rockLabelLand);
            rockLabels.add(rockLabelTop);

            panel.add(rockLabelLand);
            panel.add(rockLabelTop);
        }
        }
        
        dragon = new Dragon();
        dragonLabel = new JLabel(dragon.getDragonImage());
        dragonLabel.setBounds(dragon.getxPosition(), dragon.getyPosition(), 130, 100);
        panel.add(dragonLabel);
        frame.add(panel);
        frame.setVisible(true);

        // Al แก้ไขให้
        panel.requestFocus();

        // AI generate 
        Thread moveRocksThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                    moveRocks();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveRocksThread.start();

        // Add KeyListener to the panel
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

                dragonLabel.setBounds(dragon.getxPosition(), Positiony, 130, 100);
                panel.revalidate();
                panel.repaint();
            }
        });
        }
    
    // เคลื่อนหินไปทางซ้าย
    private void moveRocks() {
        for (JLabel rockLabel : rockLabels) {
            int x = rockLabel.getX();
            if (x <= -200) { //หลุดขอบจอไป -20 หายไปเลย สมมติหินมีขนาด x = 40 เลยจอไปครึ่งนึงหายไปเลย
                // เซตตำแหน่ง
                rockLabel.setLocation(frame.getWidth(), rockLabel.getY());
            } else {
                // เคลื่อนหินไปทางซ้าย 
                rockLabel.setLocation(x - 8, rockLabel.getY());
            }
        }
    
    	
    }
    }
    	
        


	