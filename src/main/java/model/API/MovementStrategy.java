package main.java.model.API;

import main.java.model.Entities.AbstractEntity;

public interface MovementStrategy<E extends AbstractEntity> {
    void move(Direction direction);
}
