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
import javax.swing.*;

import main.java.controller.Controller;
import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.Exceptions.IllegalEntityMovementException;
import main.java.view.GUI.GameOverScreen;
import main.java.view.GUI.GamePanel;
import main.java.view.GUI.GameWonScreen;
import main.java.view.GUI.InfoPanel;
import main.java.view.GUI.MainMenu;

public class View extends JFrame {
    private Controller controller;
    private final Model model;
    private Map<String, ImageIcon> images;
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private MainMenu mainMenu;

    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void initializeView() {
        loadImages();
        setupWindow();
        setupStatusBar();
        setupKeyListener();
    }

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
        showMainMenu();
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

    public void showGameWindow() {
        if (gamePanel != null) {
            remove(gamePanel);
        }
        if (infoPanel != null) {
            remove(infoPanel);
        }
        gamePanel = new GamePanel(model, model.getPacman(), model.getGhosts(), images);
        infoPanel = new InfoPanel(model);
        add(gamePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
        revalidate();
        repaint();
        setVisible(true);
    }

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

    private void handleKeyPress(KeyEvent e) {
        Direction direction = switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> Direction.UP;
            case KeyEvent.VK_DOWN -> Direction.DOWN;
            case KeyEvent.VK_RIGHT -> Direction.RIGHT;
            case KeyEvent.VK_LEFT -> Direction.LEFT;
            default -> null;
        };

        if (direction != null) {
            model.getPacman().setDirection(direction);
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

    private void winOrGameOver() {
        if (model.isGameOver()) {
            showGameOverScreen();
        } else if (model.isWin()) {
            showWinScreen(model.getScore());
        }
    }

    public void resetStats(boolean win) {
        model.resetGame(win);
        showGameWindow();
    }

    public void showWinScreen(int score) {
        new GameWonScreen(controller, score);
        dispose();
    }

    private void showGameOverScreen() {
        new GameOverScreen(controller, model.getScore()); // Mostra la nuova schermata Game Over
        dispose(); // Chiude la finestra attuale del gioco
    }

    private String switchDirezione(Direction direction) {
        return switch (direction) {
            case UP -> "sopra";
            case DOWN -> "sotto";
            case RIGHT -> "a destra";
            case LEFT -> "a sinistra";
        };
    }

    public void showMainMenu() {
        if (mainMenu != null) {
            remove(mainMenu);
        }
        mainMenu = new MainMenu(controller);
        mainMenu.setupMenu();
        revalidate();
        repaint();
    }

    public void updateInfoPanel() {
        if (infoPanel != null) {
            infoPanel.setLives(model.getLives());
            infoPanel.setScore(model.getScore());
        }
    }

    public void updateSuperModeStatus(int movesRemaining) {
        if (infoPanel != null) {
            infoPanel.setSuperModeStatus(movesRemaining);
            infoPanel.repaint(); // Assicura che venga ridisegnato
        }
    }
}