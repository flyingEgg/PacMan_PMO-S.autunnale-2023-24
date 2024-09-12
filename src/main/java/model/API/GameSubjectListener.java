package main.java.model.API;

import main.java.model.Model;
import main.java.model.Event.Event;

/**
 * Interfaccia per un ascoltatore di eventi di gioco.
 * Gli ascoltatori devono implementare questo per ricevere notifiche
 * sugli eventi di gioco.
 */
public interface GameSubjectListener {

    /**
     * Metodo chiamato quando si verifica un evento di gioco.
     * 
     * @param model Il modello di gioco che ha generato l'evento.
     * @param event L'evento di gioco che è stato generato.
     */
    void onGameEvent(Model model, Event event);
}