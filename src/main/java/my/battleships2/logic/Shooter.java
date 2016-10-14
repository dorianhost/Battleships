package my.battleships2.logic;

import my.battleships2.enums.CellState;
import my.battleships2.enums.FiringResult;
import my.battleships2.players.ComputerPlayer;
import my.battleships2.players.Player;
import my.battleships2.tools.*;

public class Shooter {
    public static FiringResult makeShot(Player shooter, GameField gameField){
        boolean showMessage = true;
        if (shooter instanceof ComputerPlayer) showMessage = false;
        Cell bulletCell = null;
        do {
            Coordinates coordinates = shooter.getShotCoordinates();
            if(coordinates.equals(new Coordinates(114,117,false))) {
                gameField.cheatsOnOff();
                return null;
            }
            bulletCell = gameField.getCell(coordinates);
            if(bulletCell == null && showMessage) System.out.println("WRONG COORDINATES!");
        } while (bulletCell == null);
        switch (bulletCell.getState()){
            case EMPTY:
            case NEAR_SHIP:
                bulletCell.setState(CellState.MISSED);
                if (bulletCell.isHide()) bulletCell.changeHide();
                return FiringResult.MISS;

            case DECK:
                bulletCell.setState(CellState.DAMAGED);
                if (bulletCell.isHide()) bulletCell.changeHide();
                if(((Deck)bulletCell).getParentShip().isShipAlive()) return FiringResult.HURT;
                else {
                    gameField.decrementCountShips();
                    return FiringResult.DROWNED;
                }
            case MINE:

        }
        return null;
    }
}
