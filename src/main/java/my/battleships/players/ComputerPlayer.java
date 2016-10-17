package my.battleships.players;

import my.battleships.enums.CellState;
import my.battleships.enums.ShipTypes;
import my.battleships.logic.CoordinatesChecker;
import my.battleships.logic.ShipPlacer;
import my.battleships.logic.Shooter;
import my.battleships.tools.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.concurrent.*;

public class ComputerPlayer extends Player{
    private transient Random rand;
    private GameField enemysField;
    private Deque<ShipTypes> enemysLiveShips;

    public ComputerPlayer(String name) {
        super(name);
        rand = new Random(System.currentTimeMillis());
        enemysField = new GameField();
        enemysLiveShips = new ArrayDeque<>();
        for (ShipTypes type : ShipTypes.values())
            for (int i = 0; i < type.getCount(); i++)
                enemysLiveShips.add(type);
    }

    @Override
    public ShipPlacer.PlacingInfo chooseShipPlaceCoordinates(ShipTypes shipType) {
        return new ShipPlacer.PlacingInfo(new Coordinates(rand.nextInt(10), rand.nextInt(10)), rand.nextBoolean());
    }

    @Override
    public Coordinates chooseShotCoordinates() {
        List<Coordinates> list = possibleTargets();
        ShipTypes currentTarget = enemysLiveShips.getFirst();
        if (list.size() == 0) {
            Coordinates coordinates = null;
            do{
                coordinates = new Coordinates(rand.nextInt(10), rand.nextInt(10));
            }while (!isRightPossiblePosition(coordinates, currentTarget));
            return coordinates;
        }
        else return list.get(rand.nextInt(list.size()));
    }

    @Override
    public Coordinates chooseMineSacrifice() {
        for(Ship currentShip : gameField.getShips()){
            if(currentShip.isShipAlive())
                for(Deck currentDeck : currentShip.getShipDecks())
                    if(currentDeck.getState() == CellState.DECK) return currentDeck.getCoordinates();
        }

        return null;
    }

    public GameField getEnemysField() {
        return enemysField;
    }

    public void sayResult(Shooter.ShootInfo computersShootInfo) {
        int x = computersShootInfo.getCoordinates().getX();
        int y = computersShootInfo.getCoordinates().getY();
        switch (computersShootInfo.getFiringResult()){
            case MISS:
                enemysField.getCell(x ,y).setState(CellState.MISSED);
                break;
            case MINE_SACRIFICE_HURT:
            case HURT:
                enemysField.getCell(x, y).setState(CellState.DAMAGED);
                for(Cell currentCell : enemysField.getCellsCorners(x ,y))
                    currentCell.setState(CellState.MISSED);
                break;
            case DROWNED:
            case MINE_SACRIFICE_DROWN:
            case MINE:
                enemysField.getCell(x, y).setState(CellState.DAMAGED);
                for (Coordinates curCoord : findAllShipCell(x ,y, computersShootInfo.getDamagedShipType()))
                    for(Cell currentCell : enemysField.getNearlestCells(curCoord.getX(), curCoord.getY()))
                        if (currentCell.getState() == CellState.EMPTY) currentCell.setState(CellState.MISSED);
                enemysLiveShips.remove(computersShootInfo.getDamagedShipType());
        }
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
        stream.defaultReadObject();
        rand = new Random(System.currentTimeMillis());
    }

    private boolean isNearLiveShip(int x, int y){
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2 ; j++)
                if(!CoordinatesChecker.isCellOutOfRange(x+j, y+i)
                        &&  !(i==0&&j==0)
                        && enemysField.getCell(x+j, y+i).getState() == CellState.DAMAGED) return true;

        return false;
    }

    private List<Coordinates> possibleTargets(){
        List<Coordinates> tagretList = new ArrayList<>();
        for (int i = 0; i < 10 ; i++)
            for (int j = 0; j < 10 ; j++)
                if (enemysField.getCell(j,i).getState() == CellState.EMPTY && isNearLiveShip(j, i)) tagretList.add(new Coordinates(j, i));
        return tagretList;

    }

    private boolean isRightPossiblePosition(Coordinates coordinates, ShipTypes shipType){
        if(enemysField.getCell(coordinates).getState()!=CellState.EMPTY) return false;
        boolean isPossible = false;
        int x = coordinates.getX();
        int y = coordinates.getY();
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<Boolean>> results = new ArrayList<>();
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if ((i==0)^(j==0)) {
                    int finalJ = j;
                    int finalI = i;
                    results.add(exec.submit(() -> {
                        for (int k = 1; k < shipType.getDecks(); k++)
                            if (CoordinatesChecker.isCellOutOfRange(x+finalJ*k, y+finalI*k)
                                    || enemysField.getCell(x+finalJ*k, y+finalI*k).getState() != CellState.EMPTY)
                                return false;
                        return true;
                    }));
                }
        for (Future<Boolean> currentBoolean : results){
            try {
                isPossible |= currentBoolean.get();
            } catch (ExecutionException e) {
                System.err.println("task interrupted");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                exec.shutdown();
            }
        }
        return isPossible;
    }

    private Set<Coordinates> findAllShipCell (int x, int y, ShipTypes shipType){
        Set<Coordinates> coordinatesSet = new HashSet<>();
        coordinatesSet.add(new Coordinates(x, y));
        if (shipType.getDecks() != 1){
            ExecutorService exec = Executors.newCachedThreadPool();
            for (int i = -1; i < 2; i++)
                for (int j = -1; j < 2; j++)
                    if ((i==0)^(j==0)) {
                        int finalJ = j;
                        int finalI = i;
                        exec.execute(() -> {
                            for (int k = 1; k < shipType.getDecks(); k++)
                                if (!CoordinatesChecker.isCellOutOfRange(x+finalJ*k, y+finalI*k)
                                        && enemysField.getCell(x+finalJ*k, y+finalI*k).getState() == CellState.DAMAGED)
                                    coordinatesSet.add(new Coordinates(x+finalJ*k, y+finalI*k));
                                else return;
                        });
                    }

            try {
                exec.shutdown();
                exec.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.err.println("task interrupted");
            } finally {
                if(!exec.isTerminated()){
                    System.out.println("cancel non-finished tasks");
                }
                exec.shutdownNow();
            }
        }
        if (coordinatesSet.size() != shipType.getDecks()) throw new RuntimeException();
        return coordinatesSet;
    }
}
