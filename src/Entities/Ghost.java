package Entities;

import Game.Composite.Position;

public class Ghost extends AbstractEntity{
    public Ghost(int x, int y){
        super(x, y);
    }

    @Override
    protected void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void draw() {
        System.out.println("Fantasma disegnato alla posizione: "+x+", "+y);
    }

    public Position getPosition() {
        return new Position(getX(), getY());
    }

    public void setPosition(Position position){
        this.x = position.getX();
        this.y = position.getY();

    }
}
