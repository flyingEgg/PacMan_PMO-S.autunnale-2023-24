package main.java.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JLabel;

import main.java.controller.Controller;

/**
 * Classe che rappresenta il menu principale del gioco.
 */
public class MainMenu extends JFrame {
    private final Controller controller;
    private JLabel highScoreLabel;
    private Preferences prefs;

    /**
     * Costruisce un nuovo MainMenu con il controller fornito.
     *
     * @param controller Il controller del gioco per gestire le azioni dei pulsanti
     */
    public MainMenu(Controller controller) {
        this.controller = controller;
        this.prefs = Preferences.userNodeForPackage(MainMenu.class);
        setupMenu();
        updateHighScoreLabel();
    }

    /**
     * Configura e inizializza il menu principale.
     */
    public void setupMenu() {
        setTitle("Pacman - Main Menu");

        // Imposta l'icona della finestra
        try {
            BufferedImage icon = ImageIO.read(getClass().getResource("/main/java/view/images/right.gif"));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dell'icona: " + e.getMessage());
        }

        setSize(600, 400);
        setLocationRelativeTo(null); // Centro dello schermo
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

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Label per visualizzare l'high score
        highScoreLabel = new JLabel("High Score: " + getHighScore(), JLabel.CENTER);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        highScoreLabel.setForeground(Color.WHITE);

        // Pannello per l'high score
        JPanel highScorePanel = new JPanel();
        highScorePanel.setOpaque(false);
        highScorePanel.add(highScoreLabel);

        // Pulsante Start Game
        JButton startButton = new RoundedButton("Start Game");
        startButton.setBackground(new Color(34, 139, 34)); // Verde scuro
        startButton.setFont(new Font("Arial", Font.BOLD, 22));
        startButton.setMaximumSize(new Dimension(200, 50)); // Dimensione massima
        startButton.addActionListener(e -> {
            controller.startGame();
            dispose(); // Chiude il menu principale
        });

        // Pulsante Exit
        JButton exitButton = new RoundedButton("Exit");
        exitButton.setBackground(new Color(220, 20, 60)); // Rosso scuro
        exitButton.setFont(new Font("Arial", Font.BOLD, 22));
        exitButton.setMaximumSize(new Dimension(200, 50));
        exitButton.addActionListener(e -> System.exit(0));

        // Aggiungi pulsanti al pannello
        buttonPanel.add(Box.createVerticalStrut(50)); // Spazio sopra il pulsante Start Game
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalGlue()); // Spazio flessibile
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(50)); // Spazio sotto il pulsante Exit

        // Allinea i pulsanti al centro
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aggiungi il pannello dell'highscore al pannello di sfondo
        backgroundPanel.add(highScorePanel, BorderLayout.NORTH);

        // Aggiungi il pannello dei pulsanti al pannello di sfondo
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        // Aggiungi il pannello di sfondo alla finestra
        add(backgroundPanel);

        setVisible(true);
    }

    /**
     * Ottiene l'high score salvato localmente.
     *
     * @return L'high score salvato, o 0 se non è stato ancora salvato nessun
     *         punteggio.
     */
    private int getHighScore() {
        return controller.getHighScore();
    }

    /**
     * Aggiorna l'high score se il punteggio attuale è più alto dell'high score
     * salvato.
     *
     * @param score Il punteggio attuale che sarà confrontato con l'high score
     *              salvato.
     */
    public void updateHighScore(int score) {
        int highScore = controller.getHighScore();
        if (score > highScore) {
            controller.updateHighScore(score);
            highScoreLabel.setText("High Score: " + score); // Aggiorna la label con il nuovo high score
        }
    }

    /**
     * Aggiorna il testo dell'etichetta dell'high score quando il menu principale
     * viene mostrato.
     */
    private void updateHighScoreLabel() {
        highScoreLabel.setText("High Score: " + getHighScore()); // Rilegge l'high score salvato
    }
}