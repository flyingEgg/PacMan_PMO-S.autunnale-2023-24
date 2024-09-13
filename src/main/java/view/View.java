package main.java.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.controller.Controller;
import main.java.model.Movement.Direction;
import main.java.model.Exceptions.IllegalEntityMovementException;
import main.java.view.GUI.GameOverScreen;
import main.java.view.GUI.GamePanel;
import main.java.view.GUI.GameWonScreen;
import main.java.view.GUI.InfoPanel;
import main.java.view.GUI.MainMenu;

/**
 * Classe View per la visualizzazione dell'interfaccia utente del gioco.
 */
public class View extends JFrame {

    private Controller controller;
    private Map<String, ImageIcon> images;
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private MainMenu mainMenu;

    /**
     * Costruisce una vista con il modello e il controller specificati.
     *
     * @param controller l'istanza del controller
     */
    public View(Controller controller) {
        this.controller = controller;
    }

    /**
     * Imposta il controller per la vista.
     *
     * @param controller il nuovo controller da impostare
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Restituisce il pannello di gioco corrente.
     *
     * @return il pannello di gioco
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Inizializza la vista caricando le immagini, configurando la finestra e
     * impostando il listener per i tasti.
     */
    public void initializeView() {
        loadImages();
        setupWindow();
        setupStatusBar();
        setupKeyListener();
    }

    /**
     * Configura la finestra principale dell'applicazione.
     */
    private void setupWindow() {
        setTitle("Pacman");

        try {
            BufferedImage icon = ImageIO
                    .read(Objects.requireNonNull(getClass().getResource("/main/java/view/images/right.gif")));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dell'icona: " + e.getMessage());
        }

        setSize(666, 445);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        showMainMenu(); // Mostra il menu principale all'avvio
    }

    /**
     * Configura la barra di stato nella parte inferiore della finestra.
     */
    private void setupStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Benvenuto in Pacman!");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    /**
     * Imposta il listener per i tasti per gestire gli input dell'utente.
     */
    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    /**
     * Mostra la finestra di gioco e aggiorna i pannelli di gioco e informazioni.
     */
    public void showGameWindow() {
        if (gamePanel != null) {
            remove(gamePanel);
        }
        if (infoPanel != null) {
            remove(infoPanel);
        }
        gamePanel = new GamePanel(controller.getModel(), controller.getModel().getPacman(),
                controller.getModel().getGhosts(), images);
        infoPanel = new InfoPanel(controller.getModel());
        add(gamePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
        revalidate();
        repaint();
        setVisible(true);
    }

    /**
     * Carica le immagini necessarie per il gioco.
     */
    private void loadImages() {
        images = new HashMap<>();
        String[] imageNames = { "wall", "smallDot", "bigDot", "ghost_orange", "ghost_blue", "ghost_pink", "ghost_red",
                "ghost_scared", "heart", "pacman", "left", "right", "up", "down" };
        String[] imagePaths = {
                "/main/java/view/images/wall_x.png",
                "/main/java/view/images/smallDot.png",
                "/main/java/view/images/bigDot.png",
                "/main/java/view/images/ghost_orange.gif",
                "/main/java/view/images/ghost_blue.gif",
                "/main/java/view/images/ghost_pink.gif",
                "/main/java/view/images/ghost_red.gif",
                "/main/java/view/images/ghost_scared.gif",
                "/main/java/view/images/heart.png",
                "/main/java/view/images/pacman.png",
                "/main/java/view/images/left.gif",
                "/main/java/view/images/right.gif",
                "/main/java/view/images/up.gif",
                "/main/java/view/images/down.gif"
        };

        for (int i = 0; i < imageNames.length; i++) {
            try {
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePaths[i])));
                images.put(imageNames[i], icon);
            } catch (NullPointerException e) {
                System.out.println("Errore nel caricamento dell'immagine: " + imagePaths[i] + " - " + e.getMessage());
            }
        }
    }

    /**
     * Gestisce l'input della tastiera e muove Pacman nella direzione
     * corrispondente.
     *
     * @param e l'evento di pressione del tasto
     */
    private void handleKeyPress(KeyEvent e) {
        Direction direction = switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> Direction.UP;
            case KeyEvent.VK_DOWN -> Direction.DOWN;
            case KeyEvent.VK_RIGHT -> Direction.RIGHT;
            case KeyEvent.VK_LEFT -> Direction.LEFT;
            default -> null;
        };

        if (direction != null) {
            controller.getModel().getPacman().setDirection(direction);
            try {
                controller.movePacman(direction);
                controller.moveGhosts();
                gamePanel.repaint();
                winOrGameOver();
            } catch (IllegalEntityMovementException ex) {
                System.out.println("Pacman ha colpito un muro: " + switchDirezione(direction));
            }
        }
    }

    /**
     * Verifica se il gioco è finito o se è stato vinto e mostra la schermata
     * appropriata.
     */
    private void winOrGameOver() {
        if (controller.getModel().isGameOver()) {
            showGameOverScreen();
        } else if (controller.getModel().isWin()) {
            showWinScreen(controller.getModel().getScore());
        }
    }

    /**
     * Ripristina le statistiche e mostra la finestra di gioco.
     *
     * @param win true se il gioco è stato vinto, false altrimenti
     */
    public void resetStats(boolean win) {
        controller.getModel().resetGame(win);
        showGameWindow();
    }

    /**
     * Mostra la schermata di vittoria.
     *
     * @param score il punteggio del gioco
     */
    public void showWinScreen(int score) {
        new GameWonScreen(controller, score);
        dispose(); // Chiude la finestra di gioco
    }

    /**
     * Mostra la schermata di Game Over.
     */
    private void showGameOverScreen() {
        new GameOverScreen(controller, controller.getModel().getScore()); // Mostra la schermata di Game Over
        dispose(); // Chiude la finestra di gioco
    }

    /**
     * Converte una direzione in una stringa di descrizione in italiano.
     *
     * @param direction la direzione da convertire
     * @return la stringa di descrizione della direzione
     */
    private String switchDirezione(Direction direction) {
        return switch (direction) {
            case UP -> "sopra";
            case DOWN -> "sotto";
            case RIGHT -> "a destra";
            case LEFT -> "a sinistra";
        };
    }

    /**
     * Mostra il menu principale.
     */
    public void showMainMenu() {
        if (mainMenu != null) {
            remove(mainMenu);
        }
        mainMenu = new MainMenu(controller);
        mainMenu.setupMenu();
        revalidate();
        repaint();
    }

    /**
     * Aggiorna il pannello delle informazioni con le statistiche correnti.
     */
    public void updateInfoPanel() {
        if (infoPanel != null) {
            infoPanel.setLives(controller.getModel().getLives());
            infoPanel.setScore(controller.getModel().getScore());
        }
    }

    /**
     * Aggiorna lo stato della modalità Supermode nel pannello delle informazioni.
     *
     * @param movesRemaining il numero di mosse rimanenti nella modalità Supermode
     */
    public void updateSuperModeStatus(int movesRemaining) {
        if (infoPanel != null) {
            infoPanel.setSuperModeStatus(movesRemaining);
            infoPanel.repaint(); // Assicura che venga ridisegnato
        }
    }
}