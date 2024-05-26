package Entities;

public class Ghost extends AbstractEntity {
    public Ghost(int x, int y) {
        super(x, y);
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
}
