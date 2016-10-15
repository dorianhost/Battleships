package my.battleships2.tools;

import my.battleships2.enums.CellState;
import my.battleships2.enums.ShipTypes;
import my.battleships2.logic.CoordinatesChecker;
import org.omg.CORBA.PUBLIC_MEMBER;

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
                field[currentDecksY][currentDecksX] = currentDeck;
                for (int i = -1; i <2 ; i++) {
                    for (int j = -1; j <2 ; j++) {
                        if (!CoordinatesChecker.checkOutOfRange(currentDecksY + i, currentDecksX + j)
                                && field[currentDecksY + i][currentDecksX + j].getState() == CellState.EMPTY){
                            field[currentDecksY + i][currentDecksX + j].setState(CellState.NEAR_SHIP);
                            if (currentDeck.isHide()) field[currentDecksY + i][currentDecksX + j].changeHide();
                        }

                    }
                }
            }
            if (shipForAdd.getType() != ShipTypes.MINE)countAliveShips++;
            return true;
        }
        return false;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Cell getCell (Coordinates coordinates){
        return getCell(coordinates.getY(), coordinates.getX());
    }

    public Cell getCell(int x, int y){
        return field[x][y];

    }
    public boolean isRightShotTarget(Coordinates coordinates){
        switch (field[coordinates.getY()][coordinates.getX()].getState()){
            case MISSED:
            case DAMAGED: return false;
            default: return true;
        }
    }

    public boolean isRightMineSacrifice(Coordinates coordinates){
        if(field[coordinates.getY()][coordinates.getX()].state == CellState.DECK) return true;
        else return false;
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

    public void openSpaceAroundShip(Deck deck){
        for(Deck currentDeck : deck.getParentShip().getShipDecks()){
            for (int i = -1; i < 2 ; i++) {
                for (int j = -1; j < 2 ; j++) {
                    int x = currentDeck.getCoordinates().getX() + j;
                    int y = currentDeck.getCoordinates().getY() + i;
                    if(!CoordinatesChecker.checkOutOfRange(x, y) && field[y][x].isHide()) field[y][x].changeHide();
                }
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
                if (CoordinatesChecker.checkOutOfRange(currentDecksY + i, currentDecksX + j)) continue;
                if(field[currentDecksY + i][currentDecksX + j].getState() == CellState.DECK
                        || field[currentDecksY + i][currentDecksX + j].getState() == CellState.MINE)
                    return true;
            }
        }
        return false;
    }

}
