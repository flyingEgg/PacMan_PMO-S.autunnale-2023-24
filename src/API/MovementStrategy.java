package API;

import Entities.AbstractEntity;
import Game.Strategies.Direction;

public interface MovementStrategy<E extends AbstractEntity> {
    void move(Direction direction); // Object al posto di Direction per evitare dipendenze
}
