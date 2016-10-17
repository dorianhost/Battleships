package my.battleships;

import my.battleships.tools.ConsoleHelper;
import my.battleships.logic.GameManager;
import my.battleships.players.ComputerPlayer;
import my.battleships.players.Player;

public class Battleships {


    public static void main(String[] args) {
        Player winner = GameManager.runGame();
        if (winner != null) ConsoleHelper.printWinMessage(winner);
        else System.out.println("Bye - bye!");
    }


}
