package src.main.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

//Class to represent a Game and all that encompasses it
public class Game {
    
    private String name;
    private int characterNumPerSide;
    private boolean teammate;
    private Map[] maps;
    private transient SetList setList;
    private Gson gson = new Gson();
    private Character[] characters;

    // Constructor to initialise a Game instance
    // name: Name of the Game
    // characterNumPerSide: The number of Characters on each team in this Game
    // teammate: Whether there are two players per side in this Game or not
    // maps: Array containing all the Maps in this Game (if any)
    // setList: Match History SetList containing all Sets associate to this Game
    // locally
    // characters: Array containing all the Characters in this Game
    public Game(String name, int characterNumPerSide, boolean teammate, Map[] maps, SetList setList,
            Character[] characters) {
        this.name = name;
        this.characterNumPerSide = characterNumPerSide;
        this.teammate = teammate;
        this.maps = maps;
        this.setList = setList;
        this.characters = characters;
    }

    // Converts all this Game's information into a String
    // Returns: A String containing all this Game's information neatly formatted
    public String toString() {
        String toPrint = "Game: ";
        toPrint += name + " ";
        toPrint += characterNumPerSide + " ";
        toPrint += teammate + " ";
        for (Map map : maps) {
            toPrint += map.toString() + " ";
        }
        toPrint += setList.toString() + " ";
        for (Character character : characters) {
            toPrint += character.toString() + " ";
        }
        return toPrint;
    }

    // Getter for all Characters in this Game
    // Returns: Array containing all Character instances
    public Character[] getCharacters() {
        return this.characters;
    }

    // Getter for number of Characters on each team in this Game
    // Returns: The number
    public int getCharactersPerSide() {
        return this.characterNumPerSide;
    }

    // Getter for all Maps in this Game
    // Returns: Array containing all Map instances
    public Map[] getMaps() {
        return this.maps;
    }

    // Getter for this Game's name
    // Returns: The name
    public String getName() {
        return this.name;
    }

    // Getter for this Game's SetList
    // Returns: The SetList
    public SetList getSetList() {
        return this.setList;
    }

    // Getter for whether this Game requires a teammate or not
    // Returns: true if this Game requires a teammate, false if not
    public boolean isTeammate() {
        return this.teammate;
    }

