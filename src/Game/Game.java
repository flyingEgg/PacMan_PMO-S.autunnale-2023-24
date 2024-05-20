package Game;

import Entities.Ghost;
import Entities.Pacman;

public class Game {
    private boolean onGoing;
    private boolean paused;
    private boolean gameOver;
    private int score, lives;
    private Pacman pacman;
    private Ghost ghost1, ghost2, ghost3, ghost4;


    public Game() {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = false;
        this.lives = 3;
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

    public void setGameOver(boolean gameover) {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = gameover;
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

    public void resetScore(){
        this.score = 0;
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
}
