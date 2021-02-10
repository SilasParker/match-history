package src.main.java;

import com.google.gson.JsonObject;

public class Map {
    private String name;

    public Map(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        return json;
    }

    public String toString() {
        return "Map: " + this.name;
    }
}
