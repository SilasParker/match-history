//TODO Okay so basically you need to create a list of chars and maps before you carry on the JSON stuff

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

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

    public void importSetList(String json, boolean replace) { // needs testing
        SetList tempSetList = gson.fromJson(json, SetList.class);
        if (replace) {
            this.setList = tempSetList;
        } else {
            tempSetList.getAllSets().forEach((set) -> this.setList.addSet(set));
        }

    }
}
