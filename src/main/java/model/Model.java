package main.java.model;

import java.util.ArrayList;
import java.util.List;

import main.java.controller.Strategies.GhostChaseStrategy;
import main.java.controller.Strategies.PacmanMovementStrategy;
import main.java.model.API.Direction;
import main.java.model.API.GameStatisticsListener;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.GhostColor;
import main.java.model.Entities.Pacman;
import main.java.view.GamePanel;

public class Model {
    private boolean onGoing;
    private boolean paused;
    private boolean gameOver;
    private int score, lives;
    private Grid grid;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private List<GameStatisticsListener> listeners = new ArrayList<>();
    private int superModeMoves;
    private GamePanel gamePanel;
    private PacmanMovementStrategy pacmanMovementStrategy;

    public Model() {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = false;
        this.lives = 3;
        this.score = 0;
        this.superModeMoves = 0;
        this.grid = new Grid();
        this.ghosts = new ArrayList<>();
        initializeGhosts();
        this.pacman = new Pacman(grid.getPacmanStartPosition());
        this.grid.setPacman(pacman);
        this.grid.setGhosts(ghosts);
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, this);
    }

    public void addStatisticsListener(GameStatisticsListener lis) {
        listeners.add(lis);
    }

    public void notifyScoreChanged() {
        listeners.forEach(lis -> lis.onScoreChanged(score));
    }

    public void notifyLivesChanged() {
        listeners.forEach(lis -> lis.onLivesChanged(lives));
    }

    public void movePacman(Direction direction) {
        pacmanMovementStrategy.move(direction);
        if (isSuperModeActive()) {
            decrementSuperModeMoves();
        }
    }

    private void initializeGhosts() {
        for (int i = 0; i < GhostColor.values().length; i++) {
            Ghost ghost = new Ghost(grid.getGhostStartPositions().get(i), GhostColor.values()[i]);
            ghost.setMovementStrategy(new GhostChaseStrategy(ghost, grid, this, this.gamePanel));
            ghosts.add(ghost);
        }
    }

    private void resetGhosts() {
        ghosts.clear();
        initializeGhosts();
    }

    public void activateSuperMode(int moves) {
        this.superModeMoves = moves;
        pacman.setSuperMode(true);
        setGhostsScaredMode(true);
        System.out.println("Supermode attivata! Mosse rimanenti: " + superModeMoves);
    }

    public void decrementSuperModeMoves() {
        if (superModeMoves > 0) {
            superModeMoves--;
            System.out.println("Mosse rimanenti in Supermode: " + superModeMoves);
            if (superModeMoves == 0) {
                deactivateSuperMode();
            }
        }
    }

    public void deactivateSuperMode() {
        pacman.setSuperMode(false);
        setGhostsScaredMode(false);
        System.out.println("Supermode disattivata. I fantasmi tornano alla normalità.");
    }

    private void setGhostsScaredMode(boolean scared) {
        for (Ghost ghost : ghosts) {
            ghost.setScaredMode(scared);
        }
    }

    public void eatGhost(Position ghostPosition) {
        Ghost ghostToRemove = null;

        // Trova il fantasma alla posizione data
        for (Ghost ghost : ghosts) {
            if (ghost.getPosition().equals(ghostPosition)) {
                ghostToRemove = ghost;
                break;
            }
        }

        if (ghostToRemove != null) {
            // Rimuovi il fantasma dalla griglia
            grid.removeComponent(ghostToRemove);
            ghosts.remove(ghostToRemove);
            System.out.println("Fantasma alla posizione " + ghostPosition + " è stato mangiato!");

            // Opzionalmente, incrementa il punteggio o attiva altri eventi
            incrementScore(200); // Incremento del punteggio per aver mangiato un fantasma

            // Se si desidera resettare il fantasma o aggiungere nuovi fantasmi, è possibile
            // farlo qui
        } else {
            System.out.println("Nessun fantasma trovato alla posizione " + ghostPosition);
        }
    }

    public void incrementScore(int points) {
        score += points;
        notifyScoreChanged();
    }

    public void resetGame() {
        this.score = 0;
        this.lives = 3;
        this.gameOver = false;
        this.pacman.resetPosition(grid.getPacmanStartPosition());
        resetGhosts();
    }

    public void loseLife() {
        this.lives--;
        if (this.lives <= 0) {
            setGameOver(true);
        } else {
            this.pacman.resetPosition(grid.getPacmanStartPosition());
            notifyLivesChanged();
        }
    }

    public void startStopGame(boolean onGoing) {
        this.onGoing = onGoing;
        this.paused = false;
        this.gameOver = false;
    }

    public void pauseUnpauseGame(boolean paused) {
        this.paused = paused;
        this.onGoing = !paused;
    }

    public void setGameOver(boolean gameOver) {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = gameOver;
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isSuperModeActive() {
        return superModeMoves > 0;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public Ghost getGhost(int index) {
        if (index >= 0 && index < ghosts.size()) {
            return ghosts.get(index);
        } else {
            System.out.println("Indice fantasma non valido: " + index);
            return null;
        }
    }

    public void displayMessage() {
        if (gameOver) {
            System.out.println("Hai perso! Riprovare?");
        } else if (paused) {
            System.out.println("Partita in pausa");
        } else {
            System.out.println("Partita in corso");
        }
    }
}