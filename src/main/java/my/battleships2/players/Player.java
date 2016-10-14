package my.battleships2.players;

import my.battleships2.enums.ShipTypes;
import my.battleships2.logic.CoordinatesChecker;
import my.battleships2.tools.ConsoleHelper;
import my.battleships2.tools.Coordinates;
import my.battleships2.tools.GameField;

public class Player {
    private String name;
    private final GameField gameField;

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

    public Coordinates getShipPlaceCoordinates (ShipTypes shipType){
        ConsoleHelper.placeShipHeader();
        ConsoleHelper.pritnGameField(gameField);
        Coordinates coordinates = null;
        do {
            ConsoleHelper.messageForShipsSetup(shipType);
            String buffer = ConsoleHelper.readString();
            coordinates = CoordinatesChecker.coordinatesParser(buffer);
            if(coordinates==null) System.out.println("WRONG COORDINATES!");
        } while (coordinates == null);

        return coordinates;
    }

    public Coordinates getShotCoordinates(){
        Coordinates coordinates = null;
        do{
            System.out.println("Choose your target!");
            String buffer = ConsoleHelper.readString();
            coordinates = CoordinatesChecker.coordinatesParser(buffer);
            if(coordinates==null) System.out.println("WRONG COORDINATES!");
        }
        while (coordinates == null);
        return coordinates;
    }
}
