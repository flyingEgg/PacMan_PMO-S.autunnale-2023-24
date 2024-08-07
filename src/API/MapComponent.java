package API;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import Game.Position;

public interface MapComponent {
    void draw(Graphics2D g2d, Map<String, BufferedImage> images);

    /**
     *
     * @return La posizione attuale del component
     */
    Position getPosition();
}
