package main.java.controller;

import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.Entities.Ghost;
import main.java.view.View;

/**
 * Classe Controller per gestire le interazioni tra il modello e la vista.
 */
public class Controller {

    private final Model model;
    private View view;

    /**
     * Costruisce un Controller con il modello e la vista specificati.
     *
     * @param model l'istanza del modello
     * @param view  l'istanza della vista
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Avvia il gioco e mostra la finestra di gioco.
     */
    public void startGame() {
        model.startStopGame(true);
        view.showGameWindow(); // Mostra la finestra di gioco quando inizia il gioco
        view.updateInfoPanel(); // Aggiorna il pannello informazioni con lo stato iniziale del gioco
    }

    /**
     * Muove Pacman nella direzione specificata.
     *
     * @param direction la direzione in cui muovere Pacman
     */
    public void movePacman(Direction direction) {
        if (model.isOnGoing() && !model.isPaused()) {
            checkForCollisions(); // Controlla le collisioni prima di muovere Pacman
            model.movePacman(direction);
            checkForCollisions(); // Controlla le collisioni dopo aver mosso Pacman

            view.updateInfoPanel(); // Aggiorna il pannello informazioni dopo il movimento di Pacman
            view.getGamePanel().repaint(); // Ridisegna il pannello di gioco per riflettere le modifiche
        }
    }

    /**
     * Muove i fantasmi.
     */
    public void moveGhosts() {
        if (model.isOnGoing() && !model.isPaused()) {
            model.moveGhosts();
            view.getGamePanel().repaint(); // Ridisegna il pannello di gioco per riflettere le modifiche
        }
    }

    /**
     * Controlla le collisioni tra Pacman e i fantasmi.
     */
    private void checkForCollisions() {
        // Controlla le collisioni tra Pacman e i fantasmi
        for (Ghost ghost : model.getGhosts()) {
            if (model.getPacman().getPosition().equals(ghost.getPosition())) {
                if (model.isSuperModeActive()) {
                    model.eatGhost(ghost.getPosition());
                } else {
                    model.loseLife();
                }
                view.updateInfoPanel(); // Aggiorna il pannello informazioni se un fantasma è stato mangiato o se è
                                        // stata persa una vita
                return; // Termina il controllo dopo aver gestito una collisione
            }
        }
    }

    /**
     * Imposta la vista del controller.
     *
     * @param view la nuova vista da impostare
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Mostra il menu principale.
     */
    public void showMainMenu() {
        view.showMainMenu();
    }

    /**
     * Riavvia il gioco.
     */
    public void restartGame() {
        view.resetStats(false);
    }

    /**
     * Continua a giocare dopo una vittoria.
     */
    public void keepPlayingWin() {
        view.resetStats(true);
    }
}