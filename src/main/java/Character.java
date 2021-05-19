package src.main.java;

import java.nio.file.Path;

import com.google.gson.JsonObject;

//Class representing a Character from a Game
public class Character {
    private String name;
    private Path imagePath;

    // Constructor to initialise a Character instance
    // name: The name of the Character
    // imagePath: The Path to the Character's user-assigned image
    public Character(String name, Path imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    // Converts all this Character's information into a String
    // Returns: A String containing all this Character's information neatly
    // formatted
    public String toString() {
        String toPrint = "Character: ";
        toPrint += this.name + " " + this.imagePath.toString();
        return toPrint;
    }

    // Getter for this Character's user-assigned image Path
    // Returns: Path to this Character's user-assigned image
    public Path getImagePath() {
        return this.imagePath;
    }

    // Getter for this Character's name
    // Returns: Character's name
    public String getName() {
        return this.name;
    }

    // Converts this Character instance into a JsonObject
    // Returns: This Character in JsonObject format
    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("imagePath", this.imagePath.toString());
        return json;
    }
}
