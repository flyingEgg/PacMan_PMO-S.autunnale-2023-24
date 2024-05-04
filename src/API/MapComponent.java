package API;

public interface MapComponent {
    void render();

    Object getPosition(); // Object al posto di Position per evitare dipendenze
}
