package my.battleships2.logic;

import my.battleships2.tools.Coordinates;

public class CoordinatesChecker {

    public static Coordinates coordinatesParser(String stringCoordinates){
        if (stringCoordinates.equals("cheats")) return new Coordinates(114, 117, false);
        if (!stringCoordinates.matches(("(^[a-j]\\d$)|(^[a-j]\\d[v]$)"))) return null;
        int x = (int)stringCoordinates.charAt(0) - 97;
        int y = Character.getNumericValue(stringCoordinates.charAt(1));
        return stringCoordinates.length() == 2? new Coordinates(x, y) : new Coordinates(x, y, false);
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