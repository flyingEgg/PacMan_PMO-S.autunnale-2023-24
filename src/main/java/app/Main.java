package main.java.app;

import javax.swing.SwingUtilities;
import main.java.controller.Controller;
import main.java.model.Model;
import main.java.view.View;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            Controller controller = new Controller(null, model);
            View view = new View(controller);
            controller.setView(view);
        });
    }
}
