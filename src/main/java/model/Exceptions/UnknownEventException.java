package main.java.model.Exceptions;

public class UnknownEventException extends RuntimeException {
    public UnknownEventException(String message) {
        super(message);
    }
}
