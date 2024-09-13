package main.java.model.Composite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.ImageIcon;

import main.java.model.Model;
import main.java.model.Movement.Position;

/**
 * Rappresenta un piccolo punto nel gioco che può essere raccolto dal giocatore.
 * I punti guadagnati per raccogliere questo punto sono 10.
 */
public class SmallDot extends Dot {
    private static final int POINTS = 10; // Punti per SmallDot

    /**
     * Crea un nuovo SmallDot con la posizione specificata.
     * 
     * @param position La posizione del punto.
     */
    public SmallDot(Position position) {
        super(position, POINTS); // 10 punti per un SmallDot
    }

    /**
     * Disegna il piccolo punto sul pannello di gioco.
     * 
     * @param g2d    L'oggetto Graphics2D usato per disegnare.
     * @param images Mappa delle immagini usate per il rendering degli oggetti.
     */
    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(position.getX() * 20 + 5, position.getY() * 20 + 5, 10, 10);
    }

    /**
     * Restituisce una rappresentazione in formato stringa del SmallDot.
     * 
     * @return La rappresentazione in formato stringa del SmallDot.
     */
    @Override
    public String toString() {
        return "SmallDot at " + position + " [eaten=" + eaten + "]";
    }

    /**
     * Gestisce le azioni da eseguire quando il SmallDot viene raccolto.
     * Incrementa il punteggio e notifica il cambiamento di punteggio.
     * 
     * @param model Il modello del gioco, usato per aggiornare lo stato e il
     *              punteggio.
     */
    @Override
    protected void onCollect(Model model) {
        model.incrementScore(points); // Aumenta il punteggio
        model.notifyScoreChanged(); // Notifica il cambiamento di punteggio
    }
}