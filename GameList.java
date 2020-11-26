import java.io.File;
import java.io.FilenameFilter;

public class GameList {
    private Game[] allGames;

    public GameList() {
        this.allGames = loadGamesFromFile();
    }

    private Game[] loadGamesFromFile() {
        File folder = new File("games");
        File[] matchingFiles = folder.listFiles(new FilenameFilter() {
            public boolean acceptFile(File directory, String name) {
                return name.endsWith("json");
            }
        });
        for (int i = 0; i < matchingFiles.length; i++) {
            // Go through, converting the files to Game objects and adding them to allGames
        }

    }

    public void addGame(Game newGame) {
        allGames[allGames.length] = newGame;
    }
}
