package DesignPatterns.Composite;

public class Position {
    private Integer y;
    private Integer x;

    public Position(Integer y, Integer x) {
        this.y = y;
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getX() {
        return x;
    }

    @Override
    public String toString() {
        return "(" + y + ", " + x + ")";
    }
}
