package src.main.java;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.*;

//List that contains and manages all the Games saved locally
public class GameList {

    private ArrayList<Game> allGames;

    // Constructor to initialise this GameList
    public GameList() {
        this.allGames = loadGamesFromFile();
    }

    // Converts all this Character's information into a String
    // Returns: A String containing all this GameList's information neatly formatted
    public String toString() {
        String toPrint = "GameList: ";
        for (Game game : allGames) {
            toPrint += game.toString() + " ";
        }
        return toPrint + "\n";
    }

    // Getter for this GameList's ArrayList containing all the Games
    // Returns: The Game ArrayList
    public ArrayList<Game> getAllGames() {
        return this.allGames;
    }

    // Fills this GameList with Games loaded from the local file directory's Json
    // files
    // Returns: ArrayList containing all processed Games
    private ArrayList<Game> loadGamesFromFile() {
        File[] matchingFiles = getAllMatchingGames();
        ArrayList<Game> loadedGames = new ArrayList<Game>();
        for (int i = 0; i < matchingFiles.length; i++) {
            JsonObject jsonObj = null;
            try {
                String jsonContent = Files.readString(matchingFiles[i].toPath());
                jsonObj = new JsonParser().parse(jsonContent).getAsJsonObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonObj != null) {
                String gameName = jsonObj.get("name").getAsString();
                int gameCharacterNumPerSide = jsonObj.get("characterNumPerSide").getAsInt();
                boolean gameTeammate = jsonObj.get("teammate").getAsBoolean();
                JsonArray gameMaps = jsonObj.getAsJsonArray("maps");
                ArrayList<Map> allGameMapsArr = new ArrayList<>();
                for (JsonElement currentMap : gameMaps) {
                    JsonObject currentMapJsonObj = currentMap.getAsJsonObject();
                    String currentMapName = currentMapJsonObj.get("name").getAsString();
                    allGameMapsArr.add(new Map(currentMapName));
                }
                Map[] allGameMapsFinal = allGameMapsArr.toArray(new Map[allGameMapsArr.size()]);
                JsonArray gameChars = jsonObj.get("characters").getAsJsonArray();
                ArrayList<Character> allGameCharsArr = new ArrayList<>();
                for (JsonElement currentChar : gameChars) {
                    JsonObject currentCharJsonObj = currentChar.getAsJsonObject();
                    String currentCharName = currentCharJsonObj.get("name").getAsString();
                    Path currentCharPath = Paths.get(currentCharJsonObj.get("imagePath").getAsString());
                    allGameCharsArr.add(new Character(currentCharName, currentCharPath));
                }
                Character[] allGameCharsFinal = allGameCharsArr.toArray(new Character[allGameCharsArr.size()]);
                File tempFile = new File("src/local/setLists/" + matchingFiles[i].getName());

                Game gameToAdd = new Game(gameName, gameCharacterNumPerSide, gameTeammate, allGameMapsFinal,
                        new SetList(), allGameCharsFinal);
                if (tempFile.exists()) {
                    gameToAdd.importSetList("src/local/setLists/" + matchingFiles[i].getName(), true);
                }
                loadedGames.add(gameToAdd);
            }
        }
        return loadedGames;
    }

    // Retrieves a list of all the json Files in the local file directory
    // Returns File array containing instances of all the matching Files
    private File[] getAllMatchingGames() {
        ArrayList<File> allJsonFiles = new ArrayList<>();
        File folder = new File("src/local/games");
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        };
        File[] allFolders = folder.listFiles(filter);
        FilenameFilter jsonFilter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("json");
            }
        };
        for (File currentFolder : allFolders) {
            File[] matchFiles = currentFolder.listFiles(jsonFilter);
            if (matchFiles[0].exists()) {
                allJsonFiles.add(matchFiles[0]);
            }
        }
        File[] filesToReturn = new File[allJsonFiles.size()];
        for (int i = 0; i < allJsonFiles.size(); i++) {
            filesToReturn[i] = allJsonFiles.get(i);
        }
        return filesToReturn;
    }

    // Adds a Game to this GameList
    // newGame: Game to add
    public void addGame(Game newGame) {
        allGames.add(newGame);
    }

}
