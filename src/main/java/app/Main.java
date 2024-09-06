package main.java.app;

import javax.swing.SwingUtilities;
import main.java.controller.Controller;
import main.java.model.Model;
import main.java.view.View;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View(model, null); // Initialize View with a placeholder for the Controller
            Controller controller = new Controller(model, view);
            view.setController(controller); // Set the Controller in the View
            model.setGamePanel(view.getGamePanel()); // Set the GamePanel in the Model
            controller.setView(view); // Set the View in the Controller
            view.initializeView(); // Call any initialization methods for the View
        });
    }
}
