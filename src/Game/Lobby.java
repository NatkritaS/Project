package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Lobby {
    private JFrame frame;
    private JLabel screen;
    private JPanel panel;
    private JButton button1;
    private JButton button_howto;
    private JButton button_score;
    private JButton button_exit;

    public Lobby() {
        ImageIcon background = new ImageIcon("D:\\java\\animalAdventure\\src\\images\\background_lobby.png");
        ImageIcon start1 = new ImageIcon("D:\\java\\animalAdventure\\src\\images\\play_button.png");
        ImageIcon how = new ImageIcon("D:\\java\\animalAdventure\\src\\images\\how_button.png");
        ImageIcon score = new ImageIcon("D:\\java\\animalAdventure\\src\\images\\score_button.png");
        ImageIcon exit = new ImageIcon("D:\\java\\animalAdventure\\src\\images\\exit_button.png");

        frame = new JFrame();
        frame.setTitle("AnimalAdventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(700, 800);

        panel = new JPanel();
        panel.setLayout(null);

        button1 = new JButton();
        button1.setIcon(start1);
        button1.setContentAreaFilled(false);
        button1.setFocusPainted(false);
        button1.setOpaque(false);
        button1.setBounds(250, 320, 200, 50);
        panel.add(button1);

        button_howto = new JButton();
        button_howto.setIcon(how);
        button_howto.setContentAreaFilled(false);
        button_howto.setFocusPainted(false);
        button_howto.setOpaque(false);
        button_howto.setBounds(250, 380, 200, 50);
        panel.add(button_howto);

        button_score = new JButton();
        button_score.setIcon(score);
        button_score.setContentAreaFilled(false);
        button_score.setFocusPainted(false);
        button_score.setOpaque(false);
        button_score.setBounds(250, 440, 200, 50);
        panel.add(button_score);

        button_exit = new JButton();
        button_exit.setIcon(exit);
        button_exit.setContentAreaFilled(false);
        button_exit.setFocusPainted(false);
        button_exit.setOpaque(false);
        button_exit.setBounds(250, 500, 200, 50);
        panel.add(button_exit);

        screen = new JLabel();
        screen.setIcon(background);
        screen.setBounds(0, 0, 700, 800);
        panel.add(screen);

        frame.add(panel);
        frame.setVisible(true);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // ปิดหน้าเดิม

                // เปลี่ยนหน้า
                Character_page characterPage = new Character_page();
            }
        });
    }
}