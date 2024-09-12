package main.java.model.API;

import java.util.Objects;

/**
 * Rappresenta una posizione nel piano bidimensionale.
 * Contiene le coordinate x e y e offre metodi per accedere
 * e modificare queste coordinate, oltre a metodi utili per
 * confrontare e calcolare distanze tra posizioni.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Crea una nuova posizione con le coordinate specificate.
     * 
     * @param x La coordinata x della posizione.
     * @param y La coordinata y della posizione.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Restituisce la coordinata x della posizione.
     * 
     * @return La coordinata x.
     */
    public int getX() {
        return x;
    }

    /**
     * Imposta la coordinata x della posizione.
     * 
     * @param x La nuova coordinata x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Restituisce la coordinata y della posizione.
     * 
     * @return La coordinata y.
     */
    public int getY() {
        return y;
    }

    /**
     * Imposta la coordinata y della posizione.
     * 
     * @param y La nuova coordinata y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Confronta questa posizione con un'altra posizione per verificare
     * l'uguaglianza.
     * 
     * @param obj L'oggetto da confrontare con questa posizione.
     * @return true se le posizioni sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    /**
     * Calcola il codice hash per questa posizione.
     * 
     * @return Il codice hash della posizione.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Restituisce una rappresentazione testuale della posizione.
     * 
     * @return Una stringa che rappresenta la posizione nel formato "(x, y)".
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Calcola la distanza euclidea tra questa posizione e un'altra posizione.
     * 
     * @param other La posizione con cui calcolare la distanza.
     * @return La distanza euclidea tra le due posizioni.
     */
    public double distanceTo(Position other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}