package my.battleships.logic;

import my.battleships.tools.Coordinates;

public class CoordinatesChecker {

    public static Coordinates coordinatesParser(String stringCoordinates) {
        if (!stringCoordinates.matches(("(^[a-j]\\d$)|(^[a-j]\\d[v]$)"))) return null;
        int x = (int)stringCoordinates.charAt(0) - 97;
        int y = Character.getNumericValue(stringCoordinates.charAt(1));
        return new Coordinates(x, y);
    }

    public static boolean isCellOutOfRange(Coordinates coordinates){
        int x = coordinates.getX();
        int y = coordinates.getY();
        return isCellOutOfRange(x , y);
    }

    public static boolean isCellOutOfRange(int x , int y){
        if (x < 0 || x > 9 || y < 0 || y > 9) return true;
        else return false;
    }


}
