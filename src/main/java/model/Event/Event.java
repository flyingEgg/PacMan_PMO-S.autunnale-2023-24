package main.java.model.Event;

/**
 * Rappresenta un evento nel sistema con un tipo e un timestamp.
 */
public class Event {
    private EventType type;
    private long timestamp;

    /**
     * Crea un nuovo evento con il tipo e il timestamp specificati.
     * 
     * @param type      Il tipo di evento.
     * @param timestamp Il timestamp dell'evento.
     */
    public Event(EventType type, long timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }

    /**
     * Restituisce il tipo di evento.
     * 
     * @return Il tipo di evento.
     */
    public EventType getType() {
        return type;
    }

    /**
     * Imposta il tipo di evento.
     * 
     * @param type Il nuovo tipo di evento.
     */
    public void setType(EventType type) {
        this.type = type;
    }

    /**
     * Restituisce il timestamp dell'evento.
     * 
     * @return Il timestamp dell'evento.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Imposta il timestamp dell'evento.
     * 
     * @param timestamp Il nuovo timestamp dell'evento.
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}