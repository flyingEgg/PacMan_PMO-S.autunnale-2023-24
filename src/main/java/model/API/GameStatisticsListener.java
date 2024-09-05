package main.java.model.API;

public interface GameStatisticsListener {
    void onScoreChanged(int newScore);

    void onLivesChanged(int newLives);
}
