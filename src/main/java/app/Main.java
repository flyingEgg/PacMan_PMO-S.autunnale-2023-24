package main.java.app;

import javax.swing.SwingUtilities;
import main.java.controller.Controller;
import main.java.model.Model;
import main.java.view.View;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View(null);
            Controller controller = new Controller(view, model);
            view.setController(controller);
            view.showMainMenu();
            view.setVisible(true);
        });
    }
}
