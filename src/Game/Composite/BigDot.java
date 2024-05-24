package Game.Composite;

import Game.Game;
import Game.Position;

public class BigDot extends Dot {
    private boolean eaten;

    public BigDot(Position position) {
        super(position, 0); // 0 punti per un big dot
        this.eaten = false;
    }

    @Override
    public void draw() {
        // Logica specifica per disegnare un puntino grande sulla mappa di gioco
        System.out.println("Rendering big dot at position: " + getPosition());
    }

    @Override
    public void collect(Game game) {
        // Aggiorna lo stato di Pac-Man per consentire di mangiare i fantasmi
        // DA SCOMMENTARE player.setSuperMode(true);
        System.out.println("Collecting big dot at position: " + getPosition());
        // Altra logica specifica per la raccolta di un puntino grande dalla mappa di
        // gioco
        this.eaten = true;
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