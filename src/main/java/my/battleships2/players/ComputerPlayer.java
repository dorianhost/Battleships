package my.battleships2.players;

import my.battleships2.enums.ShipTypes;
import my.battleships2.tools.Coordinates;
import my.battleships2.tools.GameField;

import java.util.Random;

public class ComputerPlayer extends Player{
    private Random rand;

    public ComputerPlayer(String name) {
        super(name);
        rand = new Random(System.currentTimeMillis());
    }

    @Override
    public Coordinates getShipPlaceCoordinates(ShipTypes shipType) {
        return new Coordinates(rand.nextInt(10), rand.nextInt(10), rand.nextBoolean());
    }
}
