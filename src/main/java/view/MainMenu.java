package main.java.view;

import javax.swing.*;
import java.awt.*;
import main.java.controller.Controller;

public class MainMenu extends JPanel {
    private Controller controller;

    public MainMenu(Controller controller) {
        this.controller = controller;
        setupMenu();
    }

    private void setupMenu() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(e -> startGame());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(50));

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        if (controller != null) {
            controller.startGame();
            SwingUtilities.invokeLater(() -> {
                Container parent = getParent();
                if (parent != null) {
                    parent.remove(MainMenu.this);
                    parent.revalidate();
                    parent.repaint();
                }
            });
        } else {
            System.out.println("Controller is not initialized.");
        }
    }
}
