package my.battleships2.logic;

import my.battleships2.enums.FiringResult;
import my.battleships2.players.Player;
import my.battleships2.tools.Coordinates;

public class MineExploder {

    public static Shooter.ShootInfo explodeMine(Player player){
        System.out.println();
        Coordinates coordinates = null;
        do {

            System.out.println("Choose ship or ship's deck which struck the mine:");
            coordinates = player.chooseMineSacrifice();
            if (!player.getGameField().isRightMineSacrifice(coordinates)) System.out.println("Wrong mine sacrifice!");
        }while (!player.getGameField().isRightMineSacrifice(coordinates));
        Shooter.makeShotByCoordinates(coordinates, player.getGameField());
        return new Shooter.ShootInfo(coordinates, FiringResult.MINE_SACRIFICE);
    }
}
