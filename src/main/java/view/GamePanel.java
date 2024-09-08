package main.java.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.java.model.Grid;
import main.java.model.Model;
import main.java.model.API.MapComponent;
import main.java.model.API.Position;
import main.java.model.Composite.BigDot;
import main.java.model.Composite.SmallDot;
import main.java.model.Composite.Wall;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.Pacman;

public class GamePanel extends JPanel {
    private Pacman pacman;
    private List<Ghost> ghosts;
    private final Map<String, ImageIcon> images;
    private final Model model; // Se necessario per accedere alla grid e altri dati

    public GamePanel(Model model, Pacman pacman, List<Ghost> ghosts, Map<String, ImageIcon> images) {
        this.model = model;
        this.pacman = pacman;
        this.ghosts = ghosts;
        this.images = images;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Disegna la griglia e i componenti
        drawGrid(g2d);
        pacman.draw(g2d, images);
        ghosts.stream().forEach(ghost -> ghost.draw(g2d, images));
    }

    private void drawGrid(Graphics2D g2d) {
        // Disegna tutte le componenti della griglia
        for (int i = 0; i < model.getGrid().getRows(); i++) {
            for (int j = 0; j < model.getGrid().getColumns(); j++) {
                Position pos = new Position(j, i);
                Optional<MapComponent> component = model.getGrid().getComponentByPosition(pos);

                if (component.isPresent()) {
                    ImageIcon icon = null,
                            uninitialisedIcon = new ImageIcon();        // Icona non inizializzata per bypassare if-else statement
                    boolean shouldBypassCondition =  component.get() instanceof Pacman ||
                            component.get() instanceof Ghost;

                    if (component.get() instanceof Wall) {
                        icon = images.get("wall");
                    } else if (component.get() instanceof SmallDot) {
                        icon = images.get("smallDot");
                    } else if (component.get() instanceof BigDot) {
                        icon = images.get("bigDot");
                    } else if (shouldBypassCondition) {
                        icon = uninitialisedIcon;
                    }

                    if (icon != null) {
                        if(!icon.equals(uninitialisedIcon)){
                            g2d.drawImage(icon.getImage(), j * Grid.CELL_SIZE, i * Grid.CELL_SIZE, null);
                        }
                    } else {
                        System.out.println("Immagine non trovata per la chiave: " + getImageKey(component.get()));
                    }
                }
            }
        }
    }

    private String getImageKey(MapComponent component) {
        if (component instanceof Wall) {
            return "wall";
        } else if (component instanceof SmallDot) {
            return "smallDot";
        } else if (component instanceof BigDot) {
            return "bigDot";
        } else if (component instanceof Ghost) {
            Ghost ghost = (Ghost) component;
            return "ghost" + ghost.getColor().name().toLowerCase(); // Adatta a seconda dei nomi delle immagini
        }
        System.out.println("Unknown component: " + component.getClass().getSimpleName());
        return "unknown";
    }
}