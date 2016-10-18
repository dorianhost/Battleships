package my.battleships.enums;

public enum ConsoleHeaders {
    LINE("==================================================\n"),
    WELCOME("                BATTLESHIPS game                  \n" +
            "                     v0.7.1                       \n"),
    PLACING_SHIPS("..................Placing ships...................\n"),
    SHOOTING(".............SHOOOOOOOOOOOOOOOOOOOOOOT!...........\n"),
    WINNER_first_part("GAME OVER! " ),
    WINNER_second_part(" wins!!!\n");

    String message;

    ConsoleHeaders(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
