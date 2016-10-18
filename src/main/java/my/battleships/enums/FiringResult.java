package my.battleships.enums;

public enum FiringResult {
    MISS("MISS"),
    DAMAGE (" ship was DAMAGED"),
    DROWNED(" ship was DROWNED"),
    MINE(" explosion!"),
    MINE_SACRIFICE_HURT(" HURT on the enemy's mine"),
    MINE_SACRIFICE_DROWN(" DROWNED on the enemy's mine");

    String status;


    FiringResult(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
