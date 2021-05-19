package src.main.java;

import com.google.gson.JsonObject;

//Class representing a Map from a Game
public class Map {
    
    private String name;

    // Constructor to initialise a Map instance
    // name: Name of the Map
    public Map(String name) {
        this.name = name;
    }

    // Converts all this Map's information into a String
    // Returns: A String containing all this Map's information neatly formatted
    public String toString() {
        return "Map: " + this.name;
    }

    // Getter for this Map's name
    // Returns: Map's name
    public String getName() {
        return this.name;
    }

    // Converts this Map into a JsonObject
    // Returns: JsonObject
    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        return json;
    }

}
