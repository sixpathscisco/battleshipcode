
import board.exceptions.PositionException;
import game.BattleshipGame;
import game.Display;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws PositionException {
        Scanner sc = new Scanner(System.in);
        BattleshipGame game;
        String name = "";
        int opt;
        boolean hasName = false;

        try {
            do {
                opt = Display.printMenu();

                switch (opt) {
                    case 1 -> {
                        if (!hasName) {
                            System.out.print("\nEnter your name: ");
                            name = sc.next();
                            hasName = true;
                        }
                        game = new BattleshipGame(name);
                        game.run();
                    }
                    case 2 -> {
                        game = new BattleshipGame();
                        game.run();
                    }
                    case 3 -> {
                        Display.printRules();
                    }
                }
            } while (opt != 0);
        } catch (InputMismatchException e) { }
        Display.printCredits();
    }
}