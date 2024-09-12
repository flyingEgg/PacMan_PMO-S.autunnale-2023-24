package main.java.model.Exceptions;

/**
 * Eccezione lanciata quando viene incontrato un tipo di evento sconosciuto.
 */
public class UnknownEventException extends RuntimeException {

    /**
     * Costruttore per l'eccezione UnknownEventException.
     *
     * @param message Il messaggio di errore che descrive il motivo dell'eccezione.
     */
    public UnknownEventException(String message) {
        super(message);
    }
}