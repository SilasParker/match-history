import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.*;

public class GameList {
    private ArrayList<Game> allGames;

    public GameList() {
        this.allGames = loadGamesFromFile();
    }

    private ArrayList<Game> loadGamesFromFile() {
        File folder = new File("games");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File directory, String name) {
                return name.endsWith("json");
            }

        };
        File[] matchingFiles = folder.listFiles(filter);
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
                Path gameImgPath = Paths.get(jsonObj.get("image").getAsString());
                JsonArray gameChars = jsonObj.get("characters").getAsJsonArray();
                ArrayList<Character> allGameCharsArr = new ArrayList<>();
                for (JsonElement currentChar : gameChars) {
                    JsonObject currentCharJsonObj = currentChar.getAsJsonObject();
                    String currentCharName = currentCharJsonObj.get("name").getAsString();
                    Path currentCharPath = Paths.get(currentCharJsonObj.get("imagePath").getAsString());
                    allGameCharsArr.add(new Character(currentCharName, currentCharPath));
                }
                Character[] allGameCharsFinal = allGameCharsArr.toArray(new Character[allGameCharsArr.size()]);
                File tempFile = new File("setLists/" + matchingFiles[i].getName());

                Game gameToAdd = new Game(gameName, gameCharacterNumPerSide, gameTeammate, allGameMapsFinal,
                        gameImgPath, new SetList(), allGameCharsFinal);
                if (tempFile.exists()) {
                    gameToAdd.importSetList("setLists/" + matchingFiles[i].getName(), true);
                }
                loadedGames.add(gameToAdd);

            }
        }
        return loadedGames;

    }

    public String toString() {
        String toPrint = "GameList: ";
        for (Game game : allGames) {
            toPrint += game.toString() + " ";
        }
        return toPrint + "/n";
    }

    public void addGame(Game newGame) {
        allGames.add(newGame);
    }

    public void outputAllGames() {
        allGames.forEach((game) -> {
            System.out.println(game.getName());
        });
    }

}
