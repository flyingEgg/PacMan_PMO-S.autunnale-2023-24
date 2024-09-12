package main.java.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import main.java.controller.Controller;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameWonScreen extends JFrame {
    private final Controller controller;
    private final int score;

    public GameWonScreen(Controller controller, int score) {
        this.controller = controller;
        this.score = score;
        setupGameWonScreen();
    }

    public void setupGameWonScreen() {
        setTitle("You Win!");

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
        JButton keepPlayingButton = new RoundedButton("Keep Playing");
        keepPlayingButton.setBackground(new Color(34, 139, 34)); // Verde scuro
        keepPlayingButton.setFont(new Font("Arial", Font.BOLD, 22));
        keepPlayingButton.setMaximumSize(new Dimension(200, 50));
        keepPlayingButton.addActionListener(e -> {
            controller.keepPlayingWin();
            dispose(); // Chiude la schermata di Vittoria
        });

        JButton menuButton = new RoundedButton("Main Menu");
        menuButton.setBackground(new Color(220, 20, 60)); // Rosso scuro
        menuButton.setFont(new Font("Arial", Font.BOLD, 22));
        menuButton.setMaximumSize(new Dimension(200, 50));
        menuButton.addActionListener(e -> {
            controller.showMainMenu();
            dispose(); // Chiude la schermata di Vittoria
        });

        panel.add(Box.createVerticalStrut(50));
        panel.add(scoreLabel);
        panel.add(Box.createVerticalGlue());
        panel.add(keepPlayingButton);
        panel.add(Box.createVerticalStrut(20)); // Spazio tra i pulsanti
        panel.add(menuButton);
        panel.add(Box.createVerticalStrut(50));

        keepPlayingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backgroundPanel.add(panel, BorderLayout.CENTER);
        add(backgroundPanel);
        setVisible(true);
    }
}
