package Entities;

public class Pacman extends AbstractEntity {
    private boolean superMode;

    public Pacman(int x, int y) {
        super(x, y);
        this.superMode = false;
    }

    @Override
    protected void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void draw() {
        System.out.println("Pacman disegnato alla posizione: " + x + ", " + y);
    }

    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
        // Puoi aggiungere logica per impostare un timer per il superMode se necessario
    }

    public boolean isSuperMode() {
        return superMode;
    }
}
