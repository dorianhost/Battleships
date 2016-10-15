package my.battleships2.logic;

import my.battleships2.enums.ConsoleHeaders;
import my.battleships2.enums.FiringResult;
import my.battleships2.players.Player;
import my.battleships2.tools.ConsoleHelper;

public class GameManager {

    public static Player runGame(Player humanPlayer, Player computerPlayer){
        ConsoleHelper.printGame(humanPlayer, computerPlayer, ConsoleHeaders.PLACING_SHIPS);
        System.out.println("Do you want to setup your ships by yourself? [Yy/Nn] ");
        if(ConsoleHelper.readString().matches("[Yy].*"))
            ShipPlacer.placeOwnShips(humanPlayer, false);
        else ShipPlacer.placeAnotherShips(computerPlayer, humanPlayer.getGameField(), false);
        ShipPlacer.placeOwnShips(computerPlayer, true);

        ConsoleHelper.printGame(humanPlayer, computerPlayer,ConsoleHeaders.SHOOTING);
        Shooter.ShootInfo playersShootInfo = null;
        Shooter.ShootInfo computersShootInfo = null;
        boolean isWinner = false;
        Player winner = null;
        StringBuilder computersShots = new StringBuilder();
        do{
            do {
                playersShootInfo = Shooter.makeShot(humanPlayer, computerPlayer.getGameField());
                if(computerPlayer.getGameField().getCountAliveShips() == 0) {
                    isWinner = true;
                    winner = humanPlayer;
                    break;
                }
                if (playersShootInfo == null) continue;
                if(playersShootInfo.getFiringResult() == FiringResult.DROWNED
                        || playersShootInfo.getFiringResult() == FiringResult.HURT){
                    ConsoleHelper.printGame(humanPlayer, computerPlayer, ConsoleHeaders.SHOOTING);
                    System.out.println(humanPlayer.getName() +": " + playersShootInfo + ", shot again");
                }
                else if (playersShootInfo.getFiringResult() == FiringResult.MINE){
                    ConsoleHelper.printGame(humanPlayer, computerPlayer, ConsoleHeaders.SHOOTING);
                    System.out.println(humanPlayer.getName() +": " + playersShootInfo);
                    playersShootInfo = MineExploder.explodeMine(humanPlayer);
                }
            }while (playersShootInfo.getFiringResult() != FiringResult.MISS
                    && playersShootInfo.getFiringResult() != FiringResult.MINE_SACRIFICE);

            do {
                if (isWinner) break;
                computersShootInfo = Shooter.makeShot(computerPlayer, humanPlayer.getGameField());
                computersShots.append(computersShootInfo + "; ");
                if (humanPlayer.getGameField().getCountAliveShips() == 0){
                    isWinner = true;
                    winner = computerPlayer;
                    break;
                }
                if (computersShootInfo.getFiringResult() == FiringResult.MINE) {
                    computersShootInfo = MineExploder.explodeMine(computerPlayer);
                    computersShots.append(computersShootInfo + "; ");
                }

            } while (computersShootInfo.getFiringResult() != FiringResult.MISS
                    && computersShootInfo.getFiringResult() != FiringResult.MINE_SACRIFICE);

            ConsoleHelper.printGame(humanPlayer, computerPlayer, ConsoleHeaders.SHOOTING);
            System.out.println(humanPlayer.getName() +": " + playersShootInfo);
            System.out.println(computerPlayer.getName() + ": " + computersShots.toString());
            computersShots.setLength(0);
        }while (!isWinner);

        return winner;
    }

}
