package main.java.controller;

import main.java.model.Model;
import main.java.model.Movement.Direction;
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
        if (model.isOnGoing()) {
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
        if (model.isOnGoing()) {
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
            // Verifica se la posizione di Pacman è uguale a quella del fantasma
            if (model.getPacman().getPosition().equals(ghost.getPosition())) {
                // Se Pacman è in modalità Supermode
                if (model.isSuperModeActive()) {
                    // Pacman mangia il fantasma
                    model.eatGhost(ghost.getPosition());
                } else {
                    // Pacman perde una vita
                    model.loseLife();
                }
                view.updateInfoPanel();
                return;
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

    /**
     * Ottiene l'high score dal model.
     *
     * @return l'high score
     */
    public int getHighScore() {
        return model.readHighScoreFromFile();
    }

    /**
     * Aggiorna l'high score nel model.
     *
     * @param score Il punteggio attuale del giocatore
     */
    public void updateHighScore(int score) {
        model.updateHighScore(score);
    }

    /**
     * Ottiene il Model.
     *
     * @return l'istanza del Model
     */
    public Model getModel(){
        return model;
    }
}