//package Game;

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
    private Character_page character;
    private JButton button_back;
    private Chubby chubby;
    protected static int Positiony = 350;

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

                chainLabelLand.setBounds(500, -90, 300, 1300); //x,y,width,height
                chainLabelTop.setBounds(50, -250, 500, 900); 
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

        panel.requestFocus();

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

        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int key1 = panel.getHeight() - chubby.getDragonHeight();

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

                chubbyLabel.setBounds(chubby.getxPosition(), Positiony, 130, 100);
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
        button_back.setBounds(590, 5, 95, 20);
        panel.add(button_back);

        button_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                character = new Character_page(frame);
            }
        });
    }

    private void moveChain() {
        for (JLabel chainLabel : chainLabels) {
            int x = chainLabel.getX();
            if (x <= -200) { //หลุดขอบจอไป -20 หายไปเลย สมมติหินมีขนาด x = 40 เลยจอไปครึ่งนึงหายไปเลย
                //เช็คตำแหน่ง
                chainLabel.setLocation(frame.getWidth(), chainLabel.getY());
            } else {
                //เคลื่อนหินไปทางซ้าย
                chainLabel.setLocation(x - 8, chainLabel.getY());
            }
        }

        Rectangle chubbyBounds = chubbyLabel.getBounds(); 
        for (JLabel chainLabel : chainLabels) {
            Rectangle chainBounds = chainLabel.getBounds();
            if (chubbyBounds.intersects(chainBounds)) { //ตรวจสอบว่านกชนโซ่ไหม .intersects() เอาไว้ทดสอบว่ามันชนกันไหม
                gameOver(); 
            }
        }
    }

    private void gameOver() { 
        JOptionPane.showMessageDialog(frame, "Game Over");
        System.exit(0);
    }
}
