package Main;

public class Match {
    private boolean onGoing;
    private boolean paused;
    private boolean gameOver;

    public Match() {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = false;
    }

    public void startStopGame(boolean onGoing) {
        this.onGoing = onGoing;
        this.paused = false;
    }

    public void pauseUnpauseGame(boolean paused) {
        this.paused = paused;
        if (!paused) {
            this.onGoing = true;
        }
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
