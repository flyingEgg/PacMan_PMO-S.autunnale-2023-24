package main.java.model.API;

import main.java.model.Movement.Position;

import java.awt.Graphics2D;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * Interfaccia per i componenti della mappa nel gioco.
 * I componenti della mappa devono implementare questa interfaccia
 * per poter essere disegnati e per fornire la loro posizione.
 */
public interface MapComponent {

    /**
     * Disegna il componente sulla mappa utilizzando le immagini fornite.
     * 
     * @param g2d    L'oggetto Graphics2D utilizzato per disegnare il componente.
     * @param images Una mappa di nomi di immagini a icone di immagini da utilizzare
     *               per il disegno.
     */
    void draw(Graphics2D g2d, Map<String, ImageIcon> images);

    /**
     * Restituisce la posizione attuale del componente sulla mappa.
     * 
     * @return La posizione attuale del componente.
     */
    Position getPosition();
}