package main.java.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    private static final int ARC_WIDTH = 40;
    private static final int ARC_HEIGHT = 40;
    private static final Color DEFAULT_COLOR = new Color(0, 0, 0); // Nero
    private static final Color HOVER_COLOR = Color.WHITE;

    /**
     * Costruisce un pulsante arrotondato con il testo specificato.
     *
     * @param text Il testo del pulsante
     */
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Per la pittura personalizzata
        setFocusPainted(false); // Rimuove il bordo di focus
        setBorderPainted(false); // Rimuove il bordo
        setOpaque(false); // Nessun riempimento di sfondo predefinito

        // Imposta il colore predefinito del testo
        setForeground(DEFAULT_COLOR);

        // Aggiungi un listener del mouse per gli effetti hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(DEFAULT_COLOR);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Imposta il colore del pulsante basato sullo stato
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground() != null ? getBackground() : Color.GRAY); // Usa il colore di sfondo o un colore
                                                                                 // predefinito
        }

        // Disegna il pulsante arrotondato
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        // Disegna il testo del pulsante
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int stringWidth = fm.stringWidth(getText());
        int stringHeight = fm.getAscent();
        int x = (getWidth() - stringWidth) / 2;
        int y = (getHeight() + stringHeight) / 2 - 4;
        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Non dipingere il bordo
    }
}