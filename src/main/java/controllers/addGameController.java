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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
        fc.getExtensionFilters().addAll(new ExtensionFilter("JPG Files", "*.jpg"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            gameImgPathLabel.setText(selectedFile.getAbsolutePath());
        }

    }

    public void editCharacterImagePath(String name, ListView<HBox> listView) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new ExtensionFilter("JPG Files", "*.jpg"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            Label label = (Label) getHBoxFromLV(name, listView).getChildren().get(3);
            label.setText(selectedFile.getAbsolutePath());
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
        editPathButton.setText("Set Image");
        editPathButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editCharacterImagePath(toDirectorySafeString(name), listView);
            }
        });
        Button removeButton = new Button();
        removeButton.setText("Remove");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeItemFromListView(hbox, listView);
            }
        });
        Label pathValue = new Label();

        hbox.getChildren().addAll(label, editPathButton, removeButton, pathValue);
        return hbox;
    }

    public void preSubmit(ActionEvent event) throws IOException { // attempts to copy over images
        String dirSafeGameName = toDirectorySafeString(gameName.getText());
        Path gamePath = Paths.get("../../../local/games/" + dirSafeGameName);
        if (Files.exists(gamePath)) {
            Alert dirExistsAlert = new Alert(AlertType.ERROR,
                    "This Game Already Exists. If not, the name is potentially too similar to another");
            dirExistsAlert.show();
        } else {
            Files.createDirectories(gamePath);
            Path charsPath = Paths.get("../../../local/games/" + dirSafeGameName + "/chars");
            Path mapsPath = Paths.get("../../../local/games/" + dirSafeGameName + "/maps");
            Files.createDirectories(charsPath);
            Files.createDirectories(mapsPath);
            String characterNameErrorList = "";
            boolean charFileCopySuccess = true;
            for (HBox hbox : charListView.getItems()) {
                Label nameLabel = (Label) hbox.getChildren().get(0);
                Label pathLabel = (Label) hbox.getChildren().get(3);
                File source = new File(pathLabel.getText());
                Path namePath = Paths.get("../../../local/games/" + dirSafeGameName + "/chars/"
                        + toDirectorySafeString(nameLabel.getText()) + ".jpg");
                if (Files.exists(namePath)) {
                    characterNameErrorList += nameLabel.getText() + ", ";
                    charFileCopySuccess = false;
                } else {
                    File dest = new File(namePath.toString());
                    try {
                        Files.copy(source.toPath(), dest.toPath());
                    } catch (IOException e) {
                        characterNameErrorList += nameLabel.getText() + ", ";
                        charFileCopySuccess = false;
                    }
                }
            }
            String mapNameErrorList = "";
            boolean mapFileCopySuccess = true;
            for (HBox hbox : mapListView.getItems()) {
                Label nameLabel = (Label) hbox.getChildren().get(0);
                Label pathLabel = (Label) hbox.getChildren().get(3);
                File source = new File(pathLabel.getText());
                Path namePath = Paths.get("../../../local/games/" + dirSafeGameName + "/maps"
                        + toDirectorySafeString(nameLabel.getText()) + ".jpg");
                if(Files.exists(namePath)) {
                    mapNameErrorList += nameLabel.getText()+", ";
                    mapFileCopySuccess = false;
                } else {
                    File dest = new File(namePath.toString());
                    try {
                        Files.copy(source.toPath(),dest.toPath());
                    } catch(IOException e) {
                        mapNameErrorList += nameLabel.getText()+", ";
                        mapFileCopySuccess = false;
                    }
                }
            }
            if(!(charFileCopySuccess && mapFileCopySuccess)) {
                String error = "There was an error copying these characters/maps\n";
                if(!charFileCopySuccess) {
                    error += characterNameErrorList+"\n";
                }
                if(!mapFileCopySuccess) {
                    error += mapNameErrorList;
                }
                Alert fileCopyError = new Alert(AlertType.ERROR,error);
                fileCopyError.show();
            } else {
                submit(); //from here
            }
        }

    }

    public void submit() {
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

    }

}
