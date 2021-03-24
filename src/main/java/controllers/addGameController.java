package src.main.java.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser.ExtensionFilter;
import src.main.java.Character;
import src.main.java.Game;
import src.main.java.Map;
import src.main.java.SetList;

public class addGameController implements Initializable {

    @FXML
    private TextField gameName, charNameEntry, mapNameEntry;
    @FXML
    private Button gameImage, charNameAdd, mapNameAdd, submit, cancel;
    @FXML
    private RadioButton charRadio1, charRadio2, charRadio3, mapRadio1, mapRadio2, teammateRadio1, teammateRadio2;
    @FXML
    private ListView<HBox> charListView, mapListView;
    @FXML
    private Label charNameLabel, mapNameLabel, gameImgPathLabel;

    private File defaultWindowPath;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public void addCharName(ActionEvent event) {
        if (charNameEntry.getText().length() <= 20 && charNameEntry.getText().length() >= 1) {
            if (Objects.isNull(getHBoxFromLV(charNameEntry.getText(), charListView))) {
                charListView.getItems().add(generateHBox(charNameEntry.getText(), charListView));
                charNameLabel.setText("");
                charNameEntry.setText("");
            } else {
                System.out.println(getHBoxFromLV(charNameEntry.getText(), charListView));
                charNameLabel.setText("This Character Already Exists");
            }
        } else {
            charNameLabel.setText("Requires 1-20 characters");
        }
    }

    public void addMapName(ActionEvent event) {
        if (mapNameEntry.getText().length() <= 20 && mapNameEntry.getText().length() >= 1) {
            if (Objects.isNull(getHBoxFromLV(mapNameEntry.getText(), mapListView))) {
                mapListView.getItems().add(generateHBox(mapNameEntry.getText(), mapListView));
                mapNameLabel.setText("");
                mapNameEntry.setText("");
            } else {
                mapNameLabel.setText("This Map Already Exists");
            }
        } else {
            mapNameLabel.setText("Requires 1-20 characters");
        }
    }

    public void editGameImagePath(ActionEvent event) {
        FileChooser fc = new FileChooser();
        if (defaultWindowPath != null) {
            fc.setInitialDirectory(defaultWindowPath);
        }
        fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files", "*.png"));
        File selectedFile = fc.showOpenDialog(null);
        if (!Objects.isNull(selectedFile)) {
            gameImgPathLabel.setText(selectedFile.getAbsolutePath());
        }
        defaultWindowPath = selectedFile.getParentFile();

    }

    public void editCharMapImagePath(String name, ListView<HBox> listView) {
        FileChooser fc = new FileChooser();
        if (defaultWindowPath.exists()) {
            fc.setInitialDirectory(defaultWindowPath);
        }
        fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files", "*.png"));
        File selectedFile = fc.showOpenDialog(null);
        if (!Objects.isNull(selectedFile)) {
            Label label = (Label) getHBoxFromLV(name, listView).getChildren().get(3);
            label.setText(selectedFile.getAbsolutePath());
        }
        defaultWindowPath = selectedFile;
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
        return fileName.toLowerCase();

    }

    private HBox getHBoxFromLV(String name, ListView<HBox> listView) {
        for (HBox hbox : listView.getItems()) {
            Label curItem = (Label) hbox.getChildren().get(0);
            System.out.println(curItem.getText() + " " + name);
            if (curItem.getText().equals(name)) {

                return hbox;
            }
        }
        return null;
    }

    private void removeItemFromListView(HBox hbox, ListView<HBox> listView) {
        listView.getItems().remove(hbox);
    }

