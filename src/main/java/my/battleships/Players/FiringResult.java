package my.battleships.Players;

public enum FiringResult {
    MISS("miss"),
    HURT ("you HURT the ship!"),
    DROWNED("enemy's ship was DROWNED..."),
    MINE(" have struck the MINE!");

    String status;

    FiringResult(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
