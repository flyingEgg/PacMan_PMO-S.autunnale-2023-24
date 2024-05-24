package Game.Composite;

import API.MapComponent;
import Game.Position;

public class Wall implements MapComponent {
    private Position position;

    public Wall(Position position) {
        this.position = position;
    }

    @Override
    public void draw() {
        // Logica di rendering per il muro (es. stampa a console o disegno su GUI)
        System.out.print("#");
    }

    @Override
    public Position getPosition() {
        return position;
    }
}