package my.battleships2.tools;

import my.battleships2.enums.CellState;
import my.battleships2.enums.ShipTypes;
import my.battleships2.logic.CoordinatesChecker;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private final Cell[][] field;
    private final List<Ship> ships;
    private int countAliveShips;

    public GameField(){
        field = new Cell[10][10];
        ships = new ArrayList<>();
        for (int i = 0; i < 10 ; i++)
            for (int j = 0; j <10 ; j++)
                field[i][j] = new Cell();
        countAliveShips = 0;
    }

    public boolean addShip(Ship shipForAdd){
        if (isRightPlace(shipForAdd)){
            ships.add(shipForAdd);
            for (Deck currentDeck : shipForAdd.getShipDecks()) {
                int currentDecksX = currentDeck.getCoordinates().getX();
                int currentDecksY = currentDeck.getCoordinates().getY();
                field[currentDecksX][currentDecksY] = currentDeck;
                for (int i = -1; i <2 ; i++) {
                    for (int j = -1; j <2 ; j++) {
                        if (!CoordinatesChecker.checkOutOfRange(currentDecksX + i, currentDecksY + j)
                                && field[currentDecksX + i][currentDecksY + j].getState() == CellState.EMPTY){
                            field[currentDecksX + i][currentDecksY + j].setState(CellState.NEAR_SHIP);
                            if (currentDeck.isHide()) field[currentDecksX + i][currentDecksY + j].changeHide();
                        }

                    }
                }
            }
            if (shipForAdd.getType() != ShipTypes.MINE)countAliveShips++;
            return true;
        }
        return false;
    }

    public Cell getCell (Coordinates coordinates){
        return getCell(coordinates.getY(), coordinates.getX());
    }

    public Cell getCell(int x, int y){
        return field[x][y];

    }

    public int shipsCount (){
        return ships.size();
    }

    public int getCountAliveShips() {
        return countAliveShips;
    }

    public void decrementCountShips(){
        countAliveShips--;
    }

    public void cheatsOnOff(){
        for (int i = 0; i <field.length ; i++) {
            for (int j = 0; j <field.length ; j++) {
                field[i][j].changeHide();
            }

        }


    }

    private boolean isRightPlace(Ship checkedShip) {
        for (Deck currentDeck: checkedShip.getShipDecks())
            if (CoordinatesChecker.checkOutOfRange(currentDeck.getCoordinates())
                    || isNearShip(currentDeck))return false;
        return true;
    }

    private boolean isNearShip (Deck currentDeck){
        int currentDecksX = currentDeck.getCoordinates().getX();
        int currentDecksY = currentDeck.getCoordinates().getY();
        for (int i = -1; i <2 ; i++) {
            for (int j = -1; j <2 ; j++) {
                if (CoordinatesChecker.checkOutOfRange(currentDecksX + i, currentDecksY + j)) continue;
                if(field[currentDecksX + i][currentDecksY + j].getState() == CellState.DECK
                        || field[currentDecksX + i][currentDecksY + j].getState() == CellState.MINE)
                    return true;
            }
        }
        return false;
    }

}
