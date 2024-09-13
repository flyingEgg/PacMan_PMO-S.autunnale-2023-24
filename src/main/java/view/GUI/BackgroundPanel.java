package main.java.view.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Un pannello che visualizza un'immagine di sfondo.
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    /**
     * Disegna l'immagine di sfondo sul pannello.
     *
     * @param g Il contesto grafico utilizzato per disegnare
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Ridimensiona l'immagine per adattarla alle dimensioni del pannello
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Imposta l'immagine di sfondo da visualizzare e ridisegna il pannello.
     *
     * @param image L'immagine di sfondo da impostare
     */
    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
        // Ridimensiona l'immagine per adattarla alle dimensioni del pannello
        setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
        repaint();
    }
}