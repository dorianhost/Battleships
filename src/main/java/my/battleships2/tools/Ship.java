package my.battleships2.tools;

import my.battleships2.enums.CellState;
import my.battleships2.enums.ShipTypes;

import java.util.LinkedList;
import java.util.List;

public class Ship {
    private final ShipTypes Type;
    private final List<Deck> ShipDecks;

    public Ship(ShipTypes type, Coordinates coordinates, boolean isHorizontal, boolean hidden){
        Type = type;
        ShipDecks = new LinkedList<>();
        for (int i = 0; i < type.getDecks(); i++)
            ShipDecks.add(new Deck(this, type, isHorizontal ? coordinates.moveX(i) : coordinates.moveY(i), hidden));
    }

    public ShipTypes getType(){
        return Type;
    }

    public List<Deck> getShipDecks() {
        return ShipDecks;
    }

    public boolean isShipAlive(){
        boolean isAlive = false;
        for (int i = 0; i < ShipDecks.size() ; i++)
            isAlive = isAlive | !(ShipDecks.get(i).getState() == CellState.DAMAGED);
        return isAlive;
    }


}
