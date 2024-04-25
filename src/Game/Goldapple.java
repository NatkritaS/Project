package Game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Goldapple {
    private JLabel goldappleLabel;
    private JPanel panel;

    public Goldapple() {
    	goldappleLabel = new JLabel(new ImageIcon("src/images/goldapple.png"));
    	goldappleLabel.setBounds(0, 0, 50, 50);
    	panel.add(goldappleLabel);
    }

    public JLabel getGoldappleLabel() {
        return goldappleLabel;
    }

    public void updatePosition(int x, int y) {
    	goldappleLabel.setBounds(x, y, 50, 50);
    }
}
