package main.java.model.Entities;

import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.ImageIcon;

import main.java.model.Movement.Direction;
import main.java.API.MapComponent;
import main.java.model.Movement.Position;

/**
 * Classe astratta che rappresenta una entità nel gioco.
 * Fornisce metodi comuni per gestire la posizione e la direzione dell'entità.
 */
public abstract class AbstractEntity implements MapComponent {
    protected Position position;
    protected Direction direction;

    /**
     * Crea una nuova entità con la posizione specificata.
     * 
     * @param position La posizione iniziale dell'entità.
     */
    public AbstractEntity(Position position) {
        this.position = position;
    }

    /**
     * Restituisce la coordinata X dell'entità.
     * 
     * @return La coordinata X dell'entità.
     */
    public int getX() {
        return position.getX();
    }

    /**
     * Imposta la coordinata X dell'entità.
     * 
     * @param x La nuova coordinata X dell'entità.
     */
    public void setX(int x) {
        position = new Position(x, position.getY());
    }

    /**
     * Restituisce la coordinata Y dell'entità.
     * 
     * @return La coordinata Y dell'entità.
     */
    public int getY() {
        return position.getY();
    }

    /**
     * Imposta la coordinata Y dell'entità.
     * 
     * @param y La nuova coordinata Y dell'entità.
     */
    public void setY(int y) {
        position = new Position(position.getX(), y);
    }

    /**
     * Disegna l'entità sul pannello di gioco.
     * 
     * @param g2d    L'oggetto Graphics2D usato per disegnare.
     * @param images Mappa delle immagini usate per il rendering degli oggetti.
     */
    @Override
    public abstract void draw(Graphics2D g2d, Map<String, ImageIcon> images);

    /**
     * Restituisce la posizione dell'entità.
     * 
     * @return La posizione dell'entità.
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Restituisce la direzione corrente dell'entità.
     * 
     * @return La direzione dell'entità.
     */
    public abstract Direction getDirection();

    /**
     * Imposta la direzione dell'entità.
     * 
     * @param d La nuova direzione dell'entità.
     */
    public abstract void setDirection(Direction d);

    /**
     * Imposta la posizione dell'entità.
     * 
     * @param position La nuova posizione dell'entità.
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}