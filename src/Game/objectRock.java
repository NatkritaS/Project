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
       
    }

    public JPanel getPanel() {
        return panel;
    }
}
