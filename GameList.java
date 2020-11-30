import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;

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
            try(Reader fileReader = new FileReader(matchingFiles[i])) {
                Game newGame = gson.fromJson(fileReader, Game.class);
                System.out.println("success");
                loadedGames.add(newGame);
            } catch (IOException e) {
                System.out.println("lol");
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
