/*
*   CLASSE DI TEST PER GLI STATI DI GIOCO
*   Di seguito vi sarà un menu le cui scelte creano istanze dei
*   tre stati di gioco a seconda della scelta.
*/

package DesignPatterns.State;

import API.GameState;
import DesignPatterns.State.GameOnGoing;
import DesignPatterns.State.GameOver;
import DesignPatterns.State.GamePause;
import Main.Match;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Match partita = new Match();
        Scanner reader = new Scanner(System.in);
        mainMenu(partita, reader);
    }

    private static void mainMenu(Match m, Scanner reader) throws IllegalStateException {
        int choice;
        boolean onMenu = true;

        do {
            System.out.println("Menu' principale");
            System.out.println("1. Test Avvia");
            System.out.println("2. Test Pausa");
            System.out.println("3. Test Gameover");
            System.out.println("4. Chiudere");

            try{
                choice = reader.nextInt();

                switch (choice) {
                    case 1:
                        testGameOnGoing(m, reader);
                        break;
                    case 2:
                        testGamePause(m, reader);
                        break;
                    case 3:
                        testGameOver(m);
                        break;
                    case 4:
                        onMenu = false;
                        System.exit(0);
                    default:
                        System.out.println("Scelta inesistente");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("Scelta non valida");
                reader.next();
            }finally {
                reader.close();
            }
        } while (onMenu);
    }

    private static void testGameOnGoing(Match p, Scanner pad) {
        GameState stato = new GameOnGoing(p);
        String command = pad.next();
        stato.enterState();
        while (true) {
            if (command.equals("p")) {
                stato.exitState();
                testGamePause(p, pad);
                break;
            } else if (command.equals("x")) {
                stato.exitState();
                testGameOver(p);
                break;
            }
        }
    }

    private static void testGameOver(Match p) {
        GameState state = new GameOver(p);

        // Entrata nello stato di Game Over
        state.enterState();
        state.exitState();
    }

    private static void testGamePause(Match p, Scanner read) {
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
                    testGameOnGoing(p, read);
                }
                case 2 -> {
                    stato.exitState();
                    mainMenu(p, read);
                }
                default ->
                    System.out.println("Scelta inesistente");
            }
        } while (choice != 2);
    }
}
