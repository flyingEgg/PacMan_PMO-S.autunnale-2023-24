package Game.Composite;

public enum FruitType {
    STRAWBERRY(100),
    CHERRY(300),
    ORANGE(500),
    APPLE(700),
    MELON(1000);

    private int points;

    FruitType(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
