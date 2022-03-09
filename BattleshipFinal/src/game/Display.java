package game;

import board.Board;
import board.Position;
import board.exceptions.PositionException;
import player.Input;
import player.Player;
import ships.Ship;
import ships.utils.ShipType;

import java.util.Scanner;

public class Display {

    public static void printTitle(){
        System.out.println("" +
                "\n" +
                "██████╗░░█████╗░████████╗████████╗██╗░░░░░███████╗░██████╗██╗░░██╗██╗██████╗░\n" +
                "██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║░░░░░██╔════╝██╔════╝██║░░██║██║██╔══██╗\n" +
                "██████╦╝███████║░░░██║░░░░░░██║░░░██║░░░░░█████╗░░╚█████╗░███████║██║██████╔╝\n" +
                "██╔══██╗██╔══██║░░░██║░░░░░░██║░░░██║░░░░░██╔══╝░░░╚═══██╗██╔══██║██║██╔═══╝░\n" +
                "██████╦╝██║░░██║░░░██║░░░░░░██║░░░███████╗███████╗██████╔╝██║░░██║██║██║░░░░░\n" +
                "╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚══════╝╚═════╝░╚═╝░░╚═╝╚═╝╚═╝░░░░░");
    }

    public static int printMenu(){
        printTitle();
        System.out.println("\n(1) - Play");
        System.out.println("(2) - Simulate game");
        System.out.println("(3) - Rules and legend");
        System.out.println("(0) - Exit\n");
        return Input.readOption(new Scanner(System.in),"reply: ");
    }

    public static void printRules(){
        System.out.println("\nHow to win:");
        System.out.println(
                "- Each player has a battlefield represented by a 10x10 grid (default) on which they place "+ ShipType.sizeAllShips() +" ships, hidden from their opponent.\n" +
                "- The goal of the game is to sink all enemy ships. A ship is sunk when hit once for each space it occupies.\n" +
                "- In other words, a "+ShipType.toNameShip(ShipType.values()[0])+", which occupies "+ShipType.values()[0].getShipLength()+" spaces, is sunk after being hit twice.\n" +
                "- The "+ ShipType.sizeAllShips() +" ships occupy "+ShipType.lengthAllShips()+" total spaces, so the first player to register 25 hits wins.");

        System.out.println("\nGameplay:");
        System.out.println(
                "- \n" +
                "- To play, follow the instructions to set up yours "+ ShipType.sizeAllShips() +" ships in any layout you want (diagonal placements or adjacent to other ships are not allowed).\n" +
                "- To place a ship it is necessary to enter a starting coordinate (A1-J10 for the default 10x10 board) and a direction (vertical or horizontal).\n" +
                "- For example: A1 or B7. Ships cannot overlap or adjoin (attacked) and must remain within boundary limits.\n" +
                "- Once both players have configured their ships, the battle can begin.\n" +
                "- Launch torpedoes at your opponent's ships by guessing the coordinates.\n" +
                "- The rows are represented by the letters A-J and the columns with the numbers 1-10 (table 10x10).\n" +
                "- Valid coordinates include a row followed by a column, e.g. A1, B7, J10, etc.\n" +
                "- You will be notified if you have hit or missed a ship.\n" +
                "- Sink all 8 computer ships to win.");
        System.out.println("\nLegend:");
        for (ShipType type: ShipType.values()){
            System.out.println(
                    "- (" + Board.SHIP + "x"+type.getShipLength() + ")\t: " + ShipType.toNameShip(type));
        }
        System.out.println(
                "- (" + Board.WATER + " )\t: Water\n" +
                        "- (" + Board.SHIP + ")\t: Ship\n" +
                        "- (" + Board.HIT + ")\t: Ship hit\n" +
                        "- (" + Board.MISS + ")\t: Miss\n");

        System.out.print("\nPress any key to continue...");
        new Scanner(System.in).nextLine();
    }

    public static void printCredits(){
        System.out.println("\nThanks for playing!");
    }

    public static void printError(String message){
        System.out.println(message);
    }

