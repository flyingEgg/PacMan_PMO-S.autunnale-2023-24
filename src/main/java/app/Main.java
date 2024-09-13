package main.java.app;

import javax.swing.SwingUtilities;

import main.java.controller.Controller;
import main.java.model.Model;
import main.java.view.View;

/**
 * Classe principale dell'applicazione.
 * Avvia l'applicazione e inizializza il Model, View e Controller.
 */
public class Main {

    /**
     * Metodo principale che avvia l'applicazione.
     * Utilizza SwingUtilities per garantire che l'inizializzazione avvenga
     * nel thread dell'event dispatching (EDT).
     * 
     * @param args Gli argomenti della riga di comando.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::initializeApplication);
    }

    /**
     * Metodo per inizializzare l'applicazione.
     * Crea e collega Model, View e Controller e avvia la vista.
     */
    private static void initializeApplication() {
        Model model = new Model(); // Crea un'istanza del Model
        View view = new View(null); // Crea un'istanza della View, placeholder per il Controller
        Controller controller = new Controller(model, view); // Crea un'istanza del Controller

        // Imposta il Controller nella View e il GamePanel nel Model
        view.setController(controller);
        model.setGamePanel(view.getGamePanel());

        // Imposta la View nel Controller
        controller.setView(view);

        // Inizializza la View
        view.initializeView();
    }
}