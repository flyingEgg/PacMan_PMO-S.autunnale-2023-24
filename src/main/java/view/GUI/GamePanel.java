package main.java.view.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.java.model.Model;
import main.java.model.API.MapComponent;
import main.java.model.API.Position;
import main.java.model.Composite.BigDot;
import main.java.model.Composite.SmallDot;
import main.java.model.Composite.Wall;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.Pacman;
import main.java.model.Grid.Grid;

/**
 * Pannello di gioco che gestisce la visualizzazione di Pacman, dei fantasmi e
 * della griglia.
 */
public class GamePanel extends JPanel {
    private Pacman pacman;
    private List<Ghost> ghosts;
    private final Map<String, ImageIcon> images;
    private final Model model;
    private final Set<String> loggedImageKeys = new HashSet<>();
    private final Set<String> loggedComponentKeys = new HashSet<>();

    /**
     * Costruisce un nuovo pannello di gioco.
     *
     * @param model  Il modello del gioco
     * @param pacman L'istanza di Pacman
     * @param ghosts La lista dei fantasmi
     * @param images La mappa delle immagini
     */
    public GamePanel(Model model, Pacman pacman, List<Ghost> ghosts, Map<String, ImageIcon> images) {
        setDoubleBuffered(true);
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

        // Abilita l'antialiasing per un rendering più fluido
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Disegna la griglia e i componenti
        drawGrid(g2d);
        pacman.draw(g2d, images);
        ghosts.forEach(ghost -> ghost.draw(g2d, images));
    }

    /**
     * Disegna la griglia e i componenti sulla griglia di gioco.
     *
     * @param g2d L'oggetto Graphics2D per il rendering
     */
    private void drawGrid(Graphics2D g2d) {
        Grid grid = model.getGrid();

        // Disegna tutte le componenti della griglia
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getColumns(); j++) {
                Position pos = new Position(j, i);
                Optional<MapComponent> component = grid.getComponentByPosition(pos);

                if (component.isPresent()) {
                    ImageIcon icon = getImageForComponent(component.get());

                    if (icon != null) {
                        g2d.drawImage(icon.getImage(), j * Grid.CELL_SIZE, i * Grid.CELL_SIZE, Grid.CELL_SIZE,
                                Grid.CELL_SIZE, this);
                    } else {
                        String key = getImageKey(component.get());
                        if (!loggedImageKeys.contains(key)) {
                            System.out.println("Immagine non trovata per la chiave: " + key);
                            loggedImageKeys.add(key);
                        }
                    }
                }
            }
        }
    }

    /**
     * Ottiene l'icona associata al componente.
     *
     * @param component Il componente della mappa
     * @return L'icona associata al componente
     */
    private ImageIcon getImageForComponent(MapComponent component) {
        String key = getImageKey(component);
        return images.get(key);
    }

    /**
     * Ottiene la chiave dell'immagine associata al componente.
     *
     * @param component Il componente della mappa
     * @return La chiave dell'immagine
     */
    private String getImageKey(MapComponent component) {
        if (component instanceof Wall) {
            return "wall";
        } else if (component instanceof SmallDot) {
            return "smallDot";
        } else if (component instanceof BigDot) {
            return "bigDot";
        } else if (component instanceof Ghost) {
            Ghost ghost = (Ghost) component;
            return "ghost" + ghost.getColor().name().toLowerCase();
        }
        String className = component.getClass().getSimpleName();
        if (!loggedComponentKeys.contains(className)) {
            System.out.println("Componente sconosciuto: " + className);
            loggedComponentKeys.add(className);
        }
        return "unknown";
    }
}