package Main;

public class Match {
    boolean onGoing, paused;

    public Match(){

    }

    public void startStopGame(boolean onGoing) {
        this.onGoing = onGoing;
        this.paused = false;
    }

    public void pauseUnpauseGame(boolean p){
        this.paused = p;
        if(p)
            this.onGoing = false;
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public boolean isPaused(){
        return paused;
    }

    public void displayMessage(){
        if(onGoing)
            System.out.println("Partita in corso");
        else if (paused)
            System.out.println("Partita in pausa");
        else
            System.out.println("Partita non in corso");
    }
}
