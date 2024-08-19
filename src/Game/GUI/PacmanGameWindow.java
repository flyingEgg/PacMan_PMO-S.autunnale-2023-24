package Game.GUI;

import API.MapComponent;
import Entities.Pacman;
import Entities.Ghost.Ghost;
import Game.Game;
import Game.PacmanGrid;
import Game.Position;
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
import java.util.Optional;

public class PacmanGameWindow extends JFrame {
    private PacmanGrid grid;
    private Pacman pacman;
    private PacmanMovementStrategy pacmanMovementStrategy;
    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;
    private final Game game;

    private Map<String, BufferedImage> images;

    public PacmanGameWindow() {
        this.grid = new PacmanGrid();
        this.game = new Game();
        this.pacman = new Pacman(game.getPacman().getX(), game.getPacman().getY());
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, game);

        setupWindow();
        loadImages();
        initializeGraphics();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    private void setupWindow() {
        setTitle("Pacman");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadImages() {
        images = new HashMap<>();
        try {
            images.put("down", ImageIO.read(Objects.requireNonNull(getClass().getResource("down.gif"))));
            images.put("ghost", ImageIO.read(Objects.requireNonNull(getClass().getResource("ghost.gif"))));
            images.put("heart", ImageIO.read(Objects.requireNonNull(getClass().getResource("heart.png"))));
            images.put("left", ImageIO.read(Objects.requireNonNull(getClass().getResource("left.gif"))));
            images.put("pacman", ImageIO.read(Objects.requireNonNull(getClass().getResource("pacman.png"))));
            images.put("right", ImageIO.read(Objects.requireNonNull(getClass().getResource("right.gif"))));
            images.put("up", ImageIO.read(Objects.requireNonNull(getClass().getResource("up.gif"))));

            // tocca mettere le immagini per la frutta

        } catch (IOException e) {
            System.out.println(e.getMessage() + "Errore nel caricamento delle risorse");
        }
    }

    private void initializeGraphics() {
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics2D = bufferedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        drawGraphics();
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
            drawGraphics();
            repaint();
        }
    }

    private void drawGraphics() {
        graphics2D.setComposite(AlphaComposite.SrcOver);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        grid.drawGrid(graphics2D, new HashMap<>());
        drawPacman();
    }

    private void drawPacman() {
        pacman.draw(graphics2D, images);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bufferedImage, 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PacmanGameWindow::new);
    }
}
