package main.java.model.Util;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Pair [a=" + a + ", b=" + b + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
