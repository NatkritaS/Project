package Game;

import javax.swing.*;
import javax.swing.text.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class objectRock {
    private ArrayList<ImageIcon> rock;
    private ImageIcon topRock, landRock;
    private JPanel panel;

    public objectRock() {
        rock = new ArrayList<>();
        this.topRock = new ImageIcon("src/images/topRock.png");
        this.landRock = new ImageIcon("src/images/landRock.png");
        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!rock.isEmpty()) {
                    for (ImageIcon r : rock) {
                        g.drawImage(r.getImage(), 0, 0, getWidth(0), getHeight(0), null);
                    }
                }
            }

			private int getHeight(int j) {
				j = 250;
				return j;
			}

			private int getWidth(int i) {
				i = 170;
				return i;
			}
        };
        panel.setVisible(true);
        random();
    }

    public void random() {
        Random r = new Random();
        int randomPos = r.nextInt(); // สุ่มเลข 0 หรือ 1 เพื่อเลือกภาพหิน
        if (randomPos == 0) {
            rock.add(topRock);
        } else {
            rock.add(landRock);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        objectRock objectRock = new objectRock();
        frame.add(objectRock.getPanel());

        frame.setVisible(true);
    }
}
