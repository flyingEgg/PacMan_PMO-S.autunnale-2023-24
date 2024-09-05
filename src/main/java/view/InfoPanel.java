package main.java.view;

import main.java.model.Model;
import main.java.model.API.GameStatisticsListener;

import java.awt.Font;
import java.awt.Color;
import javax.swing.*;

public class InfoPanel extends JPanel implements GameStatisticsListener {
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    private static final String LIVES_TEXT = "Lives: ";
    private static final String SCORE_TEXT = "Score: ";
    private Model model;
    private JLabel livesLabel;
    private JLabel scoreLabel;

    public InfoPanel(Model g) {
        this.model = g;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initializeLabels();
        setBackground(Color.LIGHT_GRAY);
        this.model.addStatisticsListener(this);
    }

    private void initializeLabels() {
        livesLabel = new JLabel(LIVES_TEXT + this.model.getLives());
        scoreLabel = new JLabel(SCORE_TEXT + this.model.getScore());
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