package my.battleships.logic;

import my.battleships.Exeptions.ExitGameException;
import my.battleships.Exeptions.LoadGameException;
import my.battleships.Exeptions.MainMenuException;
import my.battleships.enums.ShipTypes;
import my.battleships.players.ComputerPlayer;
import my.battleships.players.Player;
import my.battleships.tools.Coordinates;
import my.battleships.tools.GameField;
import my.battleships.tools.Ship;

public class ShipPlacer {

    public static void placeOwnShips(Player player, boolean hide) throws LoadGameException, ExitGameException, MainMenuException {
        placeAnotherShips(player, player.getGameField(), hide);
    }

    public static void placeAnotherShips(Player placer, GameField gameField, boolean isHide) throws ExitGameException, LoadGameException, MainMenuException {
        boolean showMessages = !(placer instanceof ComputerPlayer);
        for (ShipTypes currentType : ShipTypes.values()) {
            int shipsCountBeforeAdding = gameField.getShipsCount();
            do {
                PlacingInfo shipCoord  = placer.chooseShipPlaceCoordinates(currentType);
                boolean successPlacing = false;
                switch (currentType){
                    case FOUR_DECKER:
                    case THREE_DECKER:
                    case TWO_DECKER:
                        successPlacing = gameField.addShip(new Ship(currentType, shipCoord.coordinates,
                                shipCoord.isHorizontal, isHide));
                        break;
                    default:
                        successPlacing = gameField.addShip(new Ship(currentType, shipCoord.coordinates, false, isHide));
                }
                if (!successPlacing && showMessages) System.out.println("!!!Wrong setup place!!!!");
            }
            while (gameField.getShipsCount() < shipsCountBeforeAdding + currentType.getCount());
        }
    }

    public static class PlacingInfo {
        private Coordinates coordinates;
        private boolean isHorizontal;

        public PlacingInfo(Coordinates coordinates, boolean isHorizontal) {
            this.coordinates = coordinates;
            this.isHorizontal = isHorizontal;
        }
    }
}
