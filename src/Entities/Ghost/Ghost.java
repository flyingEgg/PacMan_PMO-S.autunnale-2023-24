package Entities.Ghost;
import Entities.AbstractEntity;

public class Ghost extends AbstractEntity {
    private Color color;

    public Ghost(int x, int y, Color c) {
        super(x, y);
        this.color = c;
    }

    @Override
    protected void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void draw() {
        System.out.println("Fantasma disegnato alla posizione: " + x + ", " + y);
    }

    public void runAway() {
        // Logica per far scappare il fantasma
        System.out.println("Fantasma alla posizione " + getPosition() + " sta scappando!");
        // Aggiungere logica per determinare come il fantasma scappa
    }

    public Color getColor() {
        return color;
    }
}
