package main.java.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.controller.Controller;

public class MainMenu extends JFrame {
    private final Controller controller;

    public MainMenu(Controller controller) {
        this.controller = controller;
        setupMenu();
    }

    public void setupMenu() {
        setTitle("Pacman - Main Menu");

        try {
            BufferedImage icon = ImageIO.read(getClass().getResource("/main/java/view/images/right.gif"));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dell'icona: " + e.getMessage());
        }

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pannello sfondo
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        try {
            BufferedImage backgroundImage = ImageIO
                    .read(getClass().getResource("/main/java/view/images/background.jpg"));
            backgroundPanel.setBackgroundImage(backgroundImage);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento dell'immagine di sfondo: " + e.getMessage());
        }
        backgroundPanel.setLayout(new BorderLayout());

        // Pannello pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Creazione pulsanti
        JButton startButton = new RoundedButton("Start Game");
        startButton.setBackground(new Color(34, 139, 34)); // Verde scuro
        startButton.setFont(new Font("Arial", Font.BOLD, 22));
        startButton.setMaximumSize(new Dimension(200, 50)); // Limita la dimensione del bottone
        startButton.addActionListener(e -> {
            controller.startGame();
            dispose(); // Chiude il menu principale
        });

        JButton exitButton = new RoundedButton("Exit");
        exitButton.setBackground(new Color(220, 20, 60)); // Rosso scuro
        exitButton.setFont(new Font("Arial", Font.BOLD, 22));
        exitButton.setMaximumSize(new Dimension(200, 50));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(50));

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);
        add(backgroundPanel);
        setVisible(true);
    }
}