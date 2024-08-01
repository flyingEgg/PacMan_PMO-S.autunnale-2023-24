package Util;

public class Pair<A, B> {

    private final A a;
    private final B b;

    public Pair(A a, B b) {
        super();
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    @Override
    public String toString() {
        return "Utilities.Pair [x=" + a + ", y=" + b + "]";
    }



}
