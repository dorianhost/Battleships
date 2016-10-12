package my.battleships;

import my.battleships.Players.*;

public class Battleships {

    private final Player humanPlayer = new HumanPlayer("Player");
    private final Player computerPlayer = new ComputerPlayer("Computer");
    private final ConsoleHelper console = new ConsoleHelper(this);

    Player getHumanPlayer() {
        return humanPlayer;
    }

    Player getComputerPlayer() {
        return computerPlayer;
    }

    private void run() {
        ConsoleHelper.printGame(humanPlayer.getGameField(), computerPlayer.getGameField());
        humanPlayer.addObserver(console);

        if(ConsoleHelper.askPlayerAboutHisOunShipInstallation())
            humanPlayer.setupTheShips();
        else
            computerPlayer.setupTheShips(humanPlayer.getGameField());

        computerPlayer.addObserver(console);
        computerPlayer.setupTheShips();

        FiringResult currentPlayersFiringResult = null;
        do{
            currentPlayersFiringResult = humanPlayer.makeHit(computerPlayer.getGameField());
            gameOverCheck();
        }
        while (currentPlayersFiringResult != FiringResult.MISS);


        do{
            ConsoleHelper.pause();
            currentPlayersFiringResult = computerPlayer.makeHit(humanPlayer.getGameField());
            gameOverCheck();
        } while(currentPlayersFiringResult != FiringResult.MISS);
    }

    public static void main(String[] args) {
        Battleships game = new Battleships();
        game.run();
    }

    private void gameOverCheck(){
        if(!computerPlayer.getGameField().isAtLeastOneShipAlive())
            ConsoleHelper.winMessageAndCloseTheProgram(humanPlayer.getName());
        if(!humanPlayer.getGameField().isAtLeastOneShipAlive())
            ConsoleHelper.winMessageAndCloseTheProgram(computerPlayer.getName());
    }
}
