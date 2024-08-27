package Game.GUI;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Pacman - Main Menu");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la finestra sullo schermo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(e -> {
            new PacmanGameWindow(); // Avvia il gioco
            dispose(); // Chiude il menu principale
        });

        exitButton.addActionListener(e -> System.exit(0)); // Esce dal programma

        buttonPanel.add(Box.createVerticalStrut(50)); // Spazio prima del primo pulsante
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Spazio tra i pulsanti
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(50)); // Spazio dopo l'ultimo pulsante

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
