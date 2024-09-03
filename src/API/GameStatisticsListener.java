package API;

public interface GameStatisticsListener {
    void onScoreChanged(int newScore);
    void onLivesChanged(int newLives);
}
