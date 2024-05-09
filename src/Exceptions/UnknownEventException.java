package Exceptions;

public class UnknownEventException extends RuntimeException{
    public UnknownEventException(String message){
        super(message);
    }
}
