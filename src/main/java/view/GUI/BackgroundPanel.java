package main.java.view.GUI;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
        repaint();
    }
}
