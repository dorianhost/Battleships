package my.battleships2.logic;

import my.battleships2.enums.CellState;
import my.battleships2.enums.FiringResult;
import my.battleships2.players.ComputerPlayer;
import my.battleships2.players.Player;
import my.battleships2.tools.*;

public class Shooter {
    public static ShootInfo makeShot(Player shooter, GameField gameField){
        boolean showMessage = true;
        if (shooter instanceof ComputerPlayer) showMessage = false;
        FiringResult firingResult = null;
        Coordinates coordinates = null;
        if (shooter instanceof ComputerPlayer) showMessage = false;
        do {
            coordinates = shooter.chooseShotCoordinates();
            if(!gameField.isRightShotTarget(coordinates) && showMessage) System.out.println("Can't shoot there!");
        } while (!gameField.isRightShotTarget(coordinates));
        firingResult = makeShotByCoordinates(coordinates, gameField);
        return new ShootInfo(coordinates, firingResult);
    }

    public static FiringResult makeShotByCoordinates (Coordinates coordinates, GameField gameField){
        FiringResult firingResult = null;
        Cell bulletCell = gameField.getCell(coordinates);
        switch (bulletCell.getState()){
            case EMPTY:
            case NEAR_SHIP:
                bulletCell.setState(CellState.MISSED);
                firingResult = FiringResult.MISS;
                break;
            case DECK:
                bulletCell.setState(CellState.DAMAGED);
                if(((Deck)bulletCell).getParentShip().isShipAlive())
                    firingResult = FiringResult.HURT;
                else {
                    gameField.decrementCountShips();
                    firingResult = FiringResult.DROWNED;
                    gameField.openSpaceAroundShip((Deck)bulletCell);
                }
                break;
            case MINE:
                firingResult = FiringResult.MINE;
                bulletCell.setState(CellState.DAMAGED);
        }
        if (bulletCell.isHide()) bulletCell.changeHide();
        return firingResult;
    }

    public static class ShootInfo{
        private Coordinates coordinates;
        private FiringResult firingResult;

        public ShootInfo(Coordinates coordinates, FiringResult firingResult) {
            this.coordinates = coordinates;
            this.firingResult = firingResult;
        }

        public FiringResult getFiringResult() {
            return firingResult;
        }

        @Override
        public String toString() {
            return "" + coordinates + " - " +  firingResult ;
        }
    }
}
