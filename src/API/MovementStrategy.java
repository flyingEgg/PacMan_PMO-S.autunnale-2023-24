package API;

import Entities.AbstractEntity;
import Game.Strategies.Direction;

public interface MovementStrategy<P extends AbstractEntity> {
    void move(Direction direction); // Object al posto di Direction per evitare dipendenze
}
