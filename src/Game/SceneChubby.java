package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SceneChubby {
    private static final Component CubbyLabel = null;
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
	private int goldfishCount = 0;
	
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
        score = new JLabel("Score:");
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
        Random rand = new Random();
        
        int totalbubbleCount = rand.nextInt(1000 - 2 + 1) + 2;
        
        for (int i = 0; i < totalbubbleCount; i++) {
            JLabel bubbleLabelTop = new JLabel(new ImageIcon("src/images/bubbleTop.png"));
            JLabel bubbleLabelLand = new JLabel(new ImageIcon("src/images/bubbleLand.png"));
            
            int LandbubbleY = rand.nextInt(401) + 300;
            int TopbubbleX = LandbubbleY - 700;
            
            bubbleLabelTop.setBounds(700 + i * 500, TopbubbleX, 100, 500);
            bubbleLabelLand.setBounds(700 + i * 500, LandbubbleY, 100, 500);
            bubbleLabelTop.setBounds(700 + i * 500, TopbubbleX, 100, 460);
            bubbleLabelLand.setBounds(700 + i * 500, LandbubbleY, 100, 445);
            
            bubbleLabels.add(bubbleLabelTop);
            bubbleLabels.add(bubbleLabelLand);
            
            panel.add(bubbleLabelTop);
            panel.add(bubbleLabelLand);
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

        Thread movebubbleThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    moveBubble();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        movebubbleThread.start();

        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int key1 = panel.getHeight() - chubby.getChubbyBirdHeight();
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
        boolean chubbyPassedBubble = false;
        int lastbubbleX = 0;

        for (JLabel bubbleLabel : bubbleLabels) {
            int x = bubbleLabel.getX();
            if (x <= -200) {
                bubbleLabel.setVisible(false);
            } else {
                bubbleLabel.setLocation(x - 8, bubbleLabel.getY());
            }

            if (x > lastbubbleX && x + bubbleLabel.getWidth() <= chubbyLabel.getX() && !chubbyPassedBubble) {
                chubbyPassedBubble = true;
                lastbubbleX = x;
            }
        }

        if (chubbyPassedBubble) {
            int addedScore = sb.CountScore();
            sb.Max_Score();
            score.setText("Score: " + addedScore);
        }

        Rectangle chubbyBounds = chubbyLabel.getBounds();
        for (JLabel bubbleLabel : bubbleLabels) {
            Rectangle bubbleBounds = bubbleLabel.getBounds();
            if (chubbyBounds.intersects(bubbleBounds)) {
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
                
                    if (random.nextInt(100) < 4) {
                        addNewGoldfish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveGoldfishThread.start();
    }
    private void addNewGoldfish() {
    	JLabel newGoldfishLabel = new JLabel(new ImageIcon("src/images/Goldfish.png"));
        newGoldfishLabel.setBounds(frame.getWidth(), random.nextInt(panel.getHeight()), 250, 100);
        panel.add(newGoldfishLabel);
        
        Thread moveSingleGoldfishThread = new Thread(() -> {
        	int xVelocity = -5;
            int yVelocity = 0;
            while (newGoldfishLabel.getX() > -50) {
                try {
                    Thread.sleep(20);
                    int newX = newGoldfishLabel.getX() + xVelocity;
                    int newY = newGoldfishLabel.getY() + yVelocity;
                    newGoldfishLabel.setLocation(newX, newY);
                    // เช็คการชนกับนก
                    Rectangle cubbyBounds = CubbyLabel.getBounds();
                    Rectangle goldfishBounds = newGoldfishLabel.getBounds();
                    if (cubbyBounds.intersects(goldfishBounds)) {
                        // ถ้านกอ้วนชนปลา ก็ลบปลานั้นออกจาก panel และเพิ่มคะแนน
                        panel.remove(newGoldfishLabel);
                        panel.revalidate();
                        panel.repaint();
                        goldfishCount++;
                        int addedScore = sb.CountScore() + goldfishCount;
                        score.setText("Score: " + addedScore);   
                        break;
                    } else {
                        panel.remove(newGoldfishLabel);
                        panel.revalidate();
                        panel.repaint();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        moveSingleGoldfishThread.start();
	}

	private void gameOver() {
        JOptionPane.showMessageDialog(frame, "Game Over");
        System.exit(0);
    }
}
