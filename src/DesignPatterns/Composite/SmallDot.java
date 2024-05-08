package DesignPatterns.Composite;

import Main.Match;

public class SmallDot extends Dot {
    private Position position;
    private boolean eaten;

    public SmallDot(Position position) {
        super(position, 10); // 10 punti per uno small dot
        this.eaten = false;
    }

    @Override
    public void render() {
        // Logica specifica per disegnare un puntino piccolo sulla mappa di gioco
        System.out.println("Rendering small dot at position: " + getPosition());
    }

    @Override
    public void collect(Match match) {
        // Logica generica per raccogliere un punto
        System.out.println("Collecting small dot at position: " + getPosition());
        match.incrementScore(points); // Incrementa il punteggio solo per uno small dot
        System.out.println("Score updated: " + match.getScore()); // Stampa il punteggio aggiornato
        this.eaten = true;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isEaten() {
        return eaten;
    }
}