    public static void printShot(Player player, Position position, boolean isHit){
        System.out.println("- " + player.getName() + " shot in " + position.toStringEncode(position) + ": " +
                (isHit ? "Hit!" : "Missed!" ));
    }

    public static void printWinner(Player player){
        System.out.println("\n✔ " + player.getName() + " won!" + "\n");
        System.out.print("\nPress any key to continue ......");
        new Scanner(System.in).nextLine();
    }

    public static void printCurrentShip(Ship ship, int numShipLeft){
        System.out.println("☛ " + ship.getName() + " (" +
                ship.toGraphicLength() +
                ") x" + numShipLeft);
    }

    public static void printAdjacentBoard(Player pOne, Player pTwo) throws PositionException {
        System.out.println(toStringAdjacentBoard(pOne, pTwo));
    }

    public static String toStringAdjacentBoard(Player pOne, Player pTwo) throws PositionException {
        Board firstBoard = pOne.getBoard();
        Board secondBoard = pTwo.getBoard().getBoardHideShips();
        String numbers  = "⒈⒉⒊⒋⒌⒍⒎⒏⒐⒑";
        String letters = "ͣᵇͨͩͤᶠᶢͪͥʲ";
        String s = "\n――――――――――――――――――――――――――――――――――\n";
        s += "\n  ";

        for (int i = 0; i < firstBoard.getLength(); i++) s += " " + numbers.charAt(i) + "";
        s += "   ";
        for (int i = 0; i < secondBoard.getLength(); i++) s += " " + numbers.charAt(i) + "";


        s += "\n";
        for (int i = 0; i < firstBoard.getLength(); i++){
            if (i == 5) s += "" + letters.charAt(i) + " "; //f
            else if (i == 6) s += letters.charAt(i) + " "; //g
            else s += letters.charAt(i) + " ";

            for (int j = 0; j < firstBoard.getLength(); j++){
                if (firstBoard.getBoard()[i][j] == Board.WATER) s += "" + Board.WATER + "" + " ";
                else if (firstBoard.getBoard()[i][j] == Board.HIT) s += Board.HIT + " ";
                else if (firstBoard.getBoard()[i][j] == Board.MISS) s += Board.MISS + " ";
                else s += firstBoard.getBoard()[i][j] + " ";
            }

            s += "   ";

            if (i == 5) s += "" + letters.charAt(i) + " "; //f
            else if (i == 6) s += letters.charAt(i) + " "; //g
            else s += letters.charAt(i) + " ";


            for (int j = 0; j < secondBoard.getLength(); j++){
                if (secondBoard.getBoard()[i][j] == Board.WATER) s += "" + Board.WATER + "" + " ";
                else if (secondBoard.getBoard()[i][j] == Board.HIT) s += Board.HIT + " ";
                else if (secondBoard.getBoard()[i][j] == Board.MISS) s += Board.MISS + " ";
                else s += secondBoard.getBoard()[i][j] + " ";
            }

            s += "\n";
        }
        s += "\n――――――――――――――――――――――――――――――――――\n";
        return s;
    }

    public static void printBoard(Board board){
        System.out.println(toStringBoard(board));
    }

    public static String toStringBoard(Board board){
        String numbers  = "⒈⒉⒊⒋⒌⒍⒎⒏⒐⒑";
        String letters = "ͣᵇͨͩͤᶠᶢͪͥʲ";
        String s = "\n ";
        for (int i = 0; i < board.getLength(); i++) s += " " + numbers.charAt(i) + "";
        s += "\n";
        for (int i = 0; i < board.getLength(); i++){
            if (i == 5) s += "" + letters.charAt(i) + " "; //f
            else if (i == 6) s += letters.charAt(i) + " "; //g
            else s += letters.charAt(i) + " ";

            for (int j = 0; j < board.getLength(); j++){
                if (board.getBoard()[i][j] == Board.WATER) s += "" + Board.WATER + "" + " ";
                else if (board.getBoard()[i][j] == Board.HIT) s += Board.HIT + " ";
                else if (board.getBoard()[i][j] == Board.MISS) s += Board.MISS + " ";
                else s += board.getBoard()[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }


}