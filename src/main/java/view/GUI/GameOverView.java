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
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.controller.Controller;

/**
 * Classe che rappresenta la schermata di Game Over.
 * Consente di visualizzare il punteggio finale e aggiornare l'high score se
 * necessario.
 */
public class GameOverView extends JFrame {
    private final Controller controller;
    private final int score;

    /**
     * Costruisce una nuova schermata di Game Over.
     *
     * @param controller Il controller del gioco
     * @param score      Il punteggio finale del giocatore
     */
    public GameOverView(Controller controller, int score) {
        this.controller = controller;
        this.score = score;

        setupGameOverScreen();
    }

    /**
     * Configura e mostra la schermata di Game Over.
     */
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

        // Pannello di sfondo
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        try {
            BufferedImage backgroundImage = ImageIO
                    .read(getClass().getResource("/main/java/view/images/background.jpg"));
            backgroundPanel.setBackgroundImage(backgroundImage);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dell'immagine di sfondo: " + e.getMessage());
        }
        backgroundPanel.setLayout(new BorderLayout());

        // Controlla e aggiorna l'high score
        int highScore = controller.getHighScore(); // Ottieni l'high score attuale
        if (score > highScore) {
            controller.updateHighScore(score); // Aggiorna l'high score
            highScore = score; // Aggiorna la variabile locale
        }

        // Pannello per punteggio e pulsanti
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Etichetta del punteggio finale
        JLabel scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Etichetta dell'high score
        JLabel highScoreLabel = new JLabel("High Score: " + highScore);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        highScoreLabel.setForeground(Color.CYAN); // Colore per evidenziare l'high score
        highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        // Aggiungi componenti al pannello
        panel.add(Box.createVerticalStrut(50));
        panel.add(scoreLabel);
        panel.add(highScoreLabel); // Aggiungi l'etichetta dell'high score
        panel.add(Box.createVerticalGlue());
        panel.add(restartButton);
        panel.add(Box.createVerticalStrut(20)); // Spazio tra i pulsanti
        panel.add(menuButton);
        panel.add(Box.createVerticalStrut(50));

        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aggiungi il pannello al pannello di sfondo e al frame
        backgroundPanel.add(panel, BorderLayout.CENTER);
        add(backgroundPanel);

        // Rendi visibile la finestra
        setVisible(true);
    }
}