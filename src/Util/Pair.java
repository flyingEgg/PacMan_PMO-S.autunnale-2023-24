package Util;

public class Pair<X,Y> {

    private final X x;
    private final Y y;

    public Pair(X x, Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Utilities.Pair [x=" + x + ", y=" + y + "]";
    }



}
