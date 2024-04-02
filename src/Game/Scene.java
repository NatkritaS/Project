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
        dragonLabel.setBounds(d.getxPosition(), d.getyPosition(), 130 , 100);
        panel.add(dragonLabel);

        rockLabels = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(350); // สุ่มตำแหน่ง x ของหิน
            int y = random.nextInt(450); // สุ่มตำแหน่ง y ของหิน
            JLabel rockLabel = new JLabel(new ImageIcon("src/images/landRock.png"));
            rockLabel.setBounds(x, y, 100, 150); // กำหนดขนาดและตำแหน่งของหิน
            rockLabels.add(rockLabel); // เพิ่ม JLabel ของหินลงใน ArrayList
            panel.add(rockLabel); // เพิ่ม JLabel ของหินลงใน JPanel
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Scene();
            }
        });
    }
}