package main.java.model;

import java.util.List;
import main.java.model.Entities.Pacman;
import main.java.model.Entities.Ghost;
import main.java.model.API.Direction;
import main.java.model.Exceptions.IllegalEntityMovementException;
import main.java.model.Strategies.PacmanMovementStrategy;
import main.java.model.API.GameStatisticsListener;

public class Model {
    private Game game;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private PacmanMovementStrategy pacmanMovementStrategy;

    public Model() {
        this.game = new Game();
        this.pacman = game.getPacman();
        this.ghosts = game.getGhosts();
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, game.getGrid(), game);
    }

    public void movePacman(Direction direction) throws IllegalEntityMovementException {
        pacmanMovementStrategy.move(direction);
    }

    public boolean isGameOver() {
        return game.isGameOver();
    }

    public void resetGame() {
        game = new Game();
        pacman = game.getPacman();
        ghosts = game.getGhosts();
        pacmanMovementStrategy = new PacmanMovementStrategy(pacman, game.getGrid(), game);
    }

    public Game getGame() {
        return game;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }
}
