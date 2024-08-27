package Game.GUI;

import java.util.Map;

import javax.swing.JPanel;

import Entities.Pacman;
import Game.PacmanGrid;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private final PacmanGrid grid;
    private final Pacman pacman;
    private final Map<String, BufferedImage> images;

    public GamePanel(PacmanGrid grid, Pacman pacman, Map<String, BufferedImage> images) {
        this.grid = grid;
        this.pacman = pacman;
        this.images = images;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grid != null && images != null) {
            grid.drawGrid((Graphics2D) g, images);
            pacman.draw((Graphics2D) g, images);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(grid.getColumns() * PacmanGrid.CELL_SIZE,
                grid.getRows() * PacmanGrid.CELL_SIZE);
    }
}
