package main.java.model.API;

import main.java.model.Entities.AbstractEntity;
import main.java.model.Movement.Direction;

/**
 * Interfaccia per la strategia di movimento di un'entità.
 * Le classi che implementano questa interfaccia definiscono
 * come un'entità dovrebbe muoversi in base alla direzione data.
 * 
 * @param <E> Il tipo dell'entità che utilizza questa strategia di movimento.
 */
public interface MovementStrategy<E extends AbstractEntity> {

    /**
     * Muove l'entità nella direzione specificata.
     * 
     * @param direction La direzione in cui muovere l'entità.
     */
    void move(Direction direction);
}