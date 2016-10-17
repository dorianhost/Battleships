package my.battleships.players;

import my.battleships.Exeptions.ExitGameException;
import my.battleships.Exeptions.LoadGameException;
import my.battleships.Exeptions.MainMenuException;
import my.battleships.enums.ConsoleHeaders;
import my.battleships.enums.ShipTypes;
import my.battleships.logic.CoordinatesChecker;
import my.battleships.logic.ShipPlacer;
import my.battleships.tools.ConsoleHelper;
import my.battleships.tools.Coordinates;
import my.battleships.tools.GameField;

import java.io.Serializable;


public class Player implements Serializable{
    private String name;
    protected final GameField gameField;

    public Player(String name) {
        this.name = name;
        this.gameField = new GameField();
    }

    public String getName() {
        return name;
    }

    public GameField getGameField() {
        return gameField;
    }

    public ShipPlacer.PlacingInfo chooseShipPlaceCoordinates(ShipTypes shipType) throws LoadGameException, ExitGameException, MainMenuException {
        System.out.println("" + ConsoleHeaders.LINE + ConsoleHeaders.PLACING_SHIPS + ConsoleHeaders.LINE);
        ConsoleHelper.pritnGameField(gameField);
        Coordinates coordinates = null;
        String buffer = null;
        do {
            ConsoleHelper.messageForShipsSetup(shipType);
            buffer = ConsoleHelper.readString();
            coordinates = CoordinatesChecker.coordinatesParser(buffer);
            if(coordinates==null) System.out.println("WRONG SHIP COORDINATES!");
        } while (coordinates == null);

        return new ShipPlacer.PlacingInfo(coordinates, buffer.length() == 2);
    }

    public Coordinates chooseShotCoordinates() throws LoadGameException, ExitGameException, MainMenuException {
        Coordinates coordinates = null;
        do{
            System.out.println("Choose your target!");
            String buffer = ConsoleHelper.readString();
            coordinates = CoordinatesChecker.coordinatesParser(buffer);
            if(coordinates==null) System.out.println("WRONG SHOOT COORDINATES!");
        }
        while (coordinates == null);
        return coordinates;
    }

    public Coordinates chooseMineSacrifice() throws LoadGameException, ExitGameException, MainMenuException {
        Coordinates coordinates = null;
        do{
            System.out.println("Choose ship or ship's deck which struck the mine:");
            String buffer = ConsoleHelper.readString();
            coordinates = CoordinatesChecker.coordinatesParser(buffer);
            if(coordinates==null) System.out.println("WRONG SHOOT COORDINATES!");
        }
        while (coordinates == null);
        return coordinates;
    }


}
