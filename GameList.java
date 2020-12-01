import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;

import com.google.gson.*;

public class GameList {
    private ArrayList<Game> allGames;

    public GameList() {
        this.allGames = loadGamesFromFile();
    }

    private ArrayList<Game> loadGamesFromFile() {
        Gson gson = new Gson();
        File folder = new File("games");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File directory, String name) {
                return name.endsWith("json");
            }

        };
        File[] matchingFiles = folder.listFiles(filter);
        ArrayList<Game> loadedGames = new ArrayList<Game>();
        for (int i = 0; i < matchingFiles.length; i++) {
            // Go through, converting the files to Game objects and adding them to allGames
            //Create JSONs and manually add attributes 
            try {
                String jsonContent = Files.readString(matchingFiles[i].toPath());
                JsonObject jsonObj = new JsonParser().parse(jsonContent).getAsJsonObject();
                Game newGame = new Game(jsonObj.get("name").toString(),(int) jsonObj.get("characterNumPerSide"),(boolean) jsonObj.get("teammate"),jsonObj.get("maps"),jsonObj.get("image"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return loadedGames;

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
