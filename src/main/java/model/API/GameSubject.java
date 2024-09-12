package main.java.model.API;

/**
 * Interfaccia per un soggetto di gioco che può essere osservato.
 * I soggetti devono implementare questo per permettere agli osservatori
 * di registrarsi e ricevere notifiche sui cambiamenti.
 */
public interface GameSubject {

    /**
     * Aggiunge un osservatore alla lista di osservatori.
     * 
     * @param observer L'osservatore da aggiungere.
     */
    void attach(GameSubjectListener observer);

    /**
     * Rimuove un osservatore dalla lista di osservatori.
     * 
     * @param observer L'osservatore da rimuovere.
     */
    void detach(GameSubjectListener observer);

    /**
     * Notifica tutti gli osservatori riguardo a un cambiamento.
     */
    void notifyObservers();
}