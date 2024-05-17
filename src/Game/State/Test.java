/*
*   CLASSE DI TEST PER GLI STATI DI GIOCO
*   Di seguito vi sarà un menu le cui scelte creano istanze dei
*   tre stati di gioco a seconda della scelta.
*/

package Game.State;

import Game.Game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Game partita = new Game();
        Scanner reader = new Scanner(System.in);
        mainMenu(partita, reader);
    }

    private static void mainMenu(Game m, Scanner reader) throws IllegalStateException {
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
                    case 1 -> testGameOnGoing(m, reader);
                    case 2 -> testGamePause(m, reader);
                    case 3 -> testGameOver(m, reader);
                    case 4 -> {
                        onMenu = false;
                        System.exit(0);
                    }
                    default -> System.out.println("Scelta inesistente");
                }
            }catch (InputMismatchException e){
                System.out.println("Scelta non valida");
                reader.next();
            }finally {
                reader.close();
            }
        } while (onMenu);
    }

    private static void testGameOnGoing(Game p, Scanner pad) {
        GameOnGoing state = new GameOnGoing(p);
        String command;

        state.enterState();
        command = pad.next();
        while (true) {
            if (command.equals("p")) {
                state.exitState();
                testGamePause(p, pad);
                break;
            } else if (command.equals("x")) {
                state.exitState();
                testGameOver(p, pad);
                break;
            }
        }
    }

    private static void testGameOver(Game p, Scanner read) {
        GameOver state = new GameOver(p);
        int choice;

        // Entrata nello stato di Game Over
        state.enterState();
        do{
            System.out.println("1. Si");          // riprova
            System.out.println("2. Menu (no)");   // vai al menu

            choice = read.nextInt();
            switch (choice){
                case 1 -> {
                    state.exitState();
                    testGameOnGoing(p, read);
                }
                case 2 -> {
                    state.exitState();
                    mainMenu(p, read);
                }
            }
        }while (choice != 2);
    }

    private static void testGamePause(Game p, Scanner read) {
        GamePause state = new GamePause(p);
        int choice;
        state.enterState();
        do {
            System.out.println("Menu' di pausa");
            System.out.println("1. Riprendi");
            System.out.println("2. Esci al menu'");

            choice = read.nextInt();
            switch (choice) {
                case 1 -> {
                    state.exitState();
                    testGameOnGoing(p, read);
                }
                case 2 -> {
                    state.quit();
                    mainMenu(p, read);
                }
                default ->
                    System.out.println("Scelta inesistente");
            }
        } while (choice != 2);
    }
}
