package main.java.model.Exceptions;

/**
 * Eccezione lanciata quando viene tentato un movimento non valido per
 * un'entità.
 */
public class IllegalEntityMovementException extends RuntimeException {

    /**
     * Costruttore per l'eccezione IllegalEntityMovementException.
     *
     * @param message Il messaggio di errore che descrive il motivo dell'eccezione.
     */
    public IllegalEntityMovementException(String message) {
        super(message);
    }
}