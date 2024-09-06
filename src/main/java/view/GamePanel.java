package main.java.view;

import java.util.List;
import java.util.Map;

import javax.swing.*;

import main.java.model.Model;
import main.java.model.Grid;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.Pacman;

import java.awt.*;

public class GamePanel extends JPanel {
    private Grid grid;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private final Map<String, ImageIcon> images;

    public GamePanel(Grid grid, Model model, Pacman pacman, List<Ghost> ghosts, Map<String, ImageIcon> images) {
        this.grid = grid;
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
}
