package my.battleships.logic;

import my.battleships.Exeptions.ExitGameException;
import my.battleships.Exeptions.LoadGameException;
import my.battleships.Exeptions.MainMenuException;
import my.battleships.players.Player;
import my.battleships.tools.ConsoleHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveLoadManager {

    public static void saveIntoFile(String saveName){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./" + saveName + ".bsh"))){
                oos.writeObject(new GameData(GameManager.getHumanPlayer(), GameManager.getComputerPlayer()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
        }
        System.out.println("Game was successfully SAVED to file " + System.getProperty("user.dir") +"\\"+ saveName + ".bsh");
    }

    public static List<Player> loadFromFile(String saveName){
        GameData gameData = null;
        try(ObjectInputStream is = new ObjectInputStream(new FileInputStream("./" + saveName + ".bsh"))){
            gameData = (GameData) is.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Game was successfully LOADED from file " + System.getProperty("user.dir") +"\\"+ saveName + ".bsh");
        return gameData.getPlayers();
    }

    private static class GameData implements Serializable {
        private List<Player> players;

        GameData(Player ... players){
            this.players = new ArrayList<>();
            Collections.addAll(this.players, players);
        }

        public List<Player> getPlayers() {
            return players;
        }
    }
}
