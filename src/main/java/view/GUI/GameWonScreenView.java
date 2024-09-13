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
 * Rappresenta la schermata di vittoria mostrata quando il giocatore vince la
 * partita.
 * Visualizza il punteggio del giocatore e fornisce opzioni per continuare a
 * giocare o tornare al menu principale.
 */
public class GameWonScreenView extends JFrame {
    private final Controller controller; // Controller per gestire le azioni di gioco
    private final int score; // Punteggio del giocatore da visualizzare

    /**
     * Costruisce una nuova schermata di vittoria.
     *
     * @param controller Il controller per gestire le azioni di gioco
     * @param score      Il punteggio da visualizzare sulla schermata
     */
    public GameWonScreenView(Controller controller, int score) {
        this.controller = controller;
        this.score = score;
        setupGameWonScreen();
    }

    /**
     * Configura la schermata di vittoria con titolo, icona, layout, immagine di
     * sfondo e pulsanti.
     */
    private void setupGameWonScreen() {
        setTitle("Hai Vinto!");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Imposta l'icona dell'applicazione
        setApplicationIcon();

        // Crea e configura il pannello di sfondo
        BackgroundPanel backgroundPanel = createBackgroundPanel();

        // Crea e aggiungi il pannello dei contenuti con punteggio e pulsanti
        JPanel contentPanel = createContentPanel();

        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        add(backgroundPanel);

        setVisible(true);
    }

    /**
     * Imposta l'icona dell'applicazione utilizzando un file di risorsa.
     */
    private void setApplicationIcon() {
        try {
            BufferedImage icon = ImageIO.read(getClass().getResource("/main/java/view/images/right.gif"));
            setIconImage(icon);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dell'icona: " + e.getMessage());
        }
    }

    /**
     * Crea e restituisce un BackgroundPanel con un'immagine di sfondo.
     *
     * @return Il BackgroundPanel con l'immagine di sfondo impostata
     */
    private BackgroundPanel createBackgroundPanel() {
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        try {
            BufferedImage backgroundImage = ImageIO
                    .read(getClass().getResource("/main/java/view/images/background.jpg"));
            backgroundPanel.setBackgroundImage(backgroundImage);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dell'immagine di sfondo: " + e.getMessage());
        }
        backgroundPanel.setLayout(new BorderLayout());
        return backgroundPanel;
    }

    /**
     * Crea e restituisce un JPanel con l'etichetta del punteggio e i pulsanti per
     * la schermata di vittoria.
     *
     * @return Il JPanel contenente l'etichetta del punteggio e i pulsanti
     */
    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Etichetta che visualizza il punteggio
        JLabel scoreLabel = new JLabel("Il tuo punteggio: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Pulsante per continuare a giocare
        JButton keepPlayingButton = new RoundedButton("Continua a Giocare");
        keepPlayingButton.setBackground(new Color(34, 139, 34)); // Verde scuro
        keepPlayingButton.setFont(new Font("Arial", Font.BOLD, 22));
        keepPlayingButton.setMaximumSize(new Dimension(200, 50));
        keepPlayingButton.addActionListener(e -> {
            controller.keepPlayingWin();
            dispose(); // Chiude la schermata di vittoria
        });

        // Pulsante per tornare al menu principale
        JButton menuButton = new RoundedButton("Menu Principale");
        menuButton.setBackground(new Color(220, 20, 60)); // Rosso scuro
        menuButton.setFont(new Font("Arial", Font.BOLD, 22));
        menuButton.setMaximumSize(new Dimension(200, 50));
        menuButton.addActionListener(e -> {
            controller.showMainMenu();
            dispose(); // Chiude la schermata di vittoria
        });

        // Aggiungi componenti al pannello
        panel.add(Box.createVerticalStrut(50));
        panel.add(scoreLabel);
        panel.add(Box.createVerticalGlue());
        panel.add(keepPlayingButton);
        panel.add(Box.createVerticalStrut(20)); // Spazio tra i pulsanti
        panel.add(menuButton);
        panel.add(Box.createVerticalStrut(50));

        keepPlayingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }
}