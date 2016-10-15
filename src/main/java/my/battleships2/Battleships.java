package my.battleships2;

import my.battleships2.enums.ConsoleHeaders;
import my.battleships2.tools.ConsoleHelper;
import my.battleships2.logic.GameManager;
import my.battleships2.players.ComputerPlayer;
import my.battleships2.players.Player;

public class Battleships {
    private final Player humanPlayer;
    private final Player computerPlayer;

    public Battleships() {
        this.humanPlayer = new Player("Player");
        this.computerPlayer = new ComputerPlayer("Computer");
    }

    public static void main(String[] args) {
        Battleships game = new Battleships();
        System.out.println(ConsoleHeaders.WELCOME);
        Player winner = GameManager.runGame(game.humanPlayer, game.computerPlayer);
        ConsoleHelper.winMessage(winner);
    }


}
