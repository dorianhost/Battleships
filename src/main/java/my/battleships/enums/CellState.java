package my.battleships.enums;

public enum CellState {
//    EMPTY ((char) 27 + "[32m." +(char)27 + "[0m"),
//    NEAR_SHIP((char) 27 + "[31m." +(char)27 + "[0m"),
//    MISSED ((char) 27 + "[32mO" +(char)27 + "[0m"),
//    DECK ((char) 27 + "[32mS" +(char)27 + "[0m"),
//    MINE ((char) 27 + "[33mM" +(char)27 + "[0m"),
//    DAMAGED((char) 27 + "[31mX" +(char)27 + "[0m");
    EMPTY ("."),
    NEAR_SHIP(" "),
    MISSED ("O"),
    DECK ("S"),
    MINE ("M"),
    DAMAGED("X");

    String form;

    CellState(String form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return form;
    }
}
