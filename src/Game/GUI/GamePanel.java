package Game.GUI;

import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import Entities.Pacman;
import Entities.Ghost.Ghost;
import Game.Game;
import Game.Grid;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private Grid grid;
    private Game game;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private final Map<String, BufferedImage> images;

    public GamePanel(Grid grid, Game game, Pacman pacman, List<Ghost> ghosts, Map<String, BufferedImage> images) {
        this.grid = grid;
        this.game = game;
        this.pacman = pacman;
        this.ghosts = ghosts;
        this.images = images;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (grid != null && images != null) {
            grid.drawGrid(g2d, images); // Metodo per disegnare la griglia
            pacman.draw(g2d, images); // Metodo per disegnare Pacman
            for (Ghost ghost : ghosts) {
                ghost.draw(g2d, images); // Metodo per disegnare ogni fantasma
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(grid.getColumns() * Grid.CELL_SIZE,
                grid.getRows() * Grid.CELL_SIZE);
    }

    public void updateGame(Grid grid, Game game, Pacman pacman, List<Ghost> ghosts) {
        this.grid = grid;
        this.game = game;
        this.pacman = pacman;
        this.ghosts = ghosts;
        repaint(); // Ridisegna il pannello con le nuove informazioni
    }
}
