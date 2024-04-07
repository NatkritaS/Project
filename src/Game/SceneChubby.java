package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SceneChubby {
    private ImageIcon background;
    private JLabel chubbyLabel;
    private ArrayList<JLabel> chainLabels;
    private JFrame frame;
    private JPanel panel;
    private Random random;
    private Chubby chubby;
    protected static int Positiony = 0;

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

        background = new ImageIcon("src/images/Underthesea_background.png");

        chainLabels = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < 2; i++) {
        	for (int j = 0; j < 3; j++) {
            int x = random.nextInt(600);
            int y = random.nextInt(500);

            JLabel chainLabelTop = new JLabel(new ImageIcon("src/images/topChain.png"));
            JLabel chainLabelLand = new JLabel(new ImageIcon("src/images/landChain.png"));

            chainLabelLand.setBounds(0, 500 , 280, 305);
            chainLabelTop.setBounds(0, 0, 300, 400);

            chainLabels.add(chainLabelLand);
            chainLabels.add(chainLabelTop);

            panel.add(chainLabelLand);
            panel.add(chainLabelTop);
        }
        }
        
        chubby = new Chubby();
        chubbyLabel = new JLabel(chubby.getChubbyBirdImage());
        chubbyLabel.setBounds(chubby.getxPosition(), chubby.getyPosition(), 130, 100);
        panel.add(chubbyLabel);
        frame.add(panel);
        frame.setVisible(true);

        // Al แก้ไขให้
        panel.requestFocus();

        // AI generate 
        Thread moveChainThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                    moveChain();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveChainThread.start();

        // Add KeyListener to the panel
        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int key1 = panel.getHeight() - chubby.getDragonHeight();

                if (key == KeyEvent.VK_UP) {
                    if (Positiony - Dragon.GRAVITY >= 0) {
                        Positiony -= Dragon.GRAVITY;
                    }
                    Chubby.flyup();
                } else if (key == KeyEvent.VK_DOWN) {
                    if (Positiony + Dragon.GRAVITY <= key1) {
                        Positiony += Dragon.GRAVITY;
                    }
                    Chubby.flyDown();
                }

                chubbyLabel.setBounds(chubby.getxPosition(), Positiony, 130, 100);
                panel.revalidate();
                panel.repaint();
            }
        });
        }
    
    // เคลื่อนหินไปทางซ้าย
    private void moveChain() {
        for (JLabel chainLabel : chainLabels) {
            int x =chainLabel.getX();
            if (x <= -200) { //หลุดขอบจอไป -20 หายไปเลย สมมติหินมีขนาด x = 40 เลยจอไปครึ่งนึงหายไปเลย
                // เซตตำแหน่ง
                chainLabel.setLocation(frame.getWidth(), chainLabel.getY());
            } else {
                // เคลื่อนหินไปทางซ้าย 
                chainLabel.setLocation(x - 8, chainLabel.getY());
            }
        }
    
    	
    }
    }
