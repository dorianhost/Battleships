package my.battleships.Players;

import my.battleships.ConsoleHelper;
import my.battleships.Exeptions.*;
import my.battleships.GameField.GameField;
import my.battleships.ships.*;




public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void setupTheShips() {
        setupTheShips(this.gameField);
    }

    @Override
    public void setupTheShips(GameField gameField) {
        for (ShipTypes currentType : ShipTypes.values()) {
            int shipsCountBeforeAdding = gameField.shipsCount();
            while (gameField.shipsCount() < shipsCountBeforeAdding + currentType.getCount()){
                ConsoleHelper.writeMessageNL("\n" + currentType.getName()+ " will be at... \n" +
                        "(\"d3\" - on \"d3\" will be head, default direction is horizontal, \"d3v\" for vertical)");
                try {
                    String buffer = ConsoleHelper.readString();
                    GameField.Coordinates coordinates = GameField.Coordinates.coordinatesParser(buffer);
                    switch (currentType){
                        case FOUR_DECKER:
                        case THREE_DECKER:
                        case TWO_DECKER:
                            gameField.addShip(new Ship(currentType, coordinates,
                                    buffer.length() == 2, false));
                            break;
                        case SINGLE_DECKER:
                            gameField.addShip(new Ship(currentType, coordinates, false, false));
                            break;
                        case MINE:
                            gameField.addShip(new Mine(currentType, coordinates, false, false));
                    }
                    reprintGameWithMessage(null);
                } catch (ShipSetupException e) {
                    reprintGameWithMessage("Can't setup near and ON existing ships. Please choose another place...");
                }catch (WrongCoordinatesException e){
                    reprintGameWithMessage("Coordinates' format is wrong! Must be \"a8\", \"c7v\", etc.");
                }
            }
        }
    }

    @Override
    public FiringResult makeHit(GameField enemysGameField) {
        FiringResult result = null;
        do{
            try {
                ConsoleHelper.writeMessageNL("\nLet's SHOOT now!! Your target:");
                String buffer = ConsoleHelper.readString();
                GameField.Coordinates coordinates = GameField.Coordinates.coordinatesParser(buffer);
                result = makeHit(enemysGameField, coordinates);
                reprintGameWithMessage(buffer + " - " + result);
                if(result == FiringResult.MINE) {
                    result = mineStruck(gameField);
                    reprintGameWithMessage(buffer + " - " + result);
                }
            } catch (WrongCoordinatesException e) {
                reprintGameWithMessage("wrong coordinates. Try another..");
            }
        }while (result == null);
        return result;
    }

    @Override
    protected GameField.Coordinates chooseSacrificeAfterMineExplosion() {
        boolean isRightCoordinates = false;
        GameField.Coordinates coordinates = null;
        do{
            try {
                ConsoleHelper.writeMessageNL("Choose ship or ship's deck witch struck the mine:");
                coordinates = GameField.Coordinates.coordinatesParser(ConsoleHelper.readString());
                isRightCoordinates = gameField.isRightMineSacrifice(coordinates);
            } catch (WrongCoordinatesException e) {
                super.setChanged();
                super.notifyObservers("wrong sacrifice coordinates. Try another..");
            }
        }while (!isRightCoordinates);
        return coordinates;
    }

}
