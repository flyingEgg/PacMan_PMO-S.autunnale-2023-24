package main.java.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.Timer;

import main.java.model.Strategies.GhostChaseStrategy;
import main.java.model.Strategies.GhostFleeStrategy;
import main.java.model.Strategies.GhostScatterStrategy;
import main.java.model.Strategies.PacmanMovementStrategy;
import main.java.model.Movement.Direction;
import main.java.API.GameStatisticsListener;
import main.java.model.Movement.Position;
import main.java.model.Composite.BigDot;
import main.java.model.Composite.SmallDot;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.GhostColor;
import main.java.model.Entities.Pacman;
import main.java.model.Grid.Grid;
import main.java.view.GUI.GamePanelView;

public class Model {
    private static final int MAX_DOTS = 176; // Numero totale dei dots nella mappa
    private static final int CHASE_INTERVAL_MIN = 6000;
    private static final int CHASE_INTERVAL_MAX = 9000;
    private static final int SCATTER_INTERVAL_MIN = 1700;
    private static final int SCATTER_INTERVAL_MAX = 2000;
    private static final String HIGHSCORE_FILE = "highscore.txt";

    private boolean gameOver;
    private boolean onGoing;
    private boolean win;
    private int dotsEaten;
    private int lives;
    private int score;
    private int highScore;
    private int superModeMovesRemaining;
    private Grid grid;
    private GamePanelView gamePanel;
    private PacmanMovementStrategy pacmanMovementStrategy;
    private final Pacman pacman;
    private final List<Ghost> ghosts;
    private final List<GameStatisticsListener> listeners = new ArrayList<>();

    private Timer chaseTimer;
    private Timer scatterTimer;
    private final Random random;

