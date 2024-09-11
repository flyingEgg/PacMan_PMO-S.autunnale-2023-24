package main.java.model;

import java.util.*;

import main.java.controller.Strategies.GhostChaseStrategy;
import main.java.controller.Strategies.GhostFleeStrategy;
import main.java.controller.Strategies.GhostScatterStrategy;
import main.java.controller.Strategies.PacmanMovementStrategy;
import main.java.model.API.Direction;
import main.java.model.API.GameStatisticsListener;
import main.java.model.API.Position;
import main.java.model.Composite.BigDot;
import main.java.model.Composite.SmallDot;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.GhostColor;
import main.java.model.Entities.Pacman;
import main.java.view.GamePanel;

import javax.swing.*;
import javax.swing.Timer;

public class Model {
    private static final int MAX_DOTS = 176; // Numero totale dei dots nella mappa

    private boolean gameOver;
    private boolean onGoing;
    private boolean paused;
    private boolean win;
    private int dotsEaten;
    private int lives;
    private int score;
    private int superModeMoves;
    private Grid grid;
    private GamePanel gamePanel;
    private PacmanMovementStrategy pacmanMovementStrategy;
    private final Pacman pacman;
    private final List<Ghost> ghosts;
    private final List<GameStatisticsListener> listeners = new ArrayList<>();

    private Timer chaseTimer;
    private Timer scatterTimer;
    private Random randTime;

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
        this.randTime = new Random();
        initChaseTimer();
        initScatterTimer();
    }

    private void initChaseTimer() {
        this.chaseTimer = new Timer(this.randTime.nextInt(5000, 8000), e -> changeStrategy(true));
        this.chaseTimer.start();
    }

    private void initScatterTimer() {
        this.scatterTimer = new Timer(this.randTime.nextInt(1700, 2000), e -> changeStrategy(false));
        this.scatterTimer.start();
    }

    private void stopTimers(){
        this.scatterTimer.stop();
        this.chaseTimer.stop();
    }

    private void changeStrategy(boolean chasing){
        String newStrat;

        for (Ghost ghost : ghosts) {
            if(chasing){
                ghost.setMovementStrategy(new GhostScatterStrategy(ghost, grid, this, gamePanel, false));
                newStrat = "scatter";
            } else {
                ghost.setMovementStrategy(new GhostChaseStrategy(ghost, grid, this, gamePanel, false));
                newStrat = "chase";
            }
            System.out.println("Strategia "+ghost.getColor()+" cambiata in "+newStrat);
        }
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

    public void moveGhosts() {
        for (Ghost g : ghosts) {
            g.move();
        }
    }

    public void handleDotEat() {
        Position pacPos = pacman.getPosition();

        // Gestisci SmallDot
        SmallDot smallDot = grid.getSmallDotAtPosition(pacPos);
        if (smallDot != null) {
            this.dotsEaten++;
            smallDot.collect(this);
            grid.removeDotFromMap(pacPos); // Rimuovi il dot dalla mappa
            if (gamePanel != null) {
                gamePanel.repaint();
            }
        }

        // Gestisci BigDot
        BigDot bigDot = grid.getBigDotAtPosition(pacPos);
        if (bigDot != null) {
            this.dotsEaten++;
            bigDot.collect(this);
            grid.removeDotFromMap(pacPos); // Rimuovi il dot dalla mappa
            if (gamePanel != null) {
                gamePanel.repaint();
            }
        }
    }

    private void initializeGhosts() {
        for (int i = 0; i < GhostColor.values().length; i++) {
            Ghost ghost = new Ghost(grid.getGhostStartPositions().get(i), GhostColor.values()[i]);
            ghost.setMovementStrategy(new GhostChaseStrategy(ghost,
                    grid,
                    this,
                    this.gamePanel,
                    true));
            ghosts.add(ghost);
        }
    }

    private void resetGhosts() {
        ghosts.clear();
        initializeGhosts();
    }

    private void resetPacman() {
        this.pacman.resetPosition(grid.getPacmanStartPosition());
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman,
                grid,
                this);
    }

    public void activateSuperMode(int moves) {
        this.superModeMoves = moves;
        pacman.setSuperMode(true);
        enableDisableScare(true);
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
        enableDisableScare(false);
        System.out.println("Supermode disattivata. I fantasmi tornano alla normalità.");
    }

    private void enableDisableScare(boolean scared) {
        for (Ghost ghost : ghosts) {
            if (scared) {
                ghost.setMovementStrategy(new GhostFleeStrategy(ghost,
                        grid,
                        this,
                        gamePanel,
                        false));
                System.out.println("settato flee");
                stopTimers();
            } else {
                ghost.setMovementStrategy(new GhostChaseStrategy(ghost,
                        grid,
                        this,
                        gamePanel,
                        false));
                initChaseTimer();
                initScatterTimer();
            }
            ghost.setScared(scared);
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

    public void winGame() {
        if (this.dotsEaten == MAX_DOTS) {
            this.win = true;
            // resetGame();
        }
    }

    public void incrementScore(int points) {
        score += points;
        notifyScoreChanged();
    }

    public void resetGame(boolean win) {
        if (!win)
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

    public boolean isWin() {
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
