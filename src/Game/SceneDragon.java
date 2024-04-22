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
    private  Lobby lobby;
    
    
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

        int topWoodCount = random.nextInt(100);
        for (int i = 0; i < topWoodCount; i++) {
            JLabel WoodLabelTop = new JLabel(new ImageIcon("src/images/WoodTop.png"));
            int ranposTop = random.nextInt(401);
            WoodLabelTop.setBounds(700 + i * 300, TopWoodY, 210, ranposTop); 
            WoodLabels.add(WoodLabelTop);
            panel.add(WoodLabelTop);
        }

        int landWoodCount = random.nextInt(100);
        for (int i = 0; i < landWoodCount; i++) {
            JLabel WoodLabelLand = new JLabel(new ImageIcon("src/images/WoodLand.png"));
            int ranposLand = 500+random.nextInt(201);
            WoodLabelLand.setBounds(700 + i * 300, LandWoodY, 210, ranposLand); 
            WoodLabels.add(WoodLabelLand);
            panel.add(WoodLabelLand);
        }

 
        
 
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
                character = new Character_page(frame);
            }
        });
    }
 
    private void moveWood() {
        boolean dragonPassedWood = false;
        for (JLabel WoodLabel : WoodLabels) {
            int x = WoodLabel.getX();
            if (x <= -200) {
                WoodLabel.setLocation(frame.getWidth(), WoodLabel.getY());
            } else {
                WoodLabel.setLocation(x - 8, WoodLabel.getY());
            }
            
           // if (x + WoodLabel.getWidth() == dragonLabel.getX()) {
             //   dragonPassedWood = true;
            }
        
      
 
        if (dragonPassedWood) {
        	int addedScore = sb.CountScore();
        	sb.Max_Score();
            score.setText("Score: " + addedScore);
        }
 
        // มังกรชนหินมั้ย
        Rectangle dragonBounds = dragonLabel.getBounds();
        for (JLabel WoodLabel : WoodLabels) {
            Rectangle rockBounds = WoodLabel.getBounds();
            if (dragonBounds.intersects(rockBounds)) {
                gameOver();
                return;
            }
        }
   }
        
 
	private void gameOver() {
		   JOptionPane.showMessageDialog(frame, "Game Over");
	        System.exit(0);
		  
	}
}