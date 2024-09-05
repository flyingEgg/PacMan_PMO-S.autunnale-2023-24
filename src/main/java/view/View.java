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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.controller.Controller;
import main.java.controller.Strategies.PacmanMovementStrategy;
import main.java.model.Game;
import main.java.model.Grid;
import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.Entities.Pacman;
import main.java.model.Exceptions.IllegalEntityMovementException;

public class View extends JFrame {
    private Controller controller;
    private Grid grid;
    private Pacman pacman;
    private PacmanMovementStrategy pacmanMovementStrategy;
    private Game game;

    private Map<String, ImageIcon> images;
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private MainMenu mainMenu;

    public View(Controller controller) {
        this.controller = controller;
        this.game = new Game();
        this.grid = new Grid();
        this.pacman = game.getPacman();
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, game);

        loadImages();
        setupWindow();
        setupStatusBar();
        setupKeyListener();
    }

    private void setupWindow() {
        setTitle("Pacman");
        setSize(800, 600); // 516, 460?
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Start with the main menu
        mainMenu = new MainMenu(controller);
        add(mainMenu, BorderLayout.CENTER);

        setVisible(true);
    }

    private void setupStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Welcome to Pacman!");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    public void showGameWindow(Model model) {
        if (gamePanel != null) {
            remove(gamePanel);
        }
        if (infoPanel != null) {
            remove(infoPanel);
        }
        gamePanel = new GamePanel(model.getGame().getGrid(), model.getGame(), model.getPacman(), model.getGhosts(),
                images);
        infoPanel = new InfoPanel(model.getGame());
        add(gamePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    private void loadImages() {
        images = new HashMap<>();
        String[] imageNames = { "down", "ghost_orange", "ghost_blue", "ghost_pink", "ghost_red", "heart", "left",
                "pacman", "right", "up" };
        String[] imagePaths = {
                "/main/java/view/images/down.gif", "/main/java/view/images/ghost_orange.gif",
                "/main/java/view/images/ghost_blue.gif", "/main/java/view/images/ghost_pink.gif",
                "/main/java/view/images/ghost_red.gif", "/main/java/view/images/heart.png",
                "/main/java/view/images/left.gif", "/main/java/view/images/pacman.png",
                "/main/java/view/images/right.gif", "/main/java/view/images/up.gif"
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
            case KeyEvent.VK_UP -> direction = Direction.UP;
            case KeyEvent.VK_DOWN -> direction = Direction.DOWN;
            case KeyEvent.VK_RIGHT -> direction = Direction.RIGHT;
            case KeyEvent.VK_LEFT -> direction = Direction.LEFT;
        }
        this.pacman.setDirection(direction);

        if (direction != null) {
            try {
                pacmanMovementStrategy.move(direction);
                gamePanel.repaint();
                checkForGameOver();
            } catch (IllegalEntityMovementException iemE) {
                System.out.println("Pacman ha colpito un muro: " + switchDirezione(direction));
            }
        }
    }

    private void checkForGameOver() {
        if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game Over! Your score: " + game.getScore());
            resetGame();
            gamePanel.repaint();
        }
    }

    public void resetGame() {
        this.game = new Game();
        this.grid = new Grid();
        this.pacman = game.getPacman();
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, game);
        showGameWindow(controller.getModel());
    }

    private String switchDirezione(Direction d) {
        return switch (d) {
            case UP -> "sopra";
            case DOWN -> "sotto";
            case RIGHT -> "a destra";
            case LEFT -> "a sinistra";
        };
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void showMainMenu() {
        if (mainMenu != null) {
            remove(mainMenu);
        }
        mainMenu = new MainMenu(controller);
        add(mainMenu, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void updateInfoPanel() {
        if (infoPanel != null) {
            infoPanel.setLives(game.getLives());
            infoPanel.setScore(game.getScore());
        }
    }
}
