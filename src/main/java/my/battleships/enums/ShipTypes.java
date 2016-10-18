package my.battleships.enums;


public enum ShipTypes {
    FOUR_DECKER(4, 1, "The four-decker"),
    THREE_DECKER(3, 2, "The three-decker"),
    TWO_DECKER(2, 3, "The two-decker"),
    SINGLE_DECKER(1, 4, "The single-decker"),
    MINE(1, 2, "The MINE");

    private int decks;
    private int count;
    private String name;

    ShipTypes(int decks, int count, String name) {
        this.decks = decks;
        this.count = count;
        this.name = name;
    }

    public int getDecks() {
        return decks;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return name;
    }
}
