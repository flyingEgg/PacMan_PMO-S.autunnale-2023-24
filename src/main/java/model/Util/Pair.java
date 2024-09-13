package main.java.model.Util;

import java.util.Objects;

/**
 * Rappresenta una coppia di valori di tipo generico.
 *
 * @param <A> Il tipo del primo valore della coppia
 * @param <B> Il tipo del secondo valore della coppia
 */
public class Pair<A, B> {

    private final A a;
    private final B b;

    /**
     * Crea una nuova coppia di valori.
     *
     * @param a Il primo valore della coppia
     * @param b Il secondo valore della coppia
     */
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Ottiene il primo valore della coppia.
     *
     * @return Il primo valore
     */
    public A getA() {
        return a;
    }

    /**
     * Ottiene il secondo valore della coppia.
     *
     * @return Il secondo valore
     */
    public B getB() {
        return b;
    }

    /**
     * Restituisce una rappresentazione in formato stringa della coppia.
     *
     * @return Una stringa che rappresenta la coppia
     */
    @Override
    public String toString() {
        return "Pair [a=" + a + ", b=" + b + "]";
    }

    /**
     * Confronta questa coppia con un altro oggetto per verificare se sono uguali.
     *
     * @param o L'oggetto da confrontare con questa coppia
     * @return {@code true} se l'oggetto è una coppia con gli stessi valori,
     *         {@code false} altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
    }

    /**
     * Calcola l'hash code per questa coppia.
     *
     * @return L'hash code della coppia
     */
    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}