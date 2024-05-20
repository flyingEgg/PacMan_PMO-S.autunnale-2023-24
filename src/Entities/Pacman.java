package Entities;

import Game.Position;

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
    public void draw() {
        System.out.println("Pacman disegnato alla posizione: "+x+", "+y);
    }

    @Override
    public Position getPosition() {
        return new Position(getX(), getY());
    }

    public void setPosition(Position position){
        this.x = position.getX();
        this.y = position.getY();
    }
}
