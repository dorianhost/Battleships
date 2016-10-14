package my.battleships2.tools;


import my.battleships2.enums.ShipTypes;
import my.battleships2.enums.CellState;

public class Deck extends Cell{
    private final Ship parentShip;
    private final Coordinates coordinates;


    public Deck(Ship parentShip, ShipTypes type, Coordinates coordinates, boolean hide) {
        this.parentShip = parentShip;
        this.coordinates = coordinates;
        this.hide = hide;
        setState(type==ShipTypes.MINE ?  CellState.MINE : CellState.DECK);
    }

    public Ship getParentShip() {
        return parentShip;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }


}