    private HBox generateHBox(String name, ListView<HBox> listView) {
        HBox hbox = new HBox();
        Label label = new Label();
        label.setText(name);
        Button editPathButton = new Button();
        if (listView.equals(charListView)) {
            editPathButton.setText("Set Image");
            editPathButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    editCharMapImagePath(name, listView);
                }
            });
        }
        Button removeButton = new Button();
        removeButton.setText("Remove");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeItemFromListView(hbox, listView);
            }
        });
        Label pathValue = new Label();
        if (listView.equals(charListView)) {
            hbox.getChildren().addAll(label, editPathButton, removeButton, pathValue);
        } else {
            hbox.getChildren().addAll(label, removeButton);
        }
        return hbox;
    }

    public void preSubmit(ActionEvent event) throws IOException { // attempts to copy over images
        String dirSafeGameName = toDirectorySafeString(gameName.getText());
        Path gamePath = Paths.get("src/local/games/" + dirSafeGameName);
        if (gameName.getText().equals("")) {
            Alert missingFieldAlert = new Alert(AlertType.WARNING, "Please Complete Missing Fields");
            missingFieldAlert.show();
        } else if (Files.exists(gamePath)) {
            System.out.println("Game Directory Already Exists");
            Alert dirExistsAlert = new Alert(AlertType.ERROR,
                    "This Game Already Exists. If not, the name is potentially too similar to another");
            dirExistsAlert.show();
        } else if (charListView.getItems().size() == 0
                || (mapListView.getItems().size() == 0 && mapRadio1.isSelected())) {
            Alert noCharOrMapAlert = new Alert(AlertType.ERROR,
                    "Please add at least 1 character or map (if applicable)");
            noCharOrMapAlert.show();
        } else {
            System.out.println("Directory not found, making new one");
            Files.createDirectories(gamePath);
            Path charsPath = Paths.get("src/local/games/" + dirSafeGameName + "/chars");
            Files.createDirectories(charsPath);
            String characterNameErrorList = "";
            boolean charFileCopySuccess = true;
            for (HBox hbox : charListView.getItems()) {
                System.out.println("Looping through characters");
                Label nameLabel = (Label) hbox.getChildren().get(0);
                Label pathLabel = (Label) hbox.getChildren().get(3);
                if (!pathLabel.getText().equals("")) {
                    File source = new File(pathLabel.getText());
                    Path namePath = Paths.get("src/local/games/" + dirSafeGameName + "/chars/"
                            + toDirectorySafeString(nameLabel.getText()) + ".png");
                    if (Files.exists(namePath)) {
                        System.out.println("Files for this character already exist");
                        characterNameErrorList += nameLabel.getText() + ", ";
                        charFileCopySuccess = false;
                    } else {
                        System.out.println("Copying image");
                        File dest = new File(namePath.toString());
                        try {
                            Files.copy(source.toPath(), dest.toPath());
                        } catch (IOException e) {
                            characterNameErrorList += nameLabel.getText() + ", ";
                            charFileCopySuccess = false;
                        }
                    }
                }

            }
            boolean gameFileCopySuccess = true;
            String gameImageError = "";
            if (!gameImgPathLabel.getText().equals("")) {
                File source = new File(gameImgPathLabel.getText());
                Path namePath = Paths.get("src/local/games/" + dirSafeGameName + "/" + dirSafeGameName + ".png");
                if (Files.exists(namePath)) {
                    System.out.println("File for this game already exists");
                    gameImageError = "An image for this game has already been set";
                    gameFileCopySuccess = false;
                } else {
                    File dest = new File(namePath.toString());
                    try {
                        Files.copy(source.toPath(), dest.toPath());
                    } catch (IOException e) {
                        gameImageError = "Could not copy Game image file";
                        gameFileCopySuccess = false;
                    }
                }
            }

            if (!charFileCopySuccess || !gameFileCopySuccess) {
                String error = "There was an error copying these images:\n";
                if (!charFileCopySuccess) {
                    error += characterNameErrorList + "\n";
                }
                if (!gameFileCopySuccess) {
                    error += gameImageError;
                }
                Alert fileCopyError = new Alert(AlertType.ERROR, error);
                fileCopyError.show();
            } else {
                System.out.println("niceone");
                submit(event);
            }
        }

    }

    public void submit(ActionEvent event) {
        String subGameName = gameName.getText();
        Path gameImagePath = Paths.get(gameImgPathLabel.getText());
        int subCharNumPerSide = 1;
        if (charRadio2.isSelected()) {
            subCharNumPerSide = 2;
        } else if (charRadio3.isSelected()) {
            subCharNumPerSide = 3;
        }
        boolean subMap = true;
        if (mapRadio2.isSelected()) {
            subMap = false;
        }
        boolean subTeammate = false;
        if (teammateRadio1.isSelected()) {
            subTeammate = true;
        }
        Character[] subCharacters = new Character[charListView.getItems().size()];
        int charCounter = 0;
        for (HBox hbox : charListView.getItems()) {
            Label tempCharNameLabel = (Label) hbox.getChildren().get(0);
            String tempCharName = tempCharNameLabel.getText();
            Path tempCharPath = Paths.get("/src/local/games/" + toDirectorySafeString(subGameName) + "/chars/"
                    + toDirectorySafeString(tempCharName) + ".png");
            Character tempChar = new Character(tempCharName, tempCharPath);
            subCharacters[charCounter] = tempChar;
            charCounter++;
        }
        Map[] subMaps = new Map[mapListView.getItems().size()];
        int mapCounter = 0;
        for (HBox hbox : mapListView.getItems()) {
            Label tempMapNameLabel = (Label) hbox.getChildren().get(0);
            String tempMapName = tempMapNameLabel.getText();
            Map tempMap = new Map(tempMapName);
            subMaps[mapCounter] = tempMap;
            mapCounter++;
        }

        Game newGame = new Game(subGameName, subCharNumPerSide, subTeammate, subMaps, new SetList(), subCharacters);
        newGame.toJson();
        gameName.getScene().getWindow().hide(); // exits

    }

}
