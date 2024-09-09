package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import main.java.controller.Strategies.GhostChaseStrategy;
import main.java.controller.Strategies.GhostFleeStrategy;
import main.java.controller.Strategies.PacmanMovementStrategy;
import main.java.model.API.Direction;
import main.java.model.API.GameStatisticsListener;
import main.java.model.API.Position;
import main.java.model.Composite.Dot;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.GhostColor;
import main.java.model.Entities.Pacman;
import main.java.view.GamePanel;

public class Model {
    private static final int MAX_DOTS = 176;  // Numero totale dei dots nella mappa
    private boolean onGoing;
    private boolean paused;
    private boolean gameOver;
    private boolean win;
    private int score, lives, dotsEaten;
    private Grid grid;
    private final Pacman pacman;
    private final List<Ghost> ghosts;
    private final List<GameStatisticsListener> listeners = new ArrayList<>();
    private int superModeMoves;
    private GamePanel gamePanel;
    private PacmanMovementStrategy pacmanMovementStrategy;

    public Model() {
        this.onGoing = false;
        this.paused = false;
        this.gameOver = false;
        this.dotsEaten = 0;
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
        pacmanMovementStrategy.move(direction); // Let strategy handle movement

        // Handle game logic related to movement
        if (isSuperModeActive()) {
            decrementSuperModeMoves();
        }
    }

    public void handleSmallDotEat(){
        Position pacPos = pacman.getPosition();
        Dot smallDot = grid.getSmallDotAtPosition(pacPos);

        handleDotEat(smallDot);
    }

    public void handleBigDotEat(){
        Position pacPos = pacman.getPosition();
        Dot bigDot = grid.getBigDotAtPosition(pacPos);

        handleDotEat(bigDot);
    }

    private void handleDotEat(Dot dot){
        if(dot != null && !dot.isEaten()){
            this.dotsEaten++;
            System.out.println(dotsEaten);
            dot.collect(this);
            if(gamePanel != null){
                gamePanel.repaint();
            }
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

    private void resetPacman() {
        this.pacman.resetPosition(grid.getPacmanStartPosition());
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, this);
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
            if (scared) {
                ghost.setMovementStrategy(new GhostFleeStrategy(ghost, grid, this, gamePanel));
            } else {
                ghost.setMovementStrategy(new GhostChaseStrategy(ghost, grid, this, gamePanel));
            }
        }
    }

    public void eatGhost(Position ghostPosition) {
        Ghost ghostToRemove = null;

        for (Ghost ghost : ghosts) {
            if (ghost.getPosition().equals(ghostPosition)) {
                ghostToRemove = ghost;
                break;
            }
        }

        if (ghostToRemove != null) {
            grid.removeComponent(ghostToRemove);
            ghosts.remove(ghostToRemove);
            System.out.println("Fantasma alla posizione " + ghostPosition + " è stato mangiato!");
            incrementScore(200);
        } else {
            System.out.println("Nessun fantasma trovato alla posizione " + ghostPosition);
        }
    }

    public void winGame(){
        if(this.dotsEaten == MAX_DOTS){
            this.win = true;
            //resetGame();
        }
    }

    public void incrementScore(int points) {
        score += points;
        notifyScoreChanged();
    }

    public void resetGame(boolean win) {
        if(!win)
            this.score = 0;
        this.lives = 3;
        this.dotsEaten = 0;
        this.gameOver = false;
        this.onGoing = true;
        this.win = false;
        this.grid = new Grid();
        resetPacman();
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

    public Optional<Position> handleMagicCoords(Position pos) {
        Map<Position, Position> magicCoordsMap = Map.of(
                new Position(9, 0), new Position(9, 17),
                new Position(9, 18), new Position(9, 1));

        return Optional.ofNullable(magicCoordsMap.get(pos));
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

    public boolean isWin(){
        return win;
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
