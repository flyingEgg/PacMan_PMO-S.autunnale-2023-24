package main.java.view;

import java.awt.Font;
import java.awt.Color;
import javax.swing.*;

import main.java.model.Game;
import main.java.model.API.GameStatisticsListener;

public class InfoPanel extends JPanel implements GameStatisticsListener {
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    private static final String LIVES_TEXT = "Lives: ";
    private static final String SCORE_TEXT = "Score: ";
    private Game game;
    private JLabel livesLabel;
    private JLabel scoreLabel;

    public InfoPanel(Game g) {
        this.game = g;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initializeLabels();
        setBackground(Color.LIGHT_GRAY);
        this.game.addStatisticsListener(this);
    }

    private void initializeLabels() {
        livesLabel = new JLabel(LIVES_TEXT + this.game.getLives());
        scoreLabel = new JLabel(SCORE_TEXT + this.game.getScore());
        livesLabel.setFont(LABEL_FONT);
        scoreLabel.setFont(LABEL_FONT);
        add(livesLabel);
        add(scoreLabel);
    }

    public void setLives(int lives) {
        livesLabel.setText(LIVES_TEXT + lives);
    }

    public void setScore(int score) {
        scoreLabel.setText(SCORE_TEXT + score);
    }

    @Override
    public void onScoreChanged(int newScore) {
        SwingUtilities.invokeLater(() -> setScore(newScore));
    }

    @Override
    public void onLivesChanged(int newLives) {
        SwingUtilities.invokeLater(() -> setLives(newLives));
    }
}