package main.java.model.API;

import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.*;

public interface MapComponent {
    void draw(Graphics2D g2d, Map<String, ImageIcon> images);

    /**
     * @return La posizione attuale del component
     */
    Position getPosition();
}
