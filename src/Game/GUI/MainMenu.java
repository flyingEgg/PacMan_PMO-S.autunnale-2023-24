package Game.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Pacman - Main Menu");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la finestra sullo schermo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Creazione di un pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Creazione dei pulsanti
        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit");

        // Aggiunta di azioni ai pulsanti
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PacmanGameWindow(); // Avvia il gioco
                dispose(); // Chiude il menu principale
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Esce dal programma
            }
        });

        // Aggiunta dei pulsanti al pannello
        buttonPanel.add(Box.createVerticalStrut(50)); // Spazio prima del primo pulsante
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Spazio tra i pulsanti
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(50)); // Spazio dopo l'ultimo pulsante

        // Centraggio dei pulsanti
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aggiunta del pannello dei pulsanti al JFrame
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}