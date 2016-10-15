package my.battleships2.players;

import my.battleships2.enums.CellState;
import my.battleships2.enums.ShipTypes;
import my.battleships2.logic.ShipPlacer;
import my.battleships2.tools.Coordinates;
import my.battleships2.tools.Deck;
import my.battleships2.tools.Ship;

import java.util.Random;

public class ComputerPlayer extends Player{
    private Random rand;

    public ComputerPlayer(String name) {
        super(name);
        rand = new Random(System.currentTimeMillis());
    }

    @Override
    public ShipPlacer.PlacingInfo chooseShipPlaceCoordinates(ShipTypes shipType) {
        return new ShipPlacer.PlacingInfo(new Coordinates(rand.nextInt(10), rand.nextInt(10)), rand.nextBoolean());
    }

    @Override
    public Coordinates chooseShotCoordinates() {
        return new Coordinates(rand.nextInt(10), rand.nextInt(10));
    }

    @Override
    public Coordinates chooseMineSacrifice() {
        for(Ship currentShip : gameField.getShips()){
            if(currentShip.isShipAlive())
                for(Deck currentDeck : currentShip.getShipDecks())
                    if(currentDeck.getState() == CellState.DECK) return currentDeck.getCoordinates();
        }
        return null;
    }
}
