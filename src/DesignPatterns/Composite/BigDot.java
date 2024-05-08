package DesignPatterns.Composite;

public class BigDot extends Dot {
    private Position position;
    private boolean eaten;

    public BigDot(Position position) {
        super(position);
        this.eaten = false;
    }

    @Override
    public void render() {
        // Implementazione per disegnare un puntino grande sulla mappa di gioco
    }

    @Override
    public void collect() {
        this.eaten = true;
        // Logica per raccogliere un puntino grande
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