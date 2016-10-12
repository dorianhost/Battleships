package my.battleships.ships;

import my.battleships.Exeptions.ShipSetupException;
import my.battleships.GameField.Deck;
import my.battleships.GameField.GameField;

public class Ship extends AbstractShip{


    public Ship(ShipTypes type, GameField.Coordinates coordinates, boolean isHorizontal, boolean hidden) {
        super(type, coordinates, isHorizontal, hidden);
        alive = true;
    }

    @Override
    public void hit() {
        alive = false;
        for(Deck currentDeck : getShipDecks())
            alive = alive | currentDeck.isAlive();
    }
}
