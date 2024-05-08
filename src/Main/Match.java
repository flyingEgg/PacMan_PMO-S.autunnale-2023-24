package Main;

public class Match {
    private boolean onGoing;
    private boolean paused;
    private boolean gameOver;
    private int score;

    public Match() {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = false;
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

    public void gameOver() {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = true;
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

    public void increaseScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void displayMessage() {
        if (gameOver) {
            System.out.println("Partita terminata");
        } else if (paused) {
            System.out.println("Partita in pausa");
        } else {
            System.out.println("Partita in corso");
        }
    }
}
