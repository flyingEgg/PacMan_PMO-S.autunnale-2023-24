package Game.Composite;

import API.MapComponent;
import Game.Position;

public class EmptySpace implements MapComponent {
    private Position position;

    public EmptySpace(Position p){
        this.position = p;
    }

    @Override
    public void draw() {
        System.out.print(" ");
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
