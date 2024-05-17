package Game.Strategies;

public abstract class GhostMovementStrategy {
    protected boolean chasing;
    protected boolean fleeing;

    public abstract Direction calculateNextMove();

    public boolean isFleeing() {
        return fleeing;
    }

    public boolean isChasing() {
        return chasing;
    }
}
