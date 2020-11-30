//TODO Okay so basically you need to create a list of chars and maps before you carry on the JSON stuff

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class Game {
    private String name;
    private int characterNumPerSide;
    private boolean teammate;
    private boolean maps;
    private String image;
    private transient SetList setList;
    private Gson gson = new Gson();

    public Game(String name, int characterNumPerSide, boolean teammate, boolean maps, String image, SetList setList) {
        this.name = name;
        this.characterNumPerSide = characterNumPerSide;
        this.teammate = teammate;
        this.maps = maps;
        this.image = image;
        this.setList = setList;
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

    public boolean isMap() {
        return this.maps;
    }

    public String getImage() {
        return this.image;
    }

    public SetList getSetList() {
        return this.setList;
    }

    public void toJSON() { // put this in GameList
        String fileName = toDirectorySafeString(this.name);
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("games", fileName));
            gson.toJson(this, writer);
            writer.close();
        } catch (JsonIOException e) {
            e.printStackTrace();
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
        String fileName = toDirectorySafeString(this.name);
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("setLists", fileName));
            gson.toJson(this.setList, writer);
            writer.close();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importSetList(String json, boolean replace) { //needs testing
        SetList tempSetList = gson.fromJson(json, SetList.class);
        if (replace) {
            this.setList = tempSetList;
        } else {
            tempSetList.getAllSets().forEach((set) -> this.setList.addSet(set));
        }

    }
}
