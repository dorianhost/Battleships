package my.battleships;

import my.battleships.Exeptions.WrongCoordinatesException;
import my.battleships.GameField.GameField;
import my.battleships.Players.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

public class ConsoleHelper implements Observer{
    public static final String letters = " a b c d e f g h i j";
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private Battleships game;

    ConsoleHelper(Battleships game) {
        this.game = game;
    }

    //write message with new line (NL)
    public static void writeMessageNL(Object message) {
        System.out.println(message);
    }
    //write message without new line
    public static void writeMessage(Object message) {
        System.out.print(message);
    }

    public static String readString() {
        String readString = null;
        try {
            readString = bufferedReader.readLine();
        } catch (IOException e) {
            writeMessageNL("FATAL ERROR " + Thread.currentThread().getName() + " : " + e.getMessage());
            e.printStackTrace();
        }
        return readString;
    }

    static void printGame(GameField playersField, GameField computersField) {
        printGame(playersField, computersField, null);
    }
    static void printGame(GameField playersField, GameField computersField, String message) {
        writeMessageNL("\n\n  " + letters + "           " + letters);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                writeMessage(' ');
                if(j == 10) writeMessage("         ");
                if(j==0 || j == 10) writeMessage(i + " ");
                if(j < 10) playersField.getFieldSquare(i, j).print();
                else computersField.getFieldSquare(i, j-10).print();
            }
            writeMessageNL("");
        }
        if (message != null) writeMessageNL("\n" + message);
    }

    public void update(Observable o, Object arg) {
        if(arg!= null && arg.equals("cheats")) {
            game.getComputerPlayer().getGameField().cheats();
            arg = null;
        }
        printGame(game.getHumanPlayer().getGameField(), game.getComputerPlayer().getGameField());
        if (arg != null) System.out.println("\n"+((Player)o).getName() + ", " + arg);
    }

    static void pause(){
        ConsoleHelper.writeMessageNL("Enter - continue");
        ConsoleHelper.readString();

    }

    static boolean askPlayerAboutHisOunShipInstallation(){
        writeMessageNL("Do you want to setup your ships by yourself? Y/y - for \"yes\"");
        if (readString().matches("[Yy]")) return true;
        return false;
    }

    static void winMessageAndCloseTheProgram(String playersName){
        writeMessageNL("GAME OVER! " + playersName +  " WIN!");
        System.exit(0);
    }
}
