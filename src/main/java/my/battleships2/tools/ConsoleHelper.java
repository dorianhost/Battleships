package my.battleships2.tools;

import my.battleships2.enums.ShipTypes;
import my.battleships2.players.Player;

import java.util.Scanner;

public class ConsoleHelper {

    private static Scanner scanner = new Scanner(System.in);

    public static String readString() {
        String readString = null;
        readString = scanner.nextLine();
        return readString;
    }

    public static void wellcomeMessage(){
        System.out.println("==================================================\n" +
                "==================================================\n" +
                "\t\t\t\tBattleships game\n" +
                "\t\t\t\t\tv0.6.5\n" +
                "==================================================\n" +
                "==================================================\n" );
    }

    public static void placeShipHeader(){
        System.out.println("==================================================\n" +
                "\t\t\t\tPlacing ships\n" +
                "==================================================\n");
    }

    public static void shootingHeader(){
        System.out.println("==================================================\n" +
                "\t\t\tSHOOOOOOOOOOOOOOOOOOOOOOT!\n" +
                "==================================================\n");
    }

    public static void pritnGameField(GameField gameField){
        System.out.print("   ");
        for (int i = 0; i < 10 ; i++)
            System.out.print((char)(97+i) + " ");
        System.out.println("");
        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < 10 ; j++) {
                System.out.print(" ");
                if (j == 0) System.out.print(i + " ");
                System.out.print(gameField.getCell(i, j));
            }
            System.out.println("");
        }
        System.out.println("\n");
    }

    public static void printGame(Player humanPlayer, Player computerPlayer){
        System.out.print("   ");
        for (int i = 0, j = 0; i < 20 ; i++, j++) {
            if(i == 10) {
                System.out.print("\t\t  ");
                j = 0;
            }
            System.out.print((char)(97+j) + " ");
        }
        System.out.println("");
        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < 20 ; j++) {
                System.out.print(" ");
                if (j == 0) System.out.print(i + " ");
                if (j == 10) System.out.print("\t\t" + i + " ");
                if (j < 10) System.out.print(humanPlayer.getGameField().getCell(i, j));
                else System.out.print(computerPlayer.getGameField().getCell(i, j-10));
            }
            System.out.println("");
        }
        System.out.println("\n");
    }

    public static void messageForShipsSetup(ShipTypes shipType){
        System.out.println("\nOk, " + shipType.getName()+ " will be at... \n" +
                "(\"d3\" - on \"d3\" will be head, default direction is horizontal, \"d3v\" for vertical)");
    }
    public static void winMessage(Player player){

    }
}
