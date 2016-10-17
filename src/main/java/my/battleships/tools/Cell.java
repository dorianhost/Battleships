package my.battleships.tools;

import my.battleships.enums.CellState;

import java.io.Serializable;

public class Cell implements Serializable{
    protected CellState state;
    protected boolean hide;

    public Cell() {
        state = CellState.EMPTY;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public void changeHide(){
        hide = !hide;
    }

    public boolean isHide() {
        return hide;
    }

    @Override
    public String toString() {
        if (hide&&(state == CellState.DECK || state==CellState.MINE || state==CellState.NEAR_SHIP)) return CellState.EMPTY.toString();
        else return state.toString();
    }
}
