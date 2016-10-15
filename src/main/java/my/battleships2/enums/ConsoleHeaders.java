package my.battleships2.enums;

public enum ConsoleHeaders {
    WELCOME("==================================================\n" +
            "==================================================\n" +
            "\t\t\t\tBattleships game\n" +
            "\t\t\t\t\tv0.6.5\n" +
            "==================================================\n" +
            "==================================================\n" ),
    PLACING_SHIPS("==================================================\n" +
            "\t\t\t\tPlacing ships\n" +
            "==================================================\n"),
    SHOOTING("==================================================\n" +
            "\t\t\tSHOOOOOOOOOOOOOOOOOOOOOOT!\n" +
            "==================================================\n");

    String message;

    ConsoleHeaders(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
