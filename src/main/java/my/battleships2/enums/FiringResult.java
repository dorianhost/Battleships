package my.battleships2.enums;

public enum FiringResult {
    MISS("MISS"),
    HURT ("you HURT the ship"),
    DROWNED("enemy's ship was DROWNED"),
    MINE(" have struck the MINE"),
    MINE_SACRIFICE(" EXPLODED on the enemy's mine");

    String status;


    FiringResult(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
