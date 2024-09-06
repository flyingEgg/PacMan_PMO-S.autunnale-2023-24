package main.java.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import main.java.controller.Controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainMenu extends JFrame {
    private final Controller controller;

    public MainMenu(Controller controller) {
        this.controller = controller;
    }

    public void setupMenu() {
        setTitle("Pacman - Main Menu");

        try {
            BufferedImage icon = ImageIO.read(getClass().getResource("/main/java/view/images/right.gif"));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dell'icona: " + e.getMessage());
        }

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            controller.startGame();
            dispose(); // Chiude il menu principale
        });

        JButton exitButton = new JButton("Exit");

        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(50));

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
