package my.battleships.logic;

import my.battleships.Exeptions.ExitGameException;
import my.battleships.Exeptions.LoadGameException;
import my.battleships.Exeptions.MainMenuException;
import my.battleships.tools.ConsoleHelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MenuManager {
    private static Set<String> commands = new HashSet<>();

    static {
        Collections.addAll(commands,
                "save",
                "load",
                "cheats",
                "showfield",
                "main",
                "exit");
    }

    public static boolean isSpecialCommand(String command){
        return command.contains(command.toLowerCase());
    }

    public static void commandExecutor(String command) throws LoadGameException, MainMenuException, ExitGameException {
        String[] parsing = command.toLowerCase().trim().split(" ");
        switch (parsing[0]){
            case "cheats":
                ConsoleHelper.cheatsOnOff();
                break;
            case "save":
                if(parsing.length == 1) break;
                SaveLoadManager.saveIntoFile(parsing[1]);
                break;
            case "showfield":
                GameManager.changeCompVision();
                break;
            case "load":
                throw new LoadGameException();
            case "main":
                throw new MainMenuException();
            case "exit":
                throw new ExitGameException();
        }
    }

    public static int MainMenu(){
        System.out.println("                    MAIN MENU:\n" +
                           "                 1. New game\n" +
                           "                 2. Resume game\n"+
                           "                 3. Load save\n" +
                           "                 4. Exit\n\n" +
                "Note: The \"cheats\" command reveals computer's gamefield for player\n" +
                "Note: The \"save <filename>\" saves game in file <filename>.bsh\n" +
                "Note: The \"load\" command loads game from file <filename>.bsh\n" +
                "Note: The \"showfield\" command reveals computer's vision of player's gamefield \n" +
                "Note: The \"main\" command returns to main menu\n"+
                "Note: The \"exit\" command is for exit the app\nNow, enter 1,2,3 or 4");
        boolean isCorrect = false;
        String choice = null;
        do {
            try {
                choice = ConsoleHelper.readString();
                isCorrect = choice.matches("^[1234]$");
                if (!isCorrect) System.out.println("Wrong choice!");
            } catch (Exception ignored) {
                System.out.println("Wrong choice!");
            }
        } while (!isCorrect);
        return Integer.parseInt(choice);
    }
}
