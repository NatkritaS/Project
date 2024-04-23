package Game;

import javax.swing.*;

class Fireball {
    private JLabel fireballLabel;

    public Fireball() {
        fireballLabel = new JLabel(new ImageIcon("src/images/fireball.png"));
        fireballLabel.setBounds(0, 0, 50, 50);
    }

    public JLabel getFireballLabel() {
        return fireballLabel;
    }

    public void updatePosition(int x, int y) {
        fireballLabel.setBounds(x, y, 50, 50);
    }
}