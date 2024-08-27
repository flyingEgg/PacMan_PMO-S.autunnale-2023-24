package Game.GUI;

import java.awt.Font;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
    private JLabel livesLabel;
    private JLabel scoreLabel;

    public InfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        livesLabel = new JLabel("Lives: 3");
        scoreLabel = new JLabel("Score: 0");
        livesLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(livesLabel);
        add(scoreLabel);
        setBackground(Color.LIGHT_GRAY);
    }

    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
