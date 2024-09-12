package main.java.view.GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import main.java.model.Model;
import main.java.model.API.GameStatisticsListener;

/**
 * Pannello informativo che visualizza il punteggio, le vite e lo stato del
 * Supermode.
 * Implementa l'interfaccia GameStatisticsListener per aggiornare dinamicamente
 * le informazioni.
 */
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
    private boolean isAnimating = false;
    private String statusText = "Not Active"; // Stato iniziale del Supermode

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

    /**
     * Costruisce un nuovo InfoPanel associato al modello fornito.
     *
     * @param model Il modello del gioco che fornisce le informazioni da
     *              visualizzare
     */
    public InfoPanel(Model model) {
        this.model = model;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(50, 50, 50));
        initializeIcons();
        initializeLabels();
        this.model.addStatisticsListener(this);
    }

    /**
     * Inizializza le etichette per il punteggio e lo stato del Supermode.
     */
    private void initializeLabels() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spaziatura attorno ai componenti
        gbc.fill = GridBagConstraints.HORIZONTAL; // Riempie orizzontalmente

        // Aggiungi livesPanel in alto
        gbc.gridx = 0;
        gbc.gridy = 0; // Riga superiore
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Si estende su tutte le colonne
        gbc.anchor = GridBagConstraints.CENTER; // Centrato orizzontalmente
        add(livesPanel, gbc);

        // Aggiungi scoreLabel sotto livesPanel
        gbc.gridy = 1; // Riga successiva
        scoreLabel = new JLabel(SCORE_TEXT + this.model.getScore());
        scoreLabel.setFont(LABEL_FONT);
        scoreLabel.setForeground(new Color(255, 223, 0));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel, gbc);

        // Aggiungi superModeLabel in basso
        gbc.gridy = 2; // Riga inferiore
        gbc.weighty = 1.0; // Spinge superModeLabel verso il basso
        gbc.anchor = GridBagConstraints.CENTER; // Centrato orizzontalmente
        superModeLabel = new JLabel(
                "<html><div style='text-align: center;'>" + SUPERMODE_TEXT + "<br>" + statusText + "</div></html>");
        superModeLabel.setFont(LABEL_FONT);
        superModeLabel.setForeground(new Color(173, 255, 47));
        superModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(superModeLabel, gbc);

        setPreferredSize(new Dimension(225, 150)); // Dimensione preferita
        revalidate();
        repaint();
    }

    /**
     * Inizializza le icone delle vite.
     */
    private void initializeIcons() {
        heartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/main/java/view/images/heart.png")));
        lifeIcons = new ArrayList<>();
        livesPanel = new JPanel();
        livesPanel.setOpaque(false); // Assicurati che il pannello sia trasparente
        livesPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Allineamento centrato per le icone
        updateLivesIcons(this.model.getLives());
    }

    /**
     * Aggiorna le icone delle vite in base al numero di vite.
     *
     * @param lives Il numero di vite da visualizzare
     */
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

    /**
     * Aggiorna il punteggio visualizzato.
     *
     * @param score Il nuovo punteggio da visualizzare
     */
    public void setScore(int score) {
        scoreLabel.setText(SCORE_TEXT + score);
        if ((score == 100 || score == 200 || score == 500 || score % 1000 == 0) && !isAnimating) {
            isAnimating = true;
            animateScoreChange();
        }
    }

    /**
     * Aggiorna il numero di vite visualizzate.
     *
     * @param lives Il nuovo numero di vite da visualizzare
     */
    public void setLives(int lives) {
        updateLivesIcons(lives);
    }

    /**
     * Aggiorna lo stato del Supermode visualizzato.
     *
     * @param movesRemaining Il numero di mosse rimanenti per il Supermode
     */
    public void setSuperModeStatus(int movesRemaining) {
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

    /**
     * Avvia l'animazione del cambiamento del punteggio.
     */
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