package Main;

public class Match {
    boolean onGoing;



    public void isOnGoing(boolean onGoing) {
        this.onGoing = onGoing;
    }

    public void displayMessage(){
        if(onGoing)
            System.out.println("Partita in corso");
        else
            System.out.println("Partita non in corso");
    }
}
