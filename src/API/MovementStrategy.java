package API;

public interface MovementStrategy {
    void move(Object direction); // Object al posto di Direction per evitare dipendenze
}
