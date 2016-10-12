package my.battleships.Players;

import my.battleships.Exeptions.WrongCoordinatesException;
import my.battleships.GameField.Deck;
import my.battleships.GameField.GameField;
import my.battleships.ships.*;

import java.util.*;

public class ComputerPlayer extends Player {
    private String name;
    private final GameField gameField;
    private Random rand;
    private Map<GameField.Coordinates, FiringResult> shootBase;
    private boolean wanglingMode;

    public ComputerPlayer(String name) {
        super(name);
        this.name = name;
        this.gameField = new GameField();
        rand = new Random(System.currentTimeMillis());
        shootBase = new LinkedHashMap<>();
        wanglingMode = false;
    }


    @Override
    public void setupTheShips() {
        setupTheShips(gameField, true);
    }

    @Override
    public void setupTheShips(GameField gameField){
        setupTheShips(gameField, false);
    }

    private void setupTheShips(GameField gameField, boolean hide) {
        for (ShipTypes currentType : ShipTypes.values()) {
            int shipsCountBeforeAdding = gameField.shipsCount();
            while (gameField.shipsCount() < shipsCountBeforeAdding + currentType.getCount()){
                try {
                    GameField.Coordinates coordinates = new GameField.Coordinates(rand.nextInt(10), rand.nextInt(10));
                    switch (currentType){
                        case FOUR_DECKER:
                        case THREE_DECKER:
                        case TWO_DECKER:
                            gameField.addShip(new Ship(currentType, coordinates, rand.nextBoolean(), hide));
                            break;
                        case SINGLE_DECKER:
                            gameField.addShip(new Ship(currentType, coordinates, false, hide));
                            break;
                        case MINE:
                            gameField.addShip(new Mine(currentType, coordinates, false, hide));
                    }
                } catch (Exception ignored) {
                    continue;
                }
            }
        }
        if (!hide) {
            reprintGameWithMessage(null);
        }
    }

    @Override
    public FiringResult makeHit(GameField gameField) {
        FiringResult firingResult = null;
        do{
            try {
                GameField.Coordinates coordinates = chooseCoordinatesForHit();
                firingResult = makeHit(gameField, coordinates);
                reprintGameWithMessage(coordinates + " - " + firingResult);
                shootBase.put(coordinates, firingResult);
                if (firingResult == FiringResult.MINE) {
                    firingResult = mineStruck(gameField);
                    reprintGameWithMessage(coordinates + " - " + firingResult);
                }
                if(firingResult == FiringResult.HURT) wanglingMode = true;
                else if (firingResult == FiringResult.DROWNED) wanglingMode = false;
            }catch (WrongCoordinatesException ignored){}
        }while (firingResult == null);
        return firingResult;
    }

    private GameField.Coordinates chooseCoordinatesForHit(){
        return new GameField.Coordinates(rand.nextInt(10), rand.nextInt(10));
//        if(!wanglingMode)return new GameField.Coordinates(rand.nextInt(10), rand.nextInt(10));
//        else{
//            int [] coordinates = new int[2];
//            final List<int[]> keyList = new ArrayList<>(shootBase.keySet());
//            for (int i = keyList.size()-1 ; i>=0; i--) {
//                if(shootBase.get(keyList.get(i)) == FiringResult.HURT){
//                    int hurtedX = keyList.get(i)[0];
//                    int hurtedY = keyList.get(i)[1];
//                    List<int[]> possibleShoots = new ArrayList<>();
//
//                    if (GameField.checkCoordinatesForOutOfBorders(hurtedX-1, hurtedY))
////                            && !shootBase.containsKey((new int[]{hurtedX-1, hurtedY})))
//                        possibleShoots.add((new int[]{hurtedX-1, hurtedY}));
//
//                    if (GameField.checkCoordinatesForOutOfBorders(hurtedX+1, hurtedY))
////                           &&  !shootBase.containsKey((new int[]{hurtedX+1, hurtedY})))
//                        possibleShoots.add((new int[]{hurtedX+1, hurtedY}));
//
//                    if (GameField.checkCoordinatesForOutOfBorders(hurtedX, hurtedY-1))
////                            && !shootBase.containsKey((new int[]{hurtedX, hurtedY-1})))
//                        possibleShoots.add((new int[]{hurtedX, hurtedY-1}));
//
//                    if (GameField.checkCoordinatesForOutOfBorders(hurtedX, hurtedY+1))
////                            && !shootBase.containsKey((new int[]{hurtedX, hurtedY+1})))
//                        possibleShoots.add((new int[]{hurtedX, hurtedY+1}));
//
//
//                    try {
//                        for (int j = 0; j < possibleShoots.size() ; j++) {
//                            if(shootBase.get(possibleShoots.get(j)) != null
//                                    && shootBase.get(possibleShoots.get(j)) == FiringResult.HURT)
//                                return possibleShoots.get(j%2+1);
//                        }
//                    } catch (IndexOutOfBoundsException e) {
//                        continue;
//                    }
//
//                    int random = 0;
//                    do{
//                        random = rand.nextInt(77543)%possibleShoots.size();
//                    } while (shootBase.containsKey(possibleShoots.get(random)));
//                    coordinates = possibleShoots.get(random);
//                    break;
//                }
//            }
//            return coordinates;
//        }
    }

    protected GameField.Coordinates chooseSacrificeAfterMineExplosion() {
        for(AbstractShip currentShip : gameField.getShips()){
            if(currentShip.isShipAlive())
                for(Deck currentDeck : currentShip.getShipDecks())
                    if(currentDeck.isAlive()) return currentDeck.getCoordinates();
        }
        return null;
    }


}