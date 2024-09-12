package main.java.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    private static final int ARC_WIDTH = 40;
    private static final int ARC_HEIGHT = 40;
    private static final Color DEFAULT_COLOR = new Color(0, 0, 0); // Black
    private static final Color HOVER_COLOR = Color.WHITE;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // For custom painting
        setFocusPainted(false); // Removes the focus border
        setBorderPainted(false); // Removes the border
        setOpaque(false); // No default background fill

        // Set default foreground color
        setForeground(DEFAULT_COLOR);

        // Add mouse listener for hover effects
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

        // Set button color based on state
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }

        // Draw rounded button
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        // Draw button text
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
        // Do not paint the border
    }
}
