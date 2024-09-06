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

public class InfoPanel extends JPanel implements GameStatisticsListener {
    private static final Font LABEL_FONT; // Modificato da static final Font a static Font per inizializzazione
                                          // differita
    private static final String SCORE_TEXT = "Score: ";
    private Model model;
    private JLabel scoreLabel;
    private ArrayList<JLabel> lifeIcons;
    private JPanel livesPanel;
    private ImageIcon heartIcon;

    static {
        Font tempFont = null;
        try (InputStream fontStream = InfoPanel.class.getResourceAsStream("/main/java/view/fonts/Pixel.ttf")) {
            if (fontStream != null) {
                tempFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(tempFont);
                tempFont = tempFont.deriveFont(Font.BOLD, 20); // Imposta la dimensione del font
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

    // Inizializza le icone di vita (heart.png)
    private void initializeIcons() {
        heartIcon = new ImageIcon(getClass().getResource("/main/java/view/images/heart.png"));
        lifeIcons = new ArrayList<>();
        livesPanel = new JPanel();
        livesPanel.setBackground(Color.BLACK);
        livesPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Aggiunto layout per allineare le icone
        updateLivesIcons(this.model.getLives());
    }

    // Aggiunge le icone di vita basate sul numero di vite
    private void updateLivesIcons(int lives) {
        livesPanel.removeAll(); // Pulisce il pannello prima di aggiungere le nuove icone
        lifeIcons.clear(); // Pulisce la lista

        for (int i = 0; i < lives; i++) {
            JLabel lifeLabel = new JLabel(heartIcon);
            lifeIcons.add(lifeLabel);
            livesPanel.add(lifeLabel);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Posiziona il livesPanel sopra al punteggio
        add(livesPanel, gbc);

        livesPanel.revalidate();
        livesPanel.repaint();
    }

    // Inizializza le etichette per il punteggio
    private void initializeLabels() {
        scoreLabel = new JLabel(SCORE_TEXT + this.model.getScore());
        scoreLabel.setFont(LABEL_FONT);
        scoreLabel.setForeground(Color.YELLOW);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Posiziona il punteggio sotto al pannello delle vite
        add(scoreLabel, gbc);

        setPreferredSize(new Dimension(150, 100));
    }

    // Imposta il punteggio con l'animazione
    public void setScore(int score) {
        scoreLabel.setText(SCORE_TEXT + score);
        if (score == 100 || score == 200 || score == 500 || score % 1000 == 0) { // Controllo per attivare l'animazione
            animateScoreChange();
        }
    }

    // Aggiorna le icone delle vite quando il numero cambia
    public void setLives(int lives) {
        updateLivesIcons(lives);
    }

    // Animazione quando il punteggio cambia
    private void animateScoreChange() {
        Timer timer = new Timer(100, new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent e) {
                if (count % 2 == 0) {
                    scoreLabel.setForeground(Color.YELLOW);
                } else {
                    scoreLabel.setForeground(Color.RED);
                }
                count++;
                if (count > 6)
                    ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    // Listener per il cambiamento del punteggio
    @Override
    public void onScoreChanged(int newScore) {
        SwingUtilities.invokeLater(() -> setScore(newScore));
    }

    // Listener per il cambiamento delle vite
    @Override
    public void onLivesChanged(int newLives) {
        SwingUtilities.invokeLater(() -> setLives(newLives));
    }
}
