package Game.Composite;

import Entities.Ghost.Ghost;
import Entities.Pacman;
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
        System.out.println("BIG_DOT: " + getPosition());
    }

    @Override
    public void collect(Game game) {
        // Imposta Pacman in modalità super
        Pacman pacman = game.getPacman();
        pacman.setSuperMode(true);

        // Fai scappare i fantasmi
        for (Ghost ghost : game.getGhosts()) {
            ghost.runAway();
        }

        System.out.println("Collecting big dot at position: " + getPosition());
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