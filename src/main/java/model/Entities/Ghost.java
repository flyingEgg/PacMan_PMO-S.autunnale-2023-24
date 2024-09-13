package main.java.model.Entities;

import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.ImageIcon;

import main.java.model.Strategies.GhostMovementStrategy;
import main.java.model.Movement.Direction;
import main.java.model.Movement.Position;
import main.java.model.Grid.Grid;

/**
 * Rappresenta un fantasma nel gioco.
 * Gestisce la posizione, la direzione, il colore, e le strategie di movimento
 * del fantasma.
 */
public class Ghost extends AbstractEntity {
    private final GhostColor color;
    private GhostMovementStrategy movementStrategy;
    private boolean scared;
    private boolean inSpawn;

    /**
     * Crea un nuovo fantasma con la posizione e il colore specificati.
     * 
     * @param position La posizione iniziale del fantasma.
     * @param color    Il colore del fantasma.
     */
    public Ghost(Position position, GhostColor color) {
        super(position);
        this.color = color;
        this.scared = false;
        this.direction = Direction.UP; // Direzione predefinita
        this.inSpawn = true;
    }

    /**
     * Disegna il fantasma sul pannello di gioco.
     * 
     * @param g2d    L'oggetto Graphics2D usato per disegnare.
     * @param images Mappa delle immagini usate per il rendering degli oggetti.
     */
    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        ImageIcon ghostImage = scared ? images.get("ghost_scared") : getGhostImage(images);

        if (ghostImage != null) {
            g2d.drawImage(ghostImage.getImage(), getPosition().getX() * Grid.CELL_SIZE,
                    getPosition().getY() * Grid.CELL_SIZE, null);
        }
    }

    /**
     * Restituisce l'immagine del fantasma in base al colore.
     * 
     * @param images Mappa delle immagini usate per il rendering degli oggetti.
     * @return L'immagine del fantasma in base al colore.
     */
    private ImageIcon getGhostImage(Map<String, ImageIcon> images) {
        return switch (this.color) {
            case BLUE -> images.get("ghost_blue");
            case ORANGE -> images.get("ghost_orange");
            case PINK -> images.get("ghost_pink");
            case RED -> images.get("ghost_red");
        };
    }

    /**
     * Muove il fantasma secondo la strategia di movimento.
     */
    public void moveByStrategy() {
        if (movementStrategy != null) {
            Direction nextDirection = movementStrategy.determineNextDirection();
            setDirection(nextDirection); // Aggiorna la direzione
            movementStrategy.movementService(); // Esegui il movimento
        } else {
            System.out.println("Movement strategy is not set for ghost.");
        }
    }

    /**
     * Imposta lo stato di paura del fantasma.
     * 
     * @param scared True se il fantasma è spaventato, altrimenti false.
     */
    public void setScared(boolean scared) {
        this.scared = scared;
    }

    /**
     * Restituisce lo stato di paura del fantasma.
     * 
     * @return True se il fantasma è spaventato, altrimenti false.
     */
    public boolean isScared() {
        return scared;
    }

    /**
     * Restituisce il colore del fantasma.
     * 
     * @return Il colore del fantasma.
     */
    public GhostColor getColor() {
        return color;
    }

    /**
     * Imposta la strategia di movimento del fantasma.
     * 
     * @param strategy La strategia di movimento da impostare.
     */
    public void setMovementStrategy(GhostMovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    /**
     * Restituisce la strategia di movimento del fantasma.
     * 
     * @return La strategia di movimento del fantasma.
     */
    public GhostMovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    /**
     * Imposta la direzione corrente del fantasma.
     * 
     * @param d La nuova direzione del fantasma.
     */
    @Override
    public void setDirection(Direction d) {
        this.direction = d;
    }

    /**
     * Restituisce la direzione corrente del fantasma.
     * 
     * @return La direzione corrente del fantasma.
     */
    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Restituisce se il fantasma si trova nella sua area di spawn.
     * 
     * @return True se il fantasma è in spawn, altrimenti false.
     */
    public boolean isInSpawn() {
        return inSpawn;
    }

    /**
     * Imposta se il fantasma è in spawn.
     * 
     * @param inSpawn True se il fantasma deve essere in spawn, altrimenti false.
     */
    public void setInSpawn(boolean inSpawn) {
        this.inSpawn = inSpawn;
    }
}