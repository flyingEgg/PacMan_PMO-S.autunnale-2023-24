package Game;

import java.util.ArrayList;
import java.util.List;

import Entities.Ghost.Color;
import Entities.Ghost.Ghost;
import Entities.Pacman;

public class Game {
    private boolean onGoing;
    private boolean paused;
    private boolean gameOver;
    private int score, lives;
    private PacmanGrid grid;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private int currentLevel;

    public Game() {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = false;
        this.lives = 3;
        this.grid = new PacmanGrid();
        this.pacman = new Pacman(11, 9);
        this.ghosts = new ArrayList<>();
        this.score = 0;
        this.currentLevel = 1;
        initialiseGhosts();
    }

    public void startStopGame(boolean onGoing) {
        this.onGoing = onGoing;
        this.paused = false;
        this.gameOver = false;
    }

    public void pauseUnpauseGame(boolean paused) {
        this.paused = paused;
        this.onGoing = !paused;
    }

    public void setGameOver(boolean gameOver) {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = gameOver;
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void incrementScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        this.score = 0;
    }

    public void loseLife() {
        lives--;
        if (lives <= 0) {
            setGameOver(true);
        }
    }

    public int getLives() {
        return lives;
    }

    public void displayMessage() {
        if (gameOver) {
            System.out.println("Hai perso! Riprovare?");
        } else if (paused) {
            System.out.println("Partita in pausa");
        } else {
            System.out.println("Partita in corso");
        }
    }

    public void eatGhost(Position ghostPosition) {
        Ghost ghost = ghosts.stream().filter(g -> g.getPosition().equals(ghostPosition)).findFirst().orElse(null);
        if (ghost != null) {
            incrementScore(200); // Aggiungi punti per ogni fantasma mangiato
            ghosts.remove(ghost);
            grid.removeComponent(ghost);
        }
    }

    public PacmanGrid getGrid() {
        return this.grid;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public Ghost getGhost(int index) {
        if (index >= 0 && index < ghosts.size()) {
            return ghosts.get(index);
        } else {
            System.out.println("Indice fantasma non valido: " + index);
            return null;
        }
    }

    private void initialiseGhosts() {
        ghosts.add(new Ghost(9, 8, Color.RED));
        ghosts.add(new Ghost(9, 9, Color.ORANGE));
        ghosts.add(new Ghost(9, 10, Color.PINK));
        ghosts.add(new Ghost(8, 9, Color.BLUE));
    }

    public void nextLevel() {
        currentLevel++;
        incrementGhostSpeed();
        grid.initializeMap(); // Reinizializza la mappa
        pacman.setPosition(grid.getPacmanStartPosition());
        initialiseGhosts();
    }

    private void incrementGhostSpeed() {
        // Aumenta la velocità dei fantasmi al passare dei livelli???
    }
}
