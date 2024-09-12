package main.java.model.Composite;

import main.java.model.API.MapComponent;
import main.java.model.API.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Rappresenta un muro nel gioco che ostacola il movimento degli altri oggetti.
 * Il muro è disegnato come un rettangolo blu.
 */
public class Wall implements MapComponent {
    private final Position position;

    /**
     * Crea un nuovo muro con la posizione specificata.
     * 
     * @param position La posizione del muro.
     */
    public Wall(Position position) {
        this.position = position;
    }

    /**
     * Disegna il muro sul pannello di gioco.
     * 
     * @param g2d    L'oggetto Graphics2D usato per disegnare.
     * @param images Mappa delle immagini usate per il rendering degli oggetti (non
     *               utilizzata in questo caso).
     */
    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(position.getX() * 20, position.getY() * 20, 20, 20);
    }

    /**
     * Restituisce la posizione attuale del muro.
     * 
     * @return La posizione del muro.
     */
    @Override
    public Position getPosition() {
        return position;
    }
}