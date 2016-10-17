package my.battleships.tools;

import my.battleships.enums.CellState;
import my.battleships.enums.ShipTypes;
import my.battleships.logic.CoordinatesChecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameField implements Serializable{
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
                field[currentDeck.getCoordinates().getY()][currentDeck.getCoordinates().getX()] = currentDeck;
                for(Cell nearlestCell : getNearlestCells(currentDeck)){
                    if (nearlestCell.getState() == CellState.EMPTY){
                        nearlestCell.setState(CellState.NEAR_SHIP);
                        if (currentDeck.isHide()) nearlestCell.changeHide();
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
        return getCell(coordinates.getX(), coordinates.getY());
    }

    public Cell getCell(int x, int y){
        return field[y][x];

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

    public int getShipsCount(){
        return ships.size();
    }

    public int getCountAliveShips() {
        return countAliveShips;
    }

    public void decrementAliveShips(){
        countAliveShips--;
    }

    public void openSpaceAroundShip(Deck deck){
        for(Deck currentDeck : deck.getParentShip().getShipDecks()){
            for(Cell currentCell : getNearlestCells(currentDeck))
                if (currentCell.isHide()) currentCell.changeHide();

        }
    }
    public void openSpaceAroundCell(Deck deck) {
        for(Cell currentCell : getCellsCorners(deck))
            if (currentCell.isHide()) currentCell.changeHide();

    }

    public List<Cell> getCellsCorners(Deck deck){
       return getCellsCorners(deck.getCoordinates().getX(), deck.getCoordinates().getY());
    }

    public List<Cell> getCellsCorners(int x, int y){
        List<Cell> cellsArray = new ArrayList<>();
        for (int i = -1; i < 2 ; i++)
            for (int j = -1; j < 2 ; j++) {
                int checkedX = x + j;
                int checkedY = y + i;
                if (i != 0 && j != 0 && !CoordinatesChecker.isCellOutOfRange(checkedX, checkedY))
                    cellsArray.add(field[checkedY][checkedX]);
            }
        return cellsArray;
    }


    public List<Cell> getNearlestCells(Deck deck){
        return getNearlestCells(deck.getCoordinates().getX(), deck.getCoordinates().getY());
    }

    public List<Cell> getNearlestCells(int x, int y){
        List<Cell> cellsArray = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2 ; j++) {
                int checkedX = x + j;
                int checkedY = y + i;
                if(!CoordinatesChecker.isCellOutOfRange(checkedX, checkedY))
                    cellsArray.add(field[checkedY][checkedX]);
            }
        }
        return cellsArray;
    }

    private boolean isRightPlace(Ship checkedShip) {
        for (Deck currentDeck: checkedShip.getShipDecks())
            if (CoordinatesChecker.isCellOutOfRange(currentDeck.getCoordinates())
                    || isNearShip(currentDeck))return false;
        return true;
    }

    private boolean isNearShip (Deck currentDeck){
        for(Cell currentCell : getNearlestCells(currentDeck))
            if (currentCell.getState() == CellState.DECK
                    || currentCell.getState() == CellState.MINE) return true;
        return false;
    }

}
