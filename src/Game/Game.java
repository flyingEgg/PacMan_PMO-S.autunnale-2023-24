package Game;

import java.util.List;

import Entities.Ghost;
import Entities.Pacman;

public class Game {
    private boolean onGoing;
    private boolean paused;
    private boolean gameOver;
    private int score, lives;
    private Pacman pacman;
    private List<Ghost> ghosts;

    public Game(Pacman pacman, List<Ghost> ghosts) {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = false;
        this.lives = 3;
        this.pacman = pacman;
        this.ghosts = ghosts;
        this.score = 0;
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
}
