package my.battleships2.logic;

import my.battleships2.enums.FiringResult;
import my.battleships2.players.Player;
import my.battleships2.tools.ConsoleHelper;

public class GameManager {

    public static Player runGame(Player humanPlayer, Player computerPlayer){
        ConsoleHelper.wellcomeMessage();
        ConsoleHelper.printGame(humanPlayer, computerPlayer);
        System.out.println("Do you want to setup your ships by yourself? [Yy/Nn] ");
        if(ConsoleHelper.readString().matches("[Yy].*"))
            ShipPlacer.placeOwnShips(humanPlayer, false);
        else ShipPlacer.placeForeignShips(computerPlayer, humanPlayer.getGameField(), false);
        ShipPlacer.placeOwnShips(computerPlayer, true);

        ConsoleHelper.shootingHeader();
        ConsoleHelper.printGame(humanPlayer, computerPlayer);
        FiringResult firingResult = null;
        for (int i = 0; i <100 ; i++) {
            firingResult = Shooter.makeShot(humanPlayer, computerPlayer.getGameField());
            ConsoleHelper.printGame(humanPlayer, computerPlayer);
        }
        return null;
    }
}
