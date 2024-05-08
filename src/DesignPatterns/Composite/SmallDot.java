package DesignPatterns.Composite;

public class SmallDot extends Dot {
    private Position position;
    private boolean eaten;

    public SmallDot(Position position) {
        super(position);
        this.eaten = false;
    }

    @Override
    public void render() {
        // Logica specifica per disegnare un puntino piccolo sulla mappa di gioco
        System.out.println("Rendering small dot at position: " + getPosition());
    }

    @Override
    public void collect() {
        this.eaten = true;
        // Logica per raccogliere un puntino piccolo
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