package Game.GUI;

import Entities.Pacman;
import Exceptions.IllegalEntityMovementException;
import Game.Game;
import Game.Grid;
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
    private Grid grid;
    private final Pacman pacman;
    private final PacmanMovementStrategy pacmanMovementStrategy;
    private Game game;

    private Map<String, ImageIcon> images;
    private GamePanel gamePanel;
    private InfoPanel infoPanel;

    public PacmanGameWindow() {
        this.game = new Game();
        this.grid = new Grid();
        this.pacman = game.getPacman();
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
        setSize(516, 460); // Dimensioni generose per includere sia il gioco che il pannello info
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra sullo schermo
        setLayout(new BorderLayout());

        if (images == null || images.isEmpty()) {
            System.out.println("Immagini non caricate correttamente.");
            return;
        }

        gamePanel = new GamePanel(game.getGrid(), game, game.getPacman(), game.getGhosts(), images);
        add(gamePanel, BorderLayout.CENTER);

        infoPanel = new InfoPanel(this.game);
        add(infoPanel, BorderLayout.EAST);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem startItem = new JMenuItem("Start");
        JMenuItem pauseItem = new JMenuItem("Pause");
        JMenuItem resetItem = new JMenuItem("Reset");

        startItem.addActionListener(e -> start());
        pauseItem.addActionListener(e -> pause());
        resetItem.addActionListener(e -> reset());

        gameMenu.add(startItem);
        gameMenu.add(pauseItem);
        gameMenu.add(resetItem);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void setupStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Welcome to Pacman!");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void start() {
        System.out.println("Gioco avviato!");
    }

    private void pause() {
        System.out.println("Gioco in pausa.");
    }

    private void reset() {
        this.game = new Game();
        this.grid = new Grid();
        this.infoPanel = new InfoPanel(this.game);
        System.out.println("Gioco resettato.");
    }

    private void loadImages() {
        images = new HashMap<>();
        String[] imageNames = {"down", "ghost_orange", "ghost_blue", "ghost_pink", "ghost_red", "heart", "left",
                "pacman", "right", "up"};
        String[] imagePaths = {
                "/Game/GUI/images/down.gif", "/Game/GUI/images/ghost_orange.gif",
                "/Game/GUI/images/ghost_blue.gif", "/Game/GUI/images/ghost_pink.gif",
                "/Game/GUI/images/ghost_red.gif", "/Game/GUI/images/heart.png",
                "/Game/GUI/images/left.gif", "/Game/GUI/images/pacman.png",
                "/Game/GUI/images/right.gif", "/Game/GUI/images/up.gif"
        };

        for (int i = 0; i < imageNames.length; i++) {
            try {
                BufferedImage image = ImageIO
                        .read(Objects.requireNonNull(getClass().getResourceAsStream(imagePaths[i])));

                ImageIcon icon = new ImageIcon(image);
                images.put(imageNames[i], icon);
            } catch (IOException | NullPointerException e) {
                System.out.println("Errore nel caricamento dell'immagine: " + imagePaths[i] + " - " + e.getMessage());
            }
        }
    }

    private void handleKeyPress(KeyEvent e) {
        Direction direction = null;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP    -> direction = Direction.UP;
            case KeyEvent.VK_DOWN  -> direction = Direction.DOWN;
            case KeyEvent.VK_RIGHT -> direction = Direction.RIGHT;
            case KeyEvent.VK_LEFT  -> direction = Direction.LEFT;
        }
        this.pacman.setDirection(direction);

        if (direction != null) {
            try {
                pacmanMovementStrategy.move(direction);
                gamePanel.repaint();
                checkForGameOver();
            } catch (IllegalEntityMovementException iemE) {
                System.out.println("Pacman hitta il muro " + switchDirezione(direction));
            }

        }
    }

    private void checkForGameOver() {
        if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game Over! Your score: " + game.getScore());

            this.reset();
            gamePanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PacmanGameWindow::new);
    }

    private String switchDirezione(Direction d) {
        return switch (d) {
            case UP -> "sopra";
            case DOWN -> "sotto";
            case RIGHT -> "a destra";
            case LEFT -> "a sinistra";
        };
    }

}
