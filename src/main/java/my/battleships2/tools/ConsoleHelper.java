package my.battleships2.tools;

import my.battleships2.enums.ConsoleHeaders;
import my.battleships2.enums.ShipTypes;
import my.battleships2.players.Player;

import java.util.Scanner;

public class ConsoleHelper {

    private static Scanner scanner = new Scanner(System.in);
    public static boolean cheatsOnOff = false;

    public static String readString() {
        String readString = null;
        readString = scanner.nextLine();
        return readString;
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

    public static void printGame(Player humanPlayer, Player computerPlayer, ConsoleHeaders header){
        System.out.println(header);
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
                else {
                    if (cheatsOnOff) computerPlayer.getGameField().getCell(i, j-10).changeHide();
                    System.out.print(computerPlayer.getGameField().getCell(i, j-10));
                }
            }
            System.out.println("");
        }
        if (cheatsOnOff) cheatsOnOff = !cheatsOnOff;
        System.out.println("\n");
    }

    public static void messageForShipsSetup(ShipTypes shipType){
        System.out.println("\nOk, " + shipType.getName()+ " will be at... \n" +
                "(\"d3\" - on \"d3\" will be head, default direction is horizontal, \"d3v\" for vertical)");
    }
    public static void winMessage(Player player){
        System.out.println("==================================================\n"
                +"GAME OVER! " + player.getName() + " is a winner!!!"
                +"==================================================\n");
    }
}
