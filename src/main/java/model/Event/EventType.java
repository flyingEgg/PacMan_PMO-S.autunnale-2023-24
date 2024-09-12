package main.java.model.Event;

/**
 * Enum che rappresenta i vari tipi di eventi che possono verificarsi nel gioco.
 */
public enum EventType {
    PACMAN_MOVED,
    GHOST_MOVED,
    SMALL_DOT_EATEN,
    BIG_DOT_EATEN,
    PACMAN_GHOST_COLLIDE,
    PACMAN_GHOST_COLLIDE_BIGDOT,
    NO_LIVES_LEFT
}