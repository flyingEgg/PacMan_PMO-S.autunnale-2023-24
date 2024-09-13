package main.java.model.Composite;

import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.ImageIcon;

import main.java.model.Model;
import main.java.API.MapComponent;
import main.java.model.Movement.Position;

/**
 * Rappresenta un punto nel gioco, che può essere raccolto dal giocatore.
 */
public abstract class Dot implements MapComponent {
    protected final Position position;
    protected boolean eaten;
    protected final int points;

    /**
     * Crea un nuovo punto con la posizione e i punti specificati.
     * 
     * @param position La posizione del punto.
     * @param points   I punti assegnati al punto.
     */
    public Dot(Position position, int points) {
        this.position = position;
        this.eaten = false;
        this.points = points;
    }

    /**
     * Disegna il punto sul pannello di gioco.
     * 
     * @param g2d    L'oggetto Graphics2D usato per disegnare.
     * @param images Mappa delle immagini usate per il rendering degli oggetti.
     */
    @Override
    public abstract void draw(Graphics2D g2d, Map<String, ImageIcon> images);

    /**
     * Marca il punto come raccolto e chiama il metodo {@link #onCollect(Model)} per
     * gestire ulteriori logiche legate alla raccolta del punto.
     * 
     * @param model Il modello del gioco, usato per aggiornare lo stato e il
     *              punteggio.
     */
    public void collect(Model model) {
        this.eaten = true;
        onCollect(model); // Chiama il metodo per la logica aggiuntiva
    }

    /**
     * Gestisce le azioni da eseguire quando il punto viene raccolto. Deve essere
     * implementato dalle sottoclassi.
     * 
     * @param model Il modello del gioco, usato per aggiornare lo stato e il
     *              punteggio.
     */
    protected abstract void onCollect(Model model);

    /**
     * Restituisce la posizione del punto.
     * 
     * @return La posizione del punto.
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Verifica se il punto è stato raccolto.
     * 
     * @return true se il punto è stato raccolto, false altrimenti.
     */
    public boolean isEaten() {
        return eaten;
    }
}