    /**
     * Costruttore della classe Model.
     */
    public Model() {
        this.onGoing = false;
        this.gameOver = false;
        this.dotsEaten = 0;
        this.lives = 3;
        this.score = 0;
        this.highScore = readHighScoreFromFile();
        this.superModeMovesRemaining = 0;
        this.grid = new Grid();
        this.ghosts = new ArrayList<>();
        initializeGhosts();
        this.pacman = new Pacman(grid.getPacmanStartPosition());
        this.grid.setPacman(pacman);
        this.grid.setGhosts(ghosts);
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, this);
        this.random = new Random();
    }

    /**
     * Inizializza il timer per la strategia di inseguimento dei fantasmi.
     */
    public void initChaseTimer() {
        this.chaseTimer = createTimer(CHASE_INTERVAL_MIN, CHASE_INTERVAL_MAX, true);
    }

    /**
     * Inizializza il timer per la strategia di dispersione dei fantasmi.
     */
    public void initScatterTimer() {
        this.scatterTimer = createTimer(SCATTER_INTERVAL_MIN, SCATTER_INTERVAL_MAX, false);
    }

    /**
     * Crea e avvia un timer con intervallo casuale tra min e max.
     *
     * @param min     Il valore minimo dell'intervallo.
     * @param max     Il valore massimo dell'intervallo.
     * @param chasing True se il timer deve cambiare la strategia a chasing, false
     *                se scatter.
     * @return Il timer creato.
     */
    private Timer createTimer(int min, int max, boolean chasing) {
        int interval = this.random.nextInt(max - min + 1) + min;
        Timer timer = new Timer(interval, e -> changeStrategy(chasing));
        timer.start();
        return timer;
    }

    /**
     * Ferma i timer di inseguimento e dispersione.
     */
    private void stopTimers() {
        if (this.scatterTimer != null)
            this.scatterTimer.stop();
        if (this.chaseTimer != null)
            this.chaseTimer.stop();
    }

    /**
     * Cambia la strategia di movimento dei fantasmi tra dispersione e inseguimento.
     *
     * @param chasing True se i fantasmi devono inseguire, false se devono
     *                disperdersi.
     */
    private void changeStrategy(boolean chasing) {
        String newStrat = chasing ? "scatter" : "chase";
        for (Ghost ghost : ghosts) {
            ghost.setMovementStrategy(chasing
                    ? new GhostScatterStrategy(ghost, grid, this, gamePanel, false)
                    : new GhostChaseStrategy(ghost, grid, this, gamePanel, false));
            System.out.println("Strategia " + ghost.getColor() + " cambiata in " + newStrat);
        }
    }

    /**
     * Aggiunge un listener per le statistiche del gioco.
     *
     * @param lis Il listener da aggiungere.
     */
    public void addStatisticsListener(GameStatisticsListener lis) {
        listeners.add(lis);
    }

    /**
     * Notifica i listener che il punteggio è cambiato.
     */
    public void notifyScoreChanged() {
        listeners.forEach(lis -> lis.onScoreChanged(score));
    }

    /**
     * Notifica i listener che il numero di vite è cambiato.
     */
    public void notifyLivesChanged() {
        listeners.forEach(lis -> lis.onLivesChanged(lives));
    }

    /**
     * Notifica i listener che lo stato della modalità Super è cambiato.
     *
     * @param movesRemaining Il numero di mosse rimanenti nella modalità Super.
     */
    public void notifySuperModeStatusChanged(int movesRemaining) {
        listeners.forEach(listener -> listener.onSuperModeStatusChanged(movesRemaining));
    }

    /**
     * Ottiene l'high score attuale.
     *
     * @return l'high score
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Aggiorna l'high score se il punteggio corrente è superiore all'high score
     * salvato.
     *
     * @param score Il punteggio attuale del giocatore
     */
    public void updateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            saveHighScoreToFile(score); // Salva il nuovo high score in un file o database
        }
    }

    /**
     * Carica l'high score da un file.
     *
     * @return L'high score salvato, o 0 se non esiste un file
     */
    public int readHighScoreFromFile() {
        int highScore = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line);
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura dell'high score: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Formato del punteggio non valido: " + e.getMessage());
        }
        return highScore;
    }

    /**
     * Salva l'high score corrente in un file.
     */
    public void saveHighScoreToFile(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGHSCORE_FILE))) {
            writer.write(Integer.toString(score));
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio dell'high score: " + e.getMessage());
        }
    }

    /**
     * Muove Pacman nella direzione specificata.
     *
     * @param direction La direzione in cui muovere Pacman.
     */
    public void movePacman(Direction direction) {
        pacmanMovementStrategy.move(direction); // Let strategy handle movement

        // Gestisci la logica di gioco relativa al movimento
        if (isSuperModeActive()) {
            decrementSuperModeMovesRemaining();
        }
    }

    /**
     * Muove tutti i fantasmi.
     */
    public void moveGhosts() {
        ghosts.forEach(Ghost::moveByStrategy);
    }

    /**
     * Gestisce il consumo dei dots da parte di Pacman.
     */
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

    /**
     * Inizializza i fantasmi con le loro posizioni e strategie di movimento.
     */
    private void initializeGhosts() {
        for (int i = 0; i < GhostColor.values().length; i++) {
            Ghost ghost = new Ghost(grid.getGhostStartPositions().get(i), GhostColor.values()[i]);
            ghost.setMovementStrategy(new GhostChaseStrategy(ghost, grid, this, this.gamePanel, true));
            ghost.getMovementStrategy().setMovStratId(i);
            ghosts.add(ghost);
        }
    }

    /**
     * Reset dei fantasmi.
     */
    private void resetGhosts() {
        ghosts.clear();
        initializeGhosts();
    }

    /**
     * Reset di Pacman.
     */
    private void resetPacman() {
        this.pacman.setPosition(grid.getPacmanStartPosition());
        this.pacmanMovementStrategy = new PacmanMovementStrategy(pacman, grid, this);
    }

    /**
     * Attiva la modalità Super.
     *
     * @param moves Il numero di mosse nella modalità Super.
     */
    public void activateSuperMode(int moves) {
        this.superModeMovesRemaining = moves;
        pacman.setSuperMode(true);
        enableDisableScare(true);
        System.out.println("Supermode attivata! Mosse rimanenti: " + superModeMovesRemaining);
    }

    /**
     * Decrementa il numero di mosse rimanenti nella modalità Super.
     */
    public void decrementSuperModeMovesRemaining() {
        if (superModeMovesRemaining > 0) {
            superModeMovesRemaining--;
            if (superModeMovesRemaining == 0) {
                deactivateSuperMode();
            }
            notifySuperModeStatusChanged(superModeMovesRemaining);
        }
    }

    /**
     * Disattiva la modalità Super.
     */
    private void deactivateSuperMode() {
        pacman.setSuperMode(false);
        enableDisableScare(false);
        System.out.println("Supermode disattivata.");
    }

    /**
     * Abilita o disabilita lo stato di paura per tutti i fantasmi.
     * <p>
     * Se l'argomento {@code scared} è {@code true}, i fantasmi vengono impostati
     * per utilizzare una strategia di movimento di fuga
     * e i timer vengono fermati. Se l'argomento è {@code false}, i fantasmi vengono
     * impostati per utilizzare una strategia di movimento di inseguimento
     * e i timer per la modalità Chase e Scatter vengono avviati.
     *
     * @param scared {@code true} per attivare lo stato di paura, {@code false} per
     *               disattivarlo
     */
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
                this.chaseTimer.start();
                this.scatterTimer.start();
            }
            ghost.setScared(scared);
        }
    }

    /**
     * Gestisce l'azione di Pacman che mangia un fantasma.
     * 
     * @param ghostPosition La posizione del fantasma da mangiare.
     */
    public void eatGhost(Position ghostPosition) {
        Ghost eatenGhost = ghosts.stream().
                filter(ghost -> ghost.getPosition().equals(ghostPosition)).
                findFirst().
                orElse(null);

        // Se un fantasma viene trovato, rilocalizzalo e aggiorna il punteggio
        if (eatenGhost != null) {
            relocateGhost(eatenGhost);
            System.out.println("Fantasma alla posizione " + ghostPosition + " è stato mangiato!");
            incrementScore(200);
        } else {
            System.out.println("Nessun fantasma trovato alla posizione " + ghostPosition);
        }
    }

    /**
     * Rilocalizza il fantasma dato a una nuova posizione disponibile.
     * 
     * @param g Il fantasma da rilocalizzare.
     */
    private void relocateGhost(Ghost g) {
        this.grid.getGhostStartPositions().stream()
                .filter(startPosition -> !isPositionOccupiedByGhost(startPosition))
                .findFirst()
                .ifPresent(g::setPosition);

        g.setMovementStrategy(new GhostChaseStrategy(g,
                grid,
                this,
                gamePanel,
                false));
        g.setScared(false);
    }

    /**
     * Verifica se una posizione è occupata da un fantasma.
     * 
     * @param position La posizione da verificare.
     * @return True se la posizione è occupata da un fantasma, false altrimenti.
     */
    private boolean isPositionOccupiedByGhost(Position position) {
        return ghosts.stream().anyMatch(ghost -> ghost.getPosition().equals(position));
    }

    /**
     * Controlla se il gioco è stato vinto.
     */
    public void winGame() {
        if (this.dotsEaten == MAX_DOTS) {
            this.win = true;
        }
    }

    /**
     * Incrementa il punteggio di gioco.
     * 
     * @param points I punti da aggiungere al punteggio.
     */
    public void incrementScore(int points) {
        score += points;
        notifyScoreChanged();
    }

    /**
     * Resetta lo stato del gioco.
     * 
     * @param win Indica se il gioco è stato vinto o meno.
     */
    public void resetGame(boolean win) {
        if (!win) {
            this.score = 0;
            this.lives = 3;
        }
        this.dotsEaten = 0;
        this.gameOver = false;
        this.onGoing = true;
        this.win = false;
        this.grid = new Grid();
        resetPacman();
        resetGhosts();
    }

    /**
     * Gestisce la perdita di una vita.
     */
    public void loseLife() {
        this.lives--;
        if (this.lives <= 0) {
            setGameOver(true);
        } else {
            this.pacman.setPosition(grid.getPacmanStartPosition());
            IntStream.range(0, ghosts.size())
                    .forEach(i -> ghosts.get(i).setPosition(grid.getGhostStartPositions().get(i)));
            onPacmanKilled();

            notifyLivesChanged();
        }
    }

    /**
     * Gestisce l'evento in cui Pacman viene ucciso.
     */
    private void onPacmanKilled() {
        stopTimers();
        ghosts.forEach(g -> g.setMovementStrategy(new GhostChaseStrategy(g,
                grid,
                this,
                gamePanel,
                true)));
        initChaseTimer();
        initScatterTimer();
    }

    /**
     * Gestisce la coordinata magica per la teletrasportazione.
     * 
     * @param pos La posizione da verificare.
     * @return La posizione di destinazione se esiste, altrimenti un oggetto vuoto.
     */
    public Optional<Position> handleMagicCoords(Position pos) {
        Map<Position, Position> magicCoordsMap = Map.of(
                new Position(9, 0), new Position(9, 17),
                new Position(9, 18), new Position(9, 1));

        return Optional.ofNullable(magicCoordsMap.get(pos));
    }

    /**
     * Avvia o ferma il gioco.
     * 
     * @param onGoing True per avviare il gioco, false per fermarlo.
     */
    public void startStopGame(boolean onGoing) {
        this.onGoing = onGoing;
        this.gameOver = false;
    }

    /**
     * Imposta lo stato del gioco su "Game Over".
     * 
     * @param gameOver True per impostare il gioco su "Game Over", false altrimenti.
     */
    public void setGameOver(boolean gameOver) {
        this.onGoing = false;
        this.gameOver = gameOver;
    }

    /**
     * Verifica se la modalità Supermode è attiva.
     * 
     * @return True se la modalità Supermode è attiva, false altrimenti.
     */
    public boolean isSuperModeActive() {
        return superModeMovesRemaining > 0;
    }

    /**
     * Ottiene il pannello di gioco.
     * 
     * @return Il pannello di gioco.
     */
    public GamePanelView getGamePanel() {
        return gamePanel;
    }

    /**
     * Ottiene il fantasma alla posizione specificata.
     * 
     * @param index L'indice del fantasma.
     * @return Il fantasma all'indice specificato, o null se l'indice non è valido.
     */
    public Ghost getGhost(int index) {
        if (index >= 0 && index < ghosts.size()) {
            return ghosts.get(index);
        } else {
            System.out.println("Indice fantasma non valido: " + index);
            return null;
        }
    }

    // Metodi getter e setter

    /**
     * Restituisce lo stato del gioco, se è finito o meno.
     * 
     * @return True se il gioco è finito, false altrimenti.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Restituisce lo stato del gioco, se è in corso o meno.
     * 
     * @return True se il gioco è in corso, false altrimenti.
     */
    public boolean isOnGoing() {
        return onGoing;
    }

    /**
     * Restituisce lo stato della vittoria del gioco.
     * 
     * @return True se il gioco è vinto, false altrimenti.
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Restituisce il numero di punti raccolti da Pacman.
     * 
     * @return Il numero di punti raccolti.
     */
    public int getDotsEaten() {
        return dotsEaten;
    }

    /**
     * Restituisce il numero di vite rimanenti.
     * 
     * @return Il numero di vite rimanenti.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Restituisce il punteggio attuale del gioco.
     * 
     * @return Il punteggio attuale.
     */
    public int getScore() {
        return score;
    }

    /**
     * Restituisce il numero di mosse rimanenti in Supermode.
     * 
     * @return Il numero di mosse rimanenti in Supermode.
     */
    public int getSuperModeMovesRemaining() {
        return superModeMovesRemaining;
    }

    /**
     * Restituisce la griglia di gioco.
     * 
     * @return La griglia di gioco.
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Imposta il pannello di gioco.
     * 
     * @param gamePanel Il pannello di gioco da impostare.
     */
    public void setGamePanel(GamePanelView gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Imposta il numero di vite rimanenti e notifica il cambiamento.
     * 
     * @param lives Il numero di vite da impostare.
     */
    public void setLives(int lives) {
        this.lives = lives;
        notifyLivesChanged(); // Notifica il cambiamento del numero di vite
    }

    /**
     * Imposta il punteggio del gioco e notifica il cambiamento.
     * 
     * @param score Il punteggio da impostare.
     */
    public void setScore(int score) {
        this.score = score;
        notifyScoreChanged(); // Notifica il cambiamento del punteggio
    }

    /**
     * Restituisce l'istanza di Pacman.
     * 
     * @return L'istanza di Pacman.
     */
    public Pacman getPacman() {
        return pacman;
    }

    /**
     * Restituisce la lista dei fantasmi.
     * 
     * @return La lista dei fantasmi.
     */
    public List<Ghost> getGhosts() {
        return ghosts;
    }

    /**
     * Restituisce il timer per la modalità inseguimento dei fantasmi.
     * 
     * @return Il timer per la modalità inseguimento.
     */
    public Timer getChaseTimer() {
        return chaseTimer;
    }

    /**
     * Restituisce il timer per la modalità dispersione dei fantasmi.
     * 
     * @return Il timer per la modalità dispersione.
     */
    public Timer getScatterTimer() {
        return scatterTimer;
    }

    /**
     * Restituisce l'istanza di Random usata per generare numeri casuali.
     * 
     * @return L'istanza di Random.
     */
    public Random getRandom() {
        return random;
    }
}