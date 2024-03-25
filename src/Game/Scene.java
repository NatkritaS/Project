package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Scene {

    private JLabel screen;
    private ArrayList<ImageIcon> backgrounds;
    private JFrame frame;
    private JPanel panel;
    private Random random;

    public Scene() {
        frame = new JFrame();
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        backgrounds = new ArrayList<>();
        backgrounds.add(new ImageIcon("src/images/background_rainning.png"));
        backgrounds.add(new ImageIcon("src/images/background_rainning2.png"));
        backgrounds.add(new ImageIcon("src/images/background_rainning3.png"));
        backgrounds.add(new ImageIcon("src/images/background_sunny.png"));
        backgrounds.add(new ImageIcon("src/images/background_sunny2.png"));
        backgrounds.add(new ImageIcon("src/images/background_sunny3.png"));

        random = new Random();

        setBackground();
        startBackgroundTimer();
        
        frame.add(panel);
        frame.setVisible(true);
    }

    private void setBackground() {
        screen = new JLabel();
        panel.add(screen, BorderLayout.CENTER);
        changeBackground();
    }

    private void changeBackground() {
        int index = random.nextInt(backgrounds.size());
        screen.setIcon(backgrounds.get(index));
    }

    private void startBackgroundTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                changeBackground();
            }
        };
        // เริ่มต้น Timer สำหรับสุ่มรูปภาพทุก 3 วินาที
        timer.schedule(task, 0, 3000);
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
