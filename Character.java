import java.nio.file.Path;

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
}
