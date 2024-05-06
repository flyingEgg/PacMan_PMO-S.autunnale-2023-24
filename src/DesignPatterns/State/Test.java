package DesignPatterns.State;

import API.GameState;
import DesignPatterns.State.GameOnGoing;
import DesignPatterns.State.GameOver;
import DesignPatterns.State.GamePause;
import Main.Match;

import java.util.Scanner;

public class Test {

    public static void main(String[] args){
        Match partita = new Match();
        Scanner scanner = new Scanner(System.in);
        mainMenu(partita, scanner);
    }

    private static void mainMenu(Match m, Scanner reader){
        int choice;

        do{
            System.out.println("Menu' principale");
            System.out.println("1. Test Avvia");
            System.out.println("2. Test Pausa");
            System.out.println("3. Test Gameover");
            System.out.println("4. Chiudere");

            choice = reader.nextInt();

            switch (choice){
                case 1:
                    testGameOnGoing(m);
                    break;
                case 2:
                    testGamePause(m, reader);
                    break;
                case 3:
                    testGameOver(m);
                    break;
                case 4:
                    break;
            }
        }while(choice != 4);
        reader.close();
    }

    private static void testGameOnGoing(Match p){
        GameState stato = new GameOnGoing(p);
        stato.enterState();
        stato.exitState();
    }

    private static void testGameOver(Match p){
        GameState stato = new GameOver(p);
        stato.enterState();
    }

    private static void testGamePause(Match p, Scanner read){
        GameState stato = new GamePause(p);
        int choice;
        do{
            System.out.println("Menu' di pausa");
            System.out.println("1. Riprendi");
            System.out.println("2. Esci");

            choice = read.nextInt();

            switch (choice) {
                case 1 -> {
                    stato.exitState();
                    testGameOnGoing(p);
                }
                case 2 -> stato.exitState(); // qui
            }
        }while(choice != 2);
        read.close();

    }

}
