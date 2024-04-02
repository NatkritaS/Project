package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Scene {
    private ImageIcon background;
    private JLabel dragonLabel;
    private ArrayList<JLabel> rockLabels;
    private JFrame frame;
    private JPanel panel;
    private Random random;

    public Scene() {
        frame = new JFrame();
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

        Dragon d = new Dragon();
        dragonLabel = new JLabel(d.getDragonImage());
        dragonLabel.setBounds(d.getxPosition(), d.getyPosition(), 130, 100);
        panel.add(dragonLabel);

        rockLabels = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(600); // random x ให้อยู่ในเฟรม
            int y = random.nextInt(500); // random x ให้อยู่ในเฟรม

            JLabel rockLabel = new JLabel(new ImageIcon("src/images/landRock.png"));
            rockLabel.setBounds(x, 0, 100, 200);
            rockLabels.add(rockLabel);
            panel.add(rockLabel);
        }

        frame.add(panel);
        frame.setVisible(true);

        // AI generate 
        Thread moveRocksThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50); // ปรับ speed ของการเคลื่อนที่
                    moveRocks();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveRocksThread.start();
    }

    // เคลื่อนหินไปทางซ้าย
    private void moveRocks() {
        for (JLabel rockLabel : rockLabels) {
            int x = rockLabel.getX();
            if (x <= -20) { //หลุดขอบจอไป -20 หายไปเลย สมมติหินมีขนาด x = 40 เลยจอไปครึ่งนึงหายไปเลย
                // เซตตำแหน่
                rockLabel.setLocation(frame.getWidth(), rockLabel.getY());
            } else {
                // เคลื่อนหินไปทางซ้าย 
                rockLabel.setLocation(x - 5, rockLabel.getY());
            }
        }
    }
}