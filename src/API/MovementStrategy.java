package API;

import Entities.AbstractEntity;

public interface MovementStrategy<P extends AbstractEntity> {
    void move(Object direction); // Object al posto di Direction per evitare dipendenze
}