    // Either extends or replaces this Game's SetList
    // jsonPath: The name of the Path to the Json that is being imported by the user
    // replace: Whether the SetList is being replaced or extended
    public void importSetList(String jsonPath, boolean replace) {
        JsonParser parser = new JsonParser();
        JsonObject json = null;
        try {
            Object obj = parser.parse(new FileReader(jsonPath));
            json = (JsonObject) obj;
        } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
        if (json != null) {
            JsonArray allSets = (JsonArray) json.get("allSets");
            SetList tempSetList = new SetList();
            for (JsonElement currentSet : allSets) {
                JsonObject currentSetJsonObject = currentSet.getAsJsonObject();
                JsonArray allMatches = currentSetJsonObject.getAsJsonArray("matches");
                ArrayList<Match> allMatchesArr = new ArrayList<>();
                for (JsonElement currentMatch : allMatches) {
                    JsonObject currentMatchJsonObject = currentMatch.getAsJsonObject();
                    JsonArray allPlayerChars = currentMatchJsonObject.getAsJsonArray("playerChars");
                    ArrayList<Character> allPlayerCharsArr = new ArrayList<>();
                    for (JsonElement currentPlayerChar : allPlayerChars) {
                        JsonObject currentPlayerCharJsonObj = currentPlayerChar.getAsJsonObject();
                        String currentPlayerCharName = currentPlayerCharJsonObj.get("name").getAsString();
                        Path currentPlayerImgPath = Paths.get(currentPlayerCharJsonObj.get("imagePath").getAsString());
                        allPlayerCharsArr.add(new Character(currentPlayerCharName, currentPlayerImgPath));
                    }
                    JsonArray allOpponentChars = currentMatchJsonObject.getAsJsonArray("opponentChars");
                    ArrayList<Character> allOpponentCharsArr = new ArrayList<>();
                    for (JsonElement currentOpponentChar : allOpponentChars) {
                        JsonObject currentOpponentCharJsonObj = currentOpponentChar.getAsJsonObject();
                        String currentOpponentCharName = currentOpponentCharJsonObj.get("name").getAsString();
                        Path currentOpponentImgPath = Paths
                                .get(currentOpponentCharJsonObj.get("imagePath").getAsString());
                        allOpponentCharsArr.add(new Character(currentOpponentCharName, currentOpponentImgPath));
                    }
                    Map map = null;
                    if (this.isMap()) {
                        JsonObject mapObject = currentMatchJsonObject.get("map").getAsJsonObject();
                        map = new Map(mapObject.get("name").getAsString());
                    }
                    boolean win = currentMatchJsonObject.get("win").getAsBoolean();
                    Character[] allPlayerCharsArrFinal = allPlayerCharsArr
                            .toArray(new Character[allPlayerCharsArr.size()]);
                    Character[] allOpponentCharsArrFinal = allOpponentCharsArr
                            .toArray(new Character[allOpponentCharsArr.size()]);
                    allMatchesArr.add(new Match(allPlayerCharsArrFinal, allOpponentCharsArrFinal, map, win));
                }
                String opponent = currentSetJsonObject.get("opponent").getAsString();
                String teammate = currentSetJsonObject.get("teammate").getAsString();
                String tournament = currentSetJsonObject.get("tournament").getAsString();
                String dateString = currentSetJsonObject.get("date").getAsString();
                LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("DD-MM-yyyy"));

                tempSetList.addSet(new Set((Match[]) allMatchesArr.toArray(new Match[allMatchesArr.size()]), opponent,
                        teammate, tournament, localDate));
            }
            if (replace) {
                this.setList = tempSetList;
            } else {
                tempSetList.getAllSets().forEach((set) -> this.setList.addSet(set));
            }
            setListJsonToFile();
        }
    }

    // Converts and saves the current SetList to a Json file
    public void setListJsonToFile() {
        JsonObject json = new JsonObject();
        JsonArray setListArray = this.setList.toJsonArray();
        json.add("allSets", setListArray);
        String fileName = toDirectorySafeString(this.name);
        Writer writer;
        try {
            writer = Files.newBufferedWriter(Paths.get("src/local/setLists", fileName + ".json"));
            gson.toJson(json, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Converts a string to be "directory safe" (can be used as a file name)
    // string: String to convert
    // Returns: The "directory safe" version of the String
    public static String toDirectorySafeString(String string) {
        char[] unsuitableChars = { '#', '%', '&', '{', '}', '\\', '<', '>', '*', '?', '/', ' ', '$', '!', '\'', '"',
                ':', '@', '+', '`', '|', '=', '.', '(', ')' };
        String fileName = "";
        for (int i = 0; i < string.length(); i++) {
            boolean charSafe = true;
            for (int j = 0; j < unsuitableChars.length; j++) {
                if (string.charAt(i) == unsuitableChars[j]) {
                    charSafe = false;
                }
            }
            if (charSafe) {
                fileName += string.charAt(i);
            }
        }
        return fileName.toLowerCase();
    }

    // Converts this Game into a JsonObject and saves it as a Json file
    public void toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("characterNumPerSide", this.characterNumPerSide);
        json.addProperty("teammate", this.teammate);
        JsonArray allMaps = new JsonArray();
        for (int i = 0; i < this.maps.length; i++) {
            allMaps.add(this.maps[i].toJsonObject());
        }
        json.add("maps", allMaps);
        JsonArray allChars = new JsonArray();
        for (int i = 0; i < this.characters.length; i++) {
            allChars.add(this.characters[i].toJsonObject());
        }
        json.add("characters", allChars);
        String fileName = toDirectorySafeString(this.name);
        Writer writer;
        try {
            writer = Files.newBufferedWriter(Paths.get("src/local/games/" + fileName, fileName + ".json"));
            gson.toJson(json, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Determines whether this Game uses Maps or not
    // Returns: Whether this Game uses Maps or not
    public boolean isMap() {
        if (this.maps.length == 0) {
            return false;
        } else {
            return true;
        }
    }

}
