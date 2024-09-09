package main.java.view;

import main.java.model.Model;
import main.java.model.API.GameStatisticsListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class InfoPanel extends JPanel implements GameStatisticsListener {
    private static final Font LABEL_FONT; // Static Font for lazy initialization
    private static final String SCORE_TEXT = "Score: ";
    private static final String SUPERMODE_TEXT = "Supermode: ";
    private Model model;
    private JLabel scoreLabel;
    private JLabel superModeLabel;
    private ArrayList<JLabel> lifeIcons;
    private JPanel livesPanel;
    private ImageIcon heartIcon;
    private Boolean isAnimating = false;

    static {
        Font tempFont = null;
        try (InputStream fontStream = InfoPanel.class.getResourceAsStream("/main/java/view/fonts/Pixel.ttf")) {
            if (fontStream != null) {
                tempFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(tempFont);
                tempFont = tempFont.deriveFont(Font.BOLD, 20); // Set font size
            } else {
                System.err.println("Font file not found.");
                tempFont = new Font("Arial", Font.BOLD, 20);
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            tempFont = new Font("Arial", Font.BOLD, 20);
        }
        LABEL_FONT = tempFont;
    }

    public InfoPanel(Model model) {
        this.model = model;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        initializeIcons();
        initializeLabels();
        this.model.addStatisticsListener(this);
    }

    // Initialize life icons (heart.png)
    private void initializeIcons() {
        heartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/main/java/view/images/heart.png")));
        lifeIcons = new ArrayList<>();
        livesPanel = new JPanel();
        livesPanel.setBackground(Color.BLACK);
        livesPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Layout to align icons
        updateLivesIcons(this.model.getLives());
    }

    // Update life icons based on the number of lives
    private void updateLivesIcons(int lives) {
        livesPanel.removeAll(); // Clear panel before adding new icons
        lifeIcons.clear(); // Clear the list

        for (int i = 0; i < lives; i++) {
            JLabel lifeLabel = new JLabel(heartIcon);
            lifeIcons.add(lifeLabel);
            livesPanel.add(lifeLabel);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Place livesPanel above the score
        add(livesPanel, gbc);

        livesPanel.revalidate();
        livesPanel.repaint();
    }

    // Initialize labels for score and supermode
    private void initializeLabels() {
        scoreLabel = new JLabel(SCORE_TEXT + this.model.getScore());
        scoreLabel.setFont(LABEL_FONT);
        scoreLabel.setForeground(Color.YELLOW);

        superModeLabel = new JLabel(SUPERMODE_TEXT + "Not active");
        superModeLabel.setFont(LABEL_FONT);
        superModeLabel.setForeground(Color.GREEN);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Place scoreLabel below the livesPanel
        add(scoreLabel, gbc);

        gbc.gridy = 2;
        add(superModeLabel, gbc);

        setPreferredSize(new Dimension(150, 100));
    }

    // Set score with animation
    public void setScore(int score) {
        scoreLabel.setText(SCORE_TEXT + score);

        // Check for animation trigger and ensure no animation is currently running
        if ((score == 100 || score == 200 || score == 500 || score % 1000 == 0) && !isAnimating) {
            isAnimating = true;
            animateScoreChange();
        }
    }

    // Update life icons when the number of lives changes
    public void setLives(int lives) {
        updateLivesIcons(lives);
    }

    // Set supermode status
    public void setSuperModeStatus(int movesRemaining) {
        if (movesRemaining > 0) {
            superModeLabel.setText(SUPERMODE_TEXT + "Active (" + movesRemaining + " moves remaining)");
            superModeLabel.setForeground(Color.RED);
        } else {
            superModeLabel.setText(SUPERMODE_TEXT + "Not active");
            superModeLabel.setForeground(Color.GREEN);
        }
    }

    // Animate score change
    private void animateScoreChange() {
        Timer timer = new Timer(100, new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent e) {
                // Alternate the score label color for the animation effect
                if (count % 2 == 0) {
                    scoreLabel.setForeground(Color.YELLOW);
                } else {
                    scoreLabel.setForeground(Color.RED);
                }
                count++;
                // Stop the timer and reset isAnimating flag after animation is done
                if (count > 6) { // Adjust the number of animation steps as needed
                    ((Timer) e.getSource()).stop();
                    isAnimating = false; // Reset flag after animation
                }
            }
        });
        timer.start();
    }

    // Listener for score change
    @Override
    public void onScoreChanged(int newScore) {
        System.out.println("Score changed: " + newScore); // Debug
        SwingUtilities.invokeLater(() -> setScore(newScore));
    }

    // Listener for lives change
    @Override
    public void onLivesChanged(int newLives) {
        System.out.println("Lives changed: " + newLives); // Debug
        SwingUtilities.invokeLater(() -> setLives(newLives));
    }
}