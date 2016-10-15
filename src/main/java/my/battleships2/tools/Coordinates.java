package my.battleships2.tools;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    //this needs for setup new ships with horizontal and vertical direction
    public Coordinates moveX(int dx){
        int newX = x + dx;
        return new Coordinates(newX, y);
    }

    public Coordinates moveY(int dy){
        int newY = y + dy;
        return new Coordinates(x, newY);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;

        Coordinates that = (Coordinates) o;

        if (x != that.x) return false;
        return y == that.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "" + (char)(x + 97) + y;
    }
}
