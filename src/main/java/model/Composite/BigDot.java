package main.java.model.Composite;

import main.java.model.Model;
import main.java.model.API.Position;

import java.awt.*;
import java.util.Map;

import javax.swing.*;

/**
 * Rappresenta un grande punto nel gioco, che offre più punti e attiva la super
 * modalità.
 */
public class BigDot extends Dot {
    private static final int POINTS = 50; // Punti per BigDot

    /**
     * Crea un nuovo grande punto con la posizione specificata.
     * 
     * @param position La posizione del grande punto.
     */
    public BigDot(Position position) {
        super(position, POINTS); // Imposta i punti per il grande punto
    }

    /**
     * Disegna il grande punto sul pannello di gioco.
     * 
     * @param g2d    L'oggetto Graphics2D usato per disegnare.
     * @param images Mappa delle immagini usate per il rendering degli oggetti.
     */
    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        // Imposta il colore del grande punto a giallo
        g2d.setColor(Color.YELLOW);
        // Disegna un'ellisse (cerchio) che rappresenta il grande punto
        g2d.fillOval(position.getX() * 20, position.getY() * 20, 20, 20);
    }

    /**
     * Azioni da eseguire quando il grande punto viene raccolto.
     * 
     * @param model Il modello del gioco, usato per aggiornare il punteggio e lo
     *              stato.
     */
    @Override
    protected void onCollect(Model model) {
        // Incrementa il punteggio del giocatore
        model.incrementScore(points);
        // Attiva la super modalità con un numero fisso di mosse
        model.activateSuperMode(20);
        // Stampa un messaggio di debug con la posizione del grande punto
        System.out.println("BigDot mangiato alla posizione: " + getPosition());
    }
}