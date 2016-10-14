package my.battleships.enums;

public enum CellState {
    EMPTY ('.'),
    MISSED ('O'),
    DECK ('S'),
    MINE ('M'),
    DAMAGED('X');

    Character form;

    CellState(Character form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return form.toString();
    }
}
