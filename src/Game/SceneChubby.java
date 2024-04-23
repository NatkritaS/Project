package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
public class SceneChubby {
    private ImageIcon background;
    private JLabel chubbyLabel;
    private ArrayList<JLabel> bubbleLabels;
    private JFrame frame;
    private JPanel panel;
    private Random random;
    private Character_page character;
    private JButton button_back;
    private Chubby chubby;
    private JLabel score;
    private Scoreboard sb;
    private JLabel goldfishLabel;
    protected static int Positiony = 350;
    protected static int Positionx = 400;
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
        score = new JLabel("Score:" );
        Font font = new Font("src/font/superpixel.ttf", Font.BOLD, 18);
        score.setFont(font);
        score.setBounds(600, 10, 100, 20);
        panel.add(score);
        frame.add(panel);
        frame.setVisible(true);
        sb = new Scoreboard();
        panel.requestFocus();
        background = new ImageIcon("src/images/Underthesea_background.png");
        bubbleLabels = new ArrayList<>();
        random = new Random();
        Random random = new Random();
        int a, b;
        a = random.nextInt(panel.getWidth() - 280-285);
        do {
            b = random.nextInt(panel.getWidth() - 200-305) + a + 500;
        } while (Math.abs(a - b) < 500);
        int TopBubbleY = 0; 
        int LandBubbleY = panel.getHeight() - 305; 
        int topBubbleCount = random.nextInt(100);
        for (int i = 0; i < topBubbleCount; i++) {
            JLabel BubbleLabelTop = new JLabel(new ImageIcon("src/images/bubbleTop.png"));
            int ranposTop = random.nextInt(401);
            BubbleLabelTop.setBounds(700 + i * 350, TopBubbleY, 100, ranposTop); 
            bubbleLabels.add(BubbleLabelTop);
            panel.add(BubbleLabelTop);
        }
 
        int landWoodCount = random.nextInt(100);
        for (int i = 0; i < landWoodCount; i++) {
            JLabel BubbleLabelLand = new JLabel(new ImageIcon("src/images/bubbleLand.png"));
            int ranposLand = 500+random.nextInt(101);
            BubbleLabelLand.setBounds(700 + i * 350, LandBubbleY, 100, ranposLand); 
            bubbleLabels.add(BubbleLabelLand);
            panel.add(BubbleLabelLand);
        }
        Positionx = -50;
        goldfishLabel = new JLabel(new ImageIcon("src/images/goldfish.png"));
        goldfishLabel.setBounds(Positionx, Positiony, 50, 50);
        panel.add(goldfishLabel);
        moveGoldfish();
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
                    moveBubble();
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
        button_back.setBounds(0, 5, 95, 20);
        panel.add(button_back);
        button_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                character = new Character_page(frame);
            }
        });
    }
    private void moveBubble() {
    	boolean chubbyPassedChain = false;
    	int lastChainX = 0;
        for (JLabel bubbleLabel : bubbleLabels) {
            int x = bubbleLabel.getX();
            if (x <= -200) { //หลุดขอบจอไป -20 หายไปเลย สมมติหินมีขนาด x = 40 เลยจอไปครึ่งนึงหายไปเลย
            	bubbleLabel.setVisible(false);
                //เช็คตำแหน่ง
            } else {
                bubbleLabel.setLocation(x - 8, bubbleLabel.getY());
            }
            if (x > lastChainX && x + bubbleLabel.getWidth() <= chubbyLabel.getX() && !chubbyPassedChain) {
            	chubbyPassedChain = true;
                lastChainX = x;
            }
        }
 
        if (chubbyPassedChain) {
            int addedScore = sb.CountScore();
            sb.Max_Score();
            score.setText("Score: " + addedScore);
        }
        Rectangle chubbyBounds = chubbyLabel.getBounds();
        for (JLabel bubbleLabel : bubbleLabels) {
            Rectangle bubbleBounds = bubbleLabel.getBounds();
            if (chubbyBounds.intersects(bubbleBounds)) { //ตรวจสอบว่านกชนโซ่ไหม .intersects() เอาไว้ทดสอบว่ามันชนกันไหม
                gameOver();
                return;
            }
        }
    }
    private void moveGoldfish() {
        Thread moveGoldfishThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    Positionx -= 5;
                    goldfishLabel.setBounds(Positionx, Positiony, 250, 100);
                    if (Positionx < -50) {
                        Positionx = frame.getWidth();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveGoldfishThread.start();
    }
    private void gameOver() {
        JOptionPane.showMessageDialog(frame, "Game Over");
        System.exit(0);
    }
}