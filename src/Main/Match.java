package Main;

public class Match {
    private boolean onGoing;
    private boolean paused;
    private boolean gameOver;
    private boolean restart;
    private int score;

    public Match() {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = false;
        this.restart = false;
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

    public void setGameOver(boolean gameover) {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = gameover;
    }

    public void setRestartMatch(boolean restart){
        restartScore();
        this.onGoing = false;
        this.paused = false;
        this.gameOver = false;
        this.restart = restart;
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

    public void restartScore(){
        this.score = 0;
    }

    public void displayMessage() {
        if (gameOver) {
            System.out.println("Partita terminata");
        } else if (paused) {
            System.out.println("Partita in pausa");
        } else if (restart) {
            System.out.println("Partita ricominciata");
        } else {
            System.out.println("Partita in corso");
        }
    }
}
