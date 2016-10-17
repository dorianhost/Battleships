package my.battleships.logic;

import my.battleships.Exeptions.ExitGameException;
import my.battleships.Exeptions.LoadGameException;
import my.battleships.Exeptions.MainMenuException;
import my.battleships.enums.ConsoleHeaders;
import my.battleships.enums.FiringResult;
import my.battleships.players.ComputerPlayer;
import my.battleships.players.Player;
import my.battleships.tools.ConsoleHelper;

import java.util.List;

public class GameManager {
    private static boolean isComputerVisionOn = false;
    private static Player humanPlayer = null;
    private static Player computerPlayer = null;
    private static int playersChoice = 0;

    public static void changeCompVision(){
        isComputerVisionOn = !isComputerVisionOn;
    }

    public static Player getHumanPlayer() {
        return humanPlayer;
    }

    public static Player getComputerPlayer() {
        return computerPlayer;
    }

    public static Player runGame(){
        Player winner = null;
        do {
            try{
                if (playersChoice == 0) {
                    ConsoleHelper.printWellcomeMEssage();
                    playersChoice = Menu.MainMenu();
                }
                switch (playersChoice){
                    case 4: return null;
                    case 3:
                        System.out.print("Filename for LOADING - ");
                        String saveName = null;
                        do{
                            try {
                                saveName = ConsoleHelper.readString();
                            } catch (Exception ignored) {
                                saveName = null;
                            }
                        }while (saveName == null);
                        List<Player> players = SaveLoadManager.loadFromFile(saveName);
                        humanPlayer = players.get(0);
                        computerPlayer = players.get(1);
                        break;
                    case 2:
                        if(humanPlayer == null || computerPlayer == null) {
                            System.out.println("You must begin the game before resume");
                            throw new MainMenuException();
                        }
                    case 1:
                        humanPlayer = new Player("Player");
                        computerPlayer = new ComputerPlayer("Computer");

                }
                if (humanPlayer.getGameField().getShipsCount() <12
                        || computerPlayer.getGameField().getShipsCount() <12)
                    placingStage();
                winner = shootingStage();
            }
            catch (MainMenuException e){
                playersChoice = 0;
            }
            catch (LoadGameException e){
                playersChoice = 3;
            }
            catch (ExitGameException e){
                playersChoice = 4;
            }
        } while (winner == null);
        return winner;
    }

    private static void placingStage() throws ExitGameException, LoadGameException, MainMenuException {
        ConsoleHelper.printGame(humanPlayer, computerPlayer, ConsoleHeaders.PLACING_SHIPS);
        System.out.println("Do you want to setup your ships by yourself? [Yy/Nn] ");
        if(ConsoleHelper.readString().matches("[Yy].*"))
            ShipPlacer.placeOwnShips(humanPlayer, false);
        else ShipPlacer.placeAnotherShips(computerPlayer, humanPlayer.getGameField(), false);
        ShipPlacer.placeOwnShips(computerPlayer, true);
    }

    private static Player shootingStage () throws LoadGameException, ExitGameException, MainMenuException {
        ConsoleHelper.printGame(humanPlayer, computerPlayer, ConsoleHeaders.SHOOTING);
        Shooter.ShootInfo playersShootInfo = null;
        Shooter.ShootInfo computersShootInfo = null;
        boolean isWinner = false;
        Player winner = null;
        StringBuilder compShootsToSHow = new StringBuilder();
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
                    ((ComputerPlayer)computerPlayer).sayResult(playersShootInfo);
                }
            }while (playersShootInfo.getFiringResult() == FiringResult.DROWNED
                    || playersShootInfo.getFiringResult() == FiringResult.HURT);

            do {
                if (isWinner) break;
                computersShootInfo = Shooter.makeShot(computerPlayer, humanPlayer.getGameField());
                ((ComputerPlayer)computerPlayer).sayResult(computersShootInfo);
                compShootsToSHow.append(computersShootInfo + "; ");
                if (humanPlayer.getGameField().getCountAliveShips() == 0){
                    isWinner = true;
                    winner = computerPlayer;
                    break;
                }
                if (computersShootInfo.getFiringResult() == FiringResult.MINE) {
                    computersShootInfo = MineExploder.explodeMine(computerPlayer);
                    compShootsToSHow.append(computersShootInfo + "; ");
                }

            } while (computersShootInfo.getFiringResult() == FiringResult.DROWNED
                    || computersShootInfo.getFiringResult() == FiringResult.HURT);

            ConsoleHelper.printGame(humanPlayer, computerPlayer, ConsoleHeaders.SHOOTING);
            System.out.println(humanPlayer.getName() +": " + playersShootInfo);
            System.out.println(computerPlayer.getName() + ": " + compShootsToSHow.toString());
            System.out.println("Players ships left - " + humanPlayer.getGameField().getCountAliveShips());
            System.out.println("Computers ships left - " + computerPlayer.getGameField().getCountAliveShips());
            if (isComputerVisionOn) ConsoleHelper.pritnGameField(((ComputerPlayer)computerPlayer).getEnemysField());
            compShootsToSHow.setLength(0);
        }while (!isWinner);

        return winner;
    }


}
