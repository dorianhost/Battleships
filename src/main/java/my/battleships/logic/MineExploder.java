package my.battleships.logic;

import my.battleships.Exeptions.ExitGameException;
import my.battleships.Exeptions.LoadGameException;
import my.battleships.Exeptions.MainMenuException;
import my.battleships.enums.FiringResult;
import my.battleships.players.Player;
import my.battleships.tools.Coordinates;

public class MineExploder {

    public static Shooter.ShootInfo explodeMine(Player player) throws ExitGameException, LoadGameException, MainMenuException {
        Shooter.ShootInfo info = null;
        System.out.println();
        Coordinates coordinates = null;
        do {
            coordinates = player.chooseMineSacrifice();
            if (!player.getGameField().isRightMineSacrifice(coordinates)) System.out.println("Wrong mine sacrifice!");
        }while (!player.getGameField().isRightMineSacrifice(coordinates));
        info = Shooter.makeShotByCoordinates(coordinates, player.getGameField());
        if (info.getFiringResult() == FiringResult.DROWNED) return new Shooter.ShootInfo(coordinates, FiringResult.MINE_SACRIFICE_DROWN, info.getDamagedShipType());
        else return new Shooter.ShootInfo(coordinates, FiringResult.MINE_SACRIFICE_HURT, info.getDamagedShipType());
    }
}
