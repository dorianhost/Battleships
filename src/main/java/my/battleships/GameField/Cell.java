package my.battleships.GameField;

import my.battleships.ConsoleHelper;
import my.battleships.enums.CellState;

public class Cell {
    private CellState form;
    protected boolean alive;

    public Cell() {
        form = CellState.EMPTY;
        alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public void print(){
        ConsoleHelper.writeWhiteMessage(form);
    }

    public boolean hit(){
        if(alive) {
            form = CellState.MISSED;
            alive = false;
            return true;
        }
        else return false;
    }

}
