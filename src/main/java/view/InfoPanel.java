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
    private static final Font LABEL_FONT;
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
                tempFont = tempFont.deriveFont(Font.BOLD, 20);
            } else {
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(50, 50, 50));
        initializeIcons();
        initializeLabels();
        this.model.addStatisticsListener(this);
    }

    private void initializeLabels() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally

        // Add livesPanel at the top
        gbc.gridx = 0;
        gbc.gridy = 0; // Top row
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        gbc.anchor = GridBagConstraints.CENTER; // Center horizontally
        add(livesPanel, gbc);

        // Add scoreLabel below livesPanel
        gbc.gridy = 1; // Next row
        scoreLabel = new JLabel(SCORE_TEXT + this.model.getScore());
        scoreLabel.setFont(LABEL_FONT);
        scoreLabel.setForeground(new Color(255, 223, 0));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel, gbc);

        // Add superModeLabel at the bottom
        gbc.gridy = 2; // Bottom row
        gbc.weighty = 1.0; // Push superModeLabel to the bottom
        gbc.anchor = GridBagConstraints.CENTER; // Center horizontally
        superModeLabel = new JLabel(
                "<html>" + SUPERMODE_TEXT + "<div style='text-align: center;'>Not active</div></html>");
        superModeLabel.setFont(LABEL_FONT);
        superModeLabel.setForeground(new Color(173, 255, 47));
        superModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(superModeLabel, gbc);

        setPreferredSize(new Dimension(225, 150)); // Adjust size as needed
        revalidate();
        repaint();
    }

    private void initializeIcons() {
        heartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/main/java/view/images/heart.png")));
        lifeIcons = new ArrayList<>();
        livesPanel = new JPanel();
        livesPanel.setOpaque(false); // Make sure the panel is transparent
        livesPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center alignment for icons
        updateLivesIcons(this.model.getLives());

        add(livesPanel);
    }

    private void updateLivesIcons(int lives) {
        livesPanel.removeAll();
        lifeIcons.clear();

        for (int i = 0; i < lives; i++) {
            JLabel lifeLabel = new JLabel(heartIcon);
            lifeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lifeLabel.setToolTipText("Life " + (i + 1));

            lifeIcons.add(lifeLabel);
            livesPanel.add(lifeLabel);
        }

        livesPanel.revalidate();
        livesPanel.repaint();
    }

    public void setScore(int score) {
        scoreLabel.setText(SCORE_TEXT + score);
        if ((score == 100 || score == 200 || score == 500 || score % 1000 == 0) && !isAnimating) {
            isAnimating = true;
            animateScoreChange();
        }
    }

    public void setLives(int lives) {
        updateLivesIcons(lives);
    }

    public void setSuperModeStatus(int movesRemaining) {
        String statusText;
        if (movesRemaining > 0) {
            statusText = "Active<br>" + movesRemaining + " moves left";
            superModeLabel.setForeground(Color.RED);
        } else {
            statusText = "Not active";
            superModeLabel.setForeground(new Color(173, 255, 47));
        }
        superModeLabel.setText(
                "<html><div style='text-align: center;'>" + SUPERMODE_TEXT + "<br>" + statusText + "</div></html>");
    }

    private void animateScoreChange() {
        Timer timer = new Timer(100, new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent e) {
                if (count % 2 == 0) {
                    scoreLabel.setForeground(new Color(255, 223, 0));
                } else {
                    scoreLabel.setForeground(new Color(255, 69, 0));
                }
                count++;
                if (count > 6) {
                    ((Timer) e.getSource()).stop();
                    isAnimating = false;
                }
            }
        });
        timer.start();
    }

    @Override
    public void onScoreChanged(int newScore) {
        System.out.println("Score changed: " + newScore); // Debug
        SwingUtilities.invokeLater(() -> setScore(newScore));
    }

    @Override
    public void onLivesChanged(int newLives) {
        System.out.println("Lives changed: " + newLives); // Debug
        SwingUtilities.invokeLater(() -> setLives(newLives));
    }

    @Override
    public void onSuperModeStatusChanged(int movesRemaining) {
        System.out.println("SuperMode moves remaining: " + movesRemaining); // Debug
        SwingUtilities.invokeLater(() -> setSuperModeStatus(movesRemaining));
    }
}