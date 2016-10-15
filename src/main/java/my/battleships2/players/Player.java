package my.battleships2.players;

import my.battleships2.enums.ConsoleHeaders;
import my.battleships2.enums.ShipTypes;
import my.battleships2.logic.CoordinatesChecker;
import my.battleships2.logic.ShipPlacer;
import my.battleships2.tools.ConsoleHelper;
import my.battleships2.tools.Coordinates;
import my.battleships2.tools.GameField;


import java.util.LinkedHashMap;
import java.util.Map;

public class Player {
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

    public ShipPlacer.PlacingInfo chooseShipPlaceCoordinates(ShipTypes shipType){
        System.out.println(ConsoleHeaders.PLACING_SHIPS);
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

    public Coordinates chooseShotCoordinates(){
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

    public Coordinates chooseMineSacrifice(){
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
