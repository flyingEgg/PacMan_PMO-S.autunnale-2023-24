package main.java.model;

import java.util.ArrayList;
import java.util.List;

import main.java.controller.Strategies.PacmanMovementStrategy;
import main.java.model.API.Direction;
import main.java.model.API.GameStatisticsListener;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.GhostColor;
import main.java.model.Entities.Pacman;
import main.java.model.Exceptions.IllegalEntityMovementException;
import main.java.controller.Strategies.GhostChaseStrategy;
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
        this.grid = new Grid();
        this.score = 0;
        initializeGame();
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, this);
    }

    private void initializeGame() {
        this.pacman = new Pacman(this.grid.getPacmanStartPosition().getX(), this.grid.getPacmanStartPosition().getY());
        this.ghosts = new ArrayList<>();
        initializeGhosts(); // Inizializza i fantasmi
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void addStatisticsListener(GameStatisticsListener lis) {
        listeners.add(lis);
    }

    public void notifyScoreChanged() {
        listeners.stream().forEach(lis -> lis.onScoreChanged(score));
    }

    public void notifyLivesChanged() {
        listeners.stream().forEach(lis -> lis.onLivesChanged(lives));
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

    public void incrementScore(int points) {
        score += points;
        notifyScoreChanged();
    }

    public int getScore() {
        return score;
    }

    public void resetGame() {
        this.score = 0;
        this.lives = 3;
        this.gameOver = false;
        this.pacman = new Pacman(this.grid.getPacmanStartPosition().getX(), this.grid.getPacmanStartPosition().getY());
        initializeGhosts();
    }

    public void loseLife() {
        this.lives--;
        if (this.lives <= 0) {
            setGameOver(true);
        }

        this.pacman = new Pacman(this.grid.getPacmanStartPosition().getX(), this.grid.getPacmanStartPosition().getY());
        notifyLivesChanged();
    }

    public int getLives() {
        return lives;
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

    public int getSuperModeMoves() {
        return superModeMoves;
    }

    public void setSuperModeMoves(int moves) {
        this.superModeMoves = moves;
    }

    public void decrementSuperModeMoves() {
        if (superModeMoves > 0) {
            superModeMoves--;
        }
    }

    public boolean isSuperModeActive() {
        return superModeMoves > 0;
    }

    public void eatGhost(Position ghostPosition) {
        Ghost ghost = ghosts.stream().filter(g -> g.getPosition().equals(ghostPosition)).findFirst().orElse(null);
        if (ghost != null) {
            incrementScore(200); // Aggiungi punti per ogni fantasma mangiato
            ghosts.remove(ghost);
            grid.removeComponent(ghost);
        }
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Pacman getPacman() {
        return pacman;
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

    public void movePacman(Direction direction) throws IllegalEntityMovementException {
        pacmanMovementStrategy.move(direction);
    }

    private void initializeGhosts() {
        for (int i = 0; i < GhostColor.values().length; i++) {
            int x = this.grid.getGhostStartPositions().get(i).getX();
            int y = this.grid.getGhostStartPositions().get(i).getY();
            Ghost ghost = new Ghost(x, y, GhostColor.values()[i]);

            // Assegna la strategia di movimento, ad esempio, in modalità normale:
            ghost.setMovementStrategy(new GhostChaseStrategy(ghost, grid, this, this.gamePanel));

            this.grid.addComponent(ghost);
            this.ghosts.add(ghost); // Aggiungi il fantasma alla lista dei fantasmi
        }
    }
}