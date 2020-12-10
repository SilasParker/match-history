//TODO Find something new to do! 8/12

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Game {
    private String name;
    private int characterNumPerSide;
    private boolean teammate;
    private Map[] maps;
    private Path image;
    private transient SetList setList;
    private Gson gson = new Gson();
    private Character[] characters;

    public Game(String name, int characterNumPerSide, boolean teammate, Map[] maps, Path image, SetList setList,
            Character[] characters) {
        this.name = name;
        this.characterNumPerSide = characterNumPerSide;
        this.teammate = teammate;
        this.maps = maps;
        this.image = image;
        this.setList = setList;
        this.characters = characters;
    }

    public String toString() {
        String toPrint = "Game: ";
        toPrint += name + " ";
        toPrint += characterNumPerSide + " ";
        toPrint += teammate + " ";
        for (Map map : maps) {
            toPrint += map.toString() + " ";
        }
        toPrint += image.toString() + " ";
        toPrint += setList.toString() + " ";
        for (Character character : characters) {
            toPrint += character.toString() + " ";
        }
        return toPrint;
    }

    public String getName() {
        return this.name;
    }

    public int getCharactersPerSide() {
        return this.characterNumPerSide;
    }

    public boolean isTeammate() {
        return this.teammate;
    }

    public Map[] getMaps() {
        return this.maps;
    }

    public Path getImage() {
        return this.image;
    }

    public SetList getSetList() {
        return this.setList;
    }

    public Character[] getCharacters() {
        return this.characters;
    }

    public void toJSON() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("characterNumPerSide", this.characterNumPerSide);
        json.addProperty("teammate", this.teammate);
        JsonArray allMaps = new JsonArray();
        for (int i = 0; i < this.maps.length; i++) {
            allMaps.add(this.maps[i].getName());
        }
        json.add("maps", allMaps);
        json.addProperty("image", this.image.toString());
        JsonArray allChars = new JsonArray();
        JsonArray allCharsPath = new JsonArray();
        for (int i = 0; i < this.characters.length; i++) {
            allChars.add(this.characters[i].getName());
            allCharsPath.add(this.characters[i].getImagePath().toString());
        }
        json.add("characters", allChars);
        json.add("charactersPaths", allCharsPath);
        String fileName = toDirectorySafeString(this.name);
        Writer writer;
        try {
            writer = Files.newBufferedWriter(Paths.get("games", fileName));
            gson.toJson(json, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toDirectorySafeString(String string) {
        char[] unsuitableChars = { '#', '%', '&', '{', '}', '\\', '<', '>', '*', '?', '/', ' ', '$', '!', '\'', '"',
                ':', '@', '+', '`', '|', '=' };
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
        return fileName.toLowerCase() + ".json";

    }

    public void setListJsonToFile() {
        JsonObject json = new JsonObject();
        JsonArray setListArray = this.setList.toJsonArray();
        json.add("allSets", setListArray);
        String fileName = toDirectorySafeString(this.name);
        Writer writer;
        try {
            writer = Files.newBufferedWriter(Paths.get("setLists", fileName));
            gson.toJson(json, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void importSetList(String jsonPath, boolean replace) { // needs testing
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
                        System.out.println(currentPlayerCharName);
                        System.out.println(currentPlayerCharJsonObj.get("imagePath").getAsString());
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
                    JsonObject mapObject = currentMatchJsonObject.get("map").getAsJsonObject();
                    Map map = new Map(mapObject.get("name").getAsString());
                    boolean win = currentMatchJsonObject.get("win").getAsBoolean();
                    Character[] allPlayerCharsArrFinal = allPlayerCharsArr
                            .toArray(new Character[allPlayerCharsArr.size()]);
                    Character[] allOpponentCharsArrFinal = allOpponentCharsArr
                            .toArray(new Character[allOpponentCharsArr.size()]);
                    allMatchesArr.add(new Match(allPlayerCharsArrFinal, allOpponentCharsArrFinal, map, win));
                }
                JsonArray scoreOrder = currentSetJsonObject.getAsJsonArray("scoreOrder");
                boolean[] scoreOrderArr = new boolean[scoreOrder.size()];
                for (int i = 0; i < scoreOrder.size(); i++) {
                    scoreOrderArr[i] = Boolean.TRUE.equals(scoreOrder.get(i));
                }
                String opponent = currentSetJsonObject.get("opponent").getAsString();
                String teammate = currentSetJsonObject.get("teammate").getAsString();
                String tournament = currentSetJsonObject.get("tournament").getAsString();
                String dateString = currentSetJsonObject.get("date").getAsString();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("DD-MM-yyyy", Locale.ENGLISH);
                Date date = null;
                try {
                    date = dateFormatter.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tempSetList.addSet(new Set((Match[]) allMatchesArr.toArray(new Match[allMatchesArr.size()]),
                        scoreOrderArr, opponent, teammate, tournament, date));

            }

            if (replace) {
                this.setList = tempSetList;
            } else {
                tempSetList.getAllSets().forEach((set) -> this.setList.addSet(set));
            }
        }

    }
}
