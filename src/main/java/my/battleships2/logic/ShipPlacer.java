package my.battleships2.logic;

import my.battleships2.enums.ShipTypes;
import my.battleships2.players.ComputerPlayer;
import my.battleships2.players.Player;
import my.battleships2.tools.Coordinates;
import my.battleships2.tools.GameField;
import my.battleships2.tools.Ship;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ShipPlacer {

    private static final Logger logger = LogManager.getLogger(ShipPlacer.class);

    public static void placeOwnShips(Player player, boolean hide){
        placeForeignShips(player, player.getGameField(), hide);
    }

    public static void placeForeignShips(Player placer, GameField gameField, boolean isHide){
        boolean showMessages = !(placer instanceof ComputerPlayer);
        for (ShipTypes currentType : ShipTypes.values()) {
            int shipsCountBeforeAdding = gameField.shipsCount();
            do {
                Coordinates coordinates = placer.getShipPlaceCoordinates(currentType);
                boolean successPlacing = false;
                switch (currentType){
                    case FOUR_DECKER:
                    case THREE_DECKER:
                    case TWO_DECKER:
                        successPlacing = gameField.addShip(new Ship(currentType, coordinates,
                                coordinates.isHorizontal(), isHide));
                        break;
                    default:
                        successPlacing = gameField.addShip(new Ship(currentType, coordinates, false, isHide));
                }
                if (!successPlacing && showMessages) logger.info("!!!Wrong setup place!!!!");
            }
            while (gameField.shipsCount() < shipsCountBeforeAdding + currentType.getCount());
        }
    }
}
