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
        startButton.setForeground(Color.BLACK);
        startButton.setFont(new Font("Arial", Font.BOLD, 22));
        startButton.setFocusPainted(false);
        startButton.setMaximumSize(new Dimension(200, 50)); // Limita la dimensione del bottone

        startButton.addActionListener(e -> {
            controller.startGame();
            dispose(); // Chiude il menu principale
        });
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setForeground(Color.BLACK);
            }
        });

        JButton exitButton = new RoundedButton("Exit");
        exitButton.setBackground(new Color(220, 20, 60)); // Rosso scuro
        exitButton.setForeground(Color.BLACK);
        exitButton.setFont(new Font("Arial", Font.BOLD, 22));
        exitButton.setFocusPainted(false);
        exitButton.setMaximumSize(new Dimension(200, 50));

        exitButton.addActionListener(e -> System.exit(0));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setForeground(Color.BLACK);
            }
        });

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

    // Classe per creare pulsanti con angoli stondati
    private static class RoundedButton extends JButton {
        private static final int ARC_WIDTH = 40;
        private static final int ARC_HEIGHT = 40;

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false); // Per permettere la personalizzazione della grafica
            setFocusPainted(false); // Rimuove il bordo di focus
            setBorderPainted(false); // Rimuove il bordo
            setOpaque(false); // Per non riempire lo sfondo con il colore predefinito
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Imposta il colore del pulsante a seconda dello stato
            if (getModel().isArmed()) {
                g2.setColor(getBackground().darker());
            } else {
                g2.setColor(getBackground());
            }

            // Disegna il pulsante con angoli stondati
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

            // Disegna il testo del pulsante
            g2.setColor(getForeground());
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int stringWidth = fm.stringWidth(getText());
            int stringHeight = fm.getAscent();
            g2.drawString(getText(), (getWidth() - stringWidth) / 2, (getHeight() + stringHeight) / 2 - 4);

            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // Non dipingere alcun bordo
        }
    }
}
