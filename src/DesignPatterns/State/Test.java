package DesignPatterns.State;

import API.GameState;
import DesignPatterns.State.GameOnGoing;
import DesignPatterns.State.GameOver;
import DesignPatterns.State.GamePause;
import Main.Match;

import java.util.Objects;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        try {
            Match partita = new Match();
            mainMenu(partita);
        } catch (IllegalStateException e) {
            System.err.println(e);
        }

    }

    private static void mainMenu(Match m) throws IllegalStateException {
        int choice;
        Scanner reader = new Scanner(System.in);

        do {
            System.out.println("Menu' principale");
            System.out.println("1. Test Avvia");
            System.out.println("2. Test Pausa");
            System.out.println("3. Test Gameover");
            System.out.println("4. Chiudere");

            choice = reader.nextInt();

            switch (choice) {
                case 1:
                    testGameOnGoing(m);
                    break;
                case 2:
                    testGamePause(m);
                    break;
                case 3:
                    testGameOver(m);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Scelta inesistente");
                    break;
            }
        } while (choice != 4);
        reader.close();
    }

    private static void testGameOnGoing(Match p) {
        GameState stato = new GameOnGoing(p);
        Scanner pad = new Scanner(System.in);
        stato.enterState();
        while(Objects.equals(pad.next(), "p")){
            if (Objects.equals(pad.next(), "p")){
                stato.exitState();
                testGamePause(p);
            }
        }
        if((Objects.equals(pad.next(), "x"))){
            testGameOver(p);
            stato.exitState();
        }
    }

    private static void testGameOver(Match p) {
        // Istanza della partita
        Match match = new Match();

        // Istanza del GameOver con la partita corrente
        GameState state = new GameOver(match);

        // Entrata nello stato di Game Over
        state.enterState();
        state.exitState();
    }

    private static void testGamePause(Match p) {
        Scanner read = new Scanner(System.in);
        GameState stato = new GamePause(p);
        int choice;
        stato.enterState();
        do {
            System.out.println("Menu' di pausa");
            System.out.println("1. Riprendi");
            System.out.println("2. Esci al menu'");

            choice = read.nextInt();

            switch (choice) {
                case 1 -> {
                    stato.exitState();
                    testGameOnGoing(p);
                }
                case 2 -> {
                    stato.exitState();
                    mainMenu(p);
                }
                default ->
                        System.out.println("Scelta inesistente");
            }
        } while (choice != 2);
        read.close();
    }
}
