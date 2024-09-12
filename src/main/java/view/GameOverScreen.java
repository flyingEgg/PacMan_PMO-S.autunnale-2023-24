package main.java.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import main.java.controller.Controller;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameOverScreen extends JFrame {
    private final Controller controller;
    private final int score;

    public GameOverScreen(Controller controller, int score) {
        this.controller = controller;
        this.score = score;
        setupGameOverScreen();
    }

    public void setupGameOverScreen() {
        setTitle("Game Over");

        try {
            BufferedImage icon = ImageIO.read(getClass().getResource("/main/java/view/images/right.gif"));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dell'icona: " + e.getMessage());
        }

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pannello sfondo
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        try {
            BufferedImage backgroundImage = ImageIO
                    .read(getClass().getResource("/main/java/view/images/background.jpg"));
            backgroundPanel.setBackgroundImage(backgroundImage);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dell'immagine di sfondo: " + e.getMessage());
        }
        backgroundPanel.setLayout(new BorderLayout());

        // Pannello punteggio e pulsanti
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Label del punteggio
        JLabel scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creazione pulsanti
        JButton restartButton = new RoundedButton("Restart Game");
        restartButton.setBackground(new Color(34, 139, 34)); // Verde scuro
        restartButton.setFont(new Font("Arial", Font.BOLD, 22));
        restartButton.setMaximumSize(new Dimension(200, 50));
        restartButton.addActionListener(e -> {
            controller.restartGame();
            dispose(); // Chiude la schermata di Game Over
        });

        JButton menuButton = new RoundedButton("Main Menu");
        menuButton.setBackground(new Color(220, 20, 60)); // Rosso scuro
        menuButton.setFont(new Font("Arial", Font.BOLD, 22));
        menuButton.setMaximumSize(new Dimension(200, 50));
        menuButton.addActionListener(e -> {
            controller.showMainMenu();
            dispose(); // Chiude la schermata di Game Over
        });

        panel.add(Box.createVerticalStrut(50));
        panel.add(scoreLabel);
        panel.add(Box.createVerticalGlue());
        panel.add(restartButton);
        panel.add(Box.createVerticalStrut(20)); // Spazio tra i pulsanti
        panel.add(menuButton);
        panel.add(Box.createVerticalStrut(50));

        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backgroundPanel.add(panel, BorderLayout.CENTER);
        add(backgroundPanel);
        setVisible(true);
    }
}
