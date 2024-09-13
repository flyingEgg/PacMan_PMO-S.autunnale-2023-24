package main.java.model.Entities;

import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.ImageIcon;

import main.java.model.Movement.Direction;
import main.java.model.Movement.Position;
import main.java.model.Grid.Grid;

/**
 * Rappresenta il personaggio di Pacman nel gioco.
 */
public class Pacman extends AbstractEntity {
    private boolean superMode;

    /**
     * Crea un'istanza di Pacman con la posizione iniziale specificata.
     *
     * @param position La posizione iniziale di Pacman.
     */
    public Pacman(Position position) {
        super(position);
        this.direction = Direction.RIGHT; // Imposta la direzione iniziale predefinita
        this.superMode = false; // Imposta lo stato iniziale della modalità Supermode
    }

    /**
     * Restituisce la direzione corrente di Pacman.
     *
     * @return La direzione corrente di Pacman.
     */
    @Override
    public Direction getDirection() {
        return direction;
    }

    /**
     * Imposta la direzione di Pacman.
     *
     * @param d La nuova direzione da impostare.
     */
    @Override
    public void setDirection(Direction d) {
        this.direction = d;
    }

    /**
     * Restituisce true se Pacman è in modalità Supermode.
     *
     * @return true se Pacman è in modalità Supermode, false altrimenti.
     */
    public boolean isSuperMode() {
        return superMode;
    }

    /**
     * Imposta lo stato della modalità Supermode per Pacman.
     *
     * @param superMode true se Pacman è in modalità Supermode, false altrimenti.
     */
    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }

    /**
     * Disegna Pacman sulla griglia utilizzando l'immagine corrispondente alla
     * direzione.
     *
     * @param g2d    Il contesto grafico per disegnare.
     * @param images La mappa delle immagini utilizzate per disegnare Pacman.
     */
    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        // Seleziona l'immagine in base alla direzione corrente
        ImageIcon pacmanImage = switch (direction) {
            case RIGHT -> images.get("right");
            case LEFT -> images.get("left");
            case UP -> images.get("up");
            case DOWN -> images.get("down");
        };

        // Verifica se l'immagine è disponibile e disegna Pacman
        if (pacmanImage != null) {
            g2d.drawImage(pacmanImage.getImage(), position.getX() * Grid.CELL_SIZE, position.getY() * Grid.CELL_SIZE,
                    Grid.CELL_SIZE, Grid.CELL_SIZE, null);
        } else {
            System.err.println("Pacman image not found for direction: " + direction);
        }
    }
}