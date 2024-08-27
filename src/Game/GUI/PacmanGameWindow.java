package Game.GUI;

import Entities.Pacman;
import Game.Game;
import Game.PacmanGrid;
import Game.Strategies.Direction;
import Game.Strategies.PacmanMovementStrategy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PacmanGameWindow extends JFrame {
    private final PacmanGrid grid;
    private final Pacman pacman;
    private final PacmanMovementStrategy pacmanMovementStrategy;
    private final Game game;

    private Map<String, BufferedImage> images;
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private JMenuBar menuBar;
    private JPanel statusBar;

    public PacmanGameWindow() {
        this.game = new Game();
        this.grid = new PacmanGrid();
        this.pacman = new Pacman(game.getPacman().getX(), game.getPacman().getY());
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, game);

        loadImages();
        setupWindow();
        setupMenu();
        setupStatusBar();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    private void setupWindow() {
        setTitle("Pacman");
        setSize(800, 600); // Dimensioni generose per includere sia il gioco che il pannello info
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra sullo schermo
        setLayout(new BorderLayout());

        // Pannello di gioco
        gamePanel = new GamePanel(grid, pacman, images);
        add(gamePanel, BorderLayout.CENTER);

        // Pannello informativo
        infoPanel = new InfoPanel();
        add(infoPanel, BorderLayout.EAST);

        // Applica un tema moderno
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    private void setupMenu() {
        menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem startItem = new JMenuItem("Start");
        JMenuItem pauseItem = new JMenuItem("Pause");
        JMenuItem resetItem = new JMenuItem("Reset");

        startItem.addActionListener(e -> startGame());
        pauseItem.addActionListener(e -> pauseGame());
        resetItem.addActionListener(e -> resetGame());

        gameMenu.add(startItem);
        gameMenu.add(pauseItem);
        gameMenu.add(resetItem);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void setupStatusBar() {
        statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Welcome to Pacman!");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void startGame() {
        // Logica per avviare il gioco
        System.out.println("Gioco avviato!");
    }

    private void pauseGame() {
        // Logica per mettere in pausa il gioco
        System.out.println("Gioco in pausa.");
    }

    private void resetGame() {
        // Logica per resettare il gioco
        System.out.println("Gioco resettato.");
    }

    private void loadImages() {
        images = new HashMap<>();
        try {
            images.put("down",
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/down.gif"))));
            images.put("ghost",
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/ghost.gif"))));
            images.put("heart",
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/heart.png"))));
            images.put("left",
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/left.gif"))));
            images.put("pacman",
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/pacman.png"))));
            images.put("right",
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/right.gif"))));
            images.put("up", ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/up.gif"))));
        } catch (IOException e) {
            System.out.println("Errore nel caricamento delle risorse: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Immagine non trovata, controlla il percorso delle risorse.");
        }
    }

    private void handleKeyPress(KeyEvent e) {
        Direction direction = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> direction = Direction.UP;
            case KeyEvent.VK_DOWN -> direction = Direction.DOWN;
            case KeyEvent.VK_LEFT -> direction = Direction.LEFT;
            case KeyEvent.VK_RIGHT -> direction = Direction.RIGHT;
        }
        if (direction != null) {
            pacman.getMyMovementStrat().move(direction);
            gamePanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PacmanGameWindow::new);
    }
}