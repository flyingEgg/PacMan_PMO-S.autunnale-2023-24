package Game;

import API.MapComponent;
import Entities.Pacman;
import Game.Game;
import Game.PacmanGrid;
import Game.Position;
import Game.Strategies.Direction;
import Game.Strategies.PacmanMovementStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class PacmanGameWindow extends JFrame {
    private PacmanGrid grid;
    private Pacman pacman;
    private PacmanMovementStrategy pacmanMovementStrategy;
    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;

    public PacmanGameWindow() {
        grid = new PacmanGrid();
        pacman = new Pacman(PacmanGrid.PACMAN_START_POSITION.getX(), PacmanGrid.PACMAN_START_POSITION.getY());
        Game game = new Game(grid); // Assumendo che tu abbia una classe Game
        pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, game);

        setupWindow();
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
        setSize(800, 600); // Dimensioni della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeGraphics() {
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics2D = bufferedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        drawGraphics(); // Disegna inizialmente
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
            pacmanMovementStrategy.move(direction);
            drawGraphics(); // Ridisegna l'immagine del buffer
            repaint();
        }
    }

    private void drawGraphics() {
        graphics2D.setComposite(AlphaComposite.SrcOver);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        drawGrid();
        drawPacman();
    }

    private void drawGrid() {
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getColumns(); j++) {
                Position pos = new Position(j, i);
                Optional<MapComponent> component = grid.getComponentByPosition(pos);
                component.ifPresent(mapComponent -> mapComponent.draw(graphics2D));
            }
        }
    }

    private void drawPacman() {
        // Disegna Pacman alla sua posizione attuale
        pacman.draw(graphics2D); // Assumendo che Pacman abbia un metodo draw che accetta Graphics2D come
                                 // argomento
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
