package my.battleships2.logic;

import my.battleships2.tools.ConsoleHelper;
import my.battleships2.tools.Coordinates;

public class CoordinatesChecker {

    public static Coordinates coordinatesParser(String stringCoordinates) {
        if (stringCoordinates.equals("cheats")) {
            ConsoleHelper.cheatsOnOff = !ConsoleHelper.cheatsOnOff;
            return null;
        }
        if (!stringCoordinates.matches(("(^[a-j]\\d$)|(^[a-j]\\d[v]$)"))) return null;
        int x = (int)stringCoordinates.charAt(0) - 97;
        int y = Character.getNumericValue(stringCoordinates.charAt(1));
        return new Coordinates(x, y);
    }

    public static boolean checkOutOfRange(Coordinates coordinates){
        int x = coordinates.getX();
        int y = coordinates.getY();
        return checkOutOfRange(x , y);
    }

    public static boolean checkOutOfRange(int x , int y){
        if (x < 0 || x > 9 || y < 0 || y > 9) return true;
        else return false;
    }


}
