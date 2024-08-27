package Game.GUI;

import java.awt.Font;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    private static final String LIVES_TEXT = "Lives: ";
    private static final String SCORE_TEXT = "Score: ";

    private JLabel livesLabel;
    private JLabel scoreLabel;

    public InfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initializeLabels();
        setBackground(Color.LIGHT_GRAY);
    }

    private void initializeLabels() {
        livesLabel = new JLabel(LIVES_TEXT + "3");
        scoreLabel = new JLabel(SCORE_TEXT + "0");
        livesLabel.setFont(LABEL_FONT);
        scoreLabel.setFont(LABEL_FONT);
        add(livesLabel);
        add(scoreLabel);
    }

    public void updateLives(int lives) {
        livesLabel.setText(LIVES_TEXT + lives);
    }

    public void updateScore(int score) {
        scoreLabel.setText(SCORE_TEXT + score);
    }
}