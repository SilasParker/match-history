import java.nio.file.Path;

import com.google.gson.JsonObject;

public class Character {
    private String name;
    private Path imagePath;

    public Character(String name, Path imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return this.name;
    }

    public Path getImagePath() {
        return this.imagePath;
    }

    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("imagePath", this.imagePath.toString());
        return json;
    }
}
