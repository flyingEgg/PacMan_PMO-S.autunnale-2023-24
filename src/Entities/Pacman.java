package Entities;

public class Pacman extends AbstractEntity {

    public Pacman(int x, int y){
        super(x, y);
    }

    @Override
    protected void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    protected void draw() {

    }
}
