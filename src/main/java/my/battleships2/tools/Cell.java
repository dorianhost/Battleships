package my.battleships2.tools;

import my.battleships2.enums.CellState;

public class Cell {
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
        if (hide&&(state == CellState.DECK || state==CellState.MINE || state ==CellState.NEAR_SHIP)) return CellState.EMPTY.toString();
        return state.toString();
    }
}
