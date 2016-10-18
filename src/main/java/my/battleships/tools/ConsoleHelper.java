package my.battleships.tools;

import my.battleships.Exeptions.ExitGameException;
import my.battleships.Exeptions.LoadGameException;
import my.battleships.Exeptions.MainMenuException;
import my.battleships.enums.ConsoleHeaders;
import my.battleships.enums.ShipTypes;
import my.battleships.logic.MenuManager;
import my.battleships.players.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static boolean cheatsOnOff = false;

    public static String readString() throws ExitGameException, LoadGameException, MainMenuException {
        String readString = null;
        try {
            readString = reader.readLine();
        } catch (IOException ignored) {
        }
        if (MenuManager.isSpecialCommand(readString.split(" ")[0]))
            MenuManager.commandExecutor(readString);
        return readString;
    }
    public static void printWellcomeMEssage(){
        System.out.print("" + ConsoleHeaders.LINE
                + ConsoleHeaders.LINE
                + ConsoleHeaders.WELCOME
                + ConsoleHeaders.LINE
                + ConsoleHeaders.LINE);
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
                System.out.print(gameField.getCell(j, i));
            }
            System.out.println("");
        }
        System.out.println("\n");
    }

    public static void printGame(Player humanPlayer, Player computerPlayer, ConsoleHeaders header){
        System.out.print("" + ConsoleHeaders.LINE + header + ConsoleHeaders.LINE +"\n   ");
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
                if (j < 10) System.out.print(humanPlayer.getGameField().getCell(j, i));
                else if (cheatsOnOff) System.out.print(computerPlayer.getGameField().getCell(j - 10, i).getState());
                else System.out.print(computerPlayer.getGameField().getCell(j-10, i));

            }
            System.out.println("");
        }
        System.out.println("\n");
    }

    public static void messageForShipsSetup(ShipTypes shipType){
        System.out.println("\nOk, " + shipType+ " will be at... \n" +
                "(\"d3\" - on \"d3\" will be head, default direction is horizontal, \"d3v\" for vertical)");
    }

    public static void printWinMessage(Player player){
        String secondLine = new StringBuilder(ConsoleHeaders.WINNER_first_part.toString())
                .append(player.getName())
                .append(ConsoleHeaders.WINNER_second_part).toString();
        System.out.print(ConsoleHeaders.LINE);
        for (int i = 0; i <(ConsoleHeaders.LINE.toString().length()/2 - secondLine.length()/2); i++)
            System.out.print(" ");
        System.out.println("" + secondLine + ConsoleHeaders.LINE);
    }

    public static void cheatsOnOff(){
        ConsoleHelper.cheatsOnOff = !ConsoleHelper.cheatsOnOff;
    }
}
