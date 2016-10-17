package my.battleships.logic;

import my.battleships.Exeptions.ExitGameException;
import my.battleships.Exeptions.LoadGameException;
import my.battleships.Exeptions.MainMenuException;
import my.battleships.enums.CellState;
import my.battleships.enums.FiringResult;
import my.battleships.enums.ShipTypes;
import my.battleships.players.ComputerPlayer;
import my.battleships.players.Player;
import my.battleships.tools.*;

public class Shooter {
    public static ShootInfo makeShot(Player shooter, GameField gameField) throws ExitGameException, LoadGameException, MainMenuException {
        boolean showMessage = true;
        if (shooter instanceof ComputerPlayer) showMessage = false;
        FiringResult firingResult = null;
        Coordinates coordinates = null;
        if (shooter instanceof ComputerPlayer) showMessage = false;
        do {
            coordinates = shooter.chooseShotCoordinates();
            if(!gameField.isRightShotTarget(coordinates) && showMessage) System.out.println("Can't shoot there!");
        } while (!gameField.isRightShotTarget(coordinates));
        return makeShotByCoordinates(coordinates, gameField);
    }

    public static ShootInfo makeShotByCoordinates (Coordinates coordinates, GameField gameField){
        FiringResult firingResult = null;
        ShipTypes shipType = null;
        Cell bulletCell = gameField.getCell(coordinates);
        switch (bulletCell.getState()){
            case EMPTY:
            case NEAR_SHIP:
                bulletCell.setState(CellState.MISSED);
                firingResult = FiringResult.MISS;
                break;
            case DECK:
                bulletCell.setState(CellState.DAMAGED);
                if(((Deck)bulletCell).getParentShip().isShipAlive()) {
                    firingResult = FiringResult.HURT;
                    gameField.openSpaceAroundCell((Deck)bulletCell);
                }
                else {
                    gameField.decrementAliveShips();
                    firingResult = FiringResult.DROWNED;
                    gameField.openSpaceAroundShip((Deck)bulletCell);
                    shipType = ((Deck)bulletCell).getParentShip().getType();
                }
                break;
            case MINE:
                firingResult = FiringResult.MINE;
                bulletCell.setState(CellState.DAMAGED);
                gameField.openSpaceAroundShip((Deck)bulletCell);
                shipType = ShipTypes.MINE;
        }
        if (bulletCell.isHide()) bulletCell.changeHide();
        return new ShootInfo(coordinates, firingResult, shipType);
    }

    public static class ShootInfo{
        private Coordinates coordinates;
        private FiringResult firingResult;
        private ShipTypes damagedShipType;

        public ShootInfo(Coordinates coordinates, FiringResult firingResult, ShipTypes shipTypes) {
            this.coordinates = coordinates;
            this.firingResult = firingResult;
            this.damagedShipType = shipTypes;
        }

        public FiringResult getFiringResult() {
            return firingResult;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public ShipTypes getDamagedShipType() {
            return damagedShipType;
        }

        @Override
        public String toString() {
            if (damagedShipType != null) return "" + coordinates + " - " + damagedShipType + firingResult;
            else return "" + coordinates + " - " +  firingResult ;
        }
    }
}
