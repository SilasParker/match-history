package src.main.java.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private Label charNameLabel, mapNameLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void addCharName(ActionEvent event) {
        if (charNameEntry.getText().length() <= 20 && charNameEntry.getText().length() >= 1) {
            if (getHBoxFromLV(charNameEntry.getText(), charListView) != null) { //why does this not work????????????
                charListView.getItems().add(generateHBox(charNameEntry.getText(), charListView));
                charNameLabel.setText("");
                charNameEntry.setText("");
            } else {
                System.out.println(getHBoxFromLV(charNameEntry.getText(),charListView));
                charNameLabel.setText("This Character Already Exists");
            }
        } else {
            charNameLabel.setText("Requires 1-20 characters");
        }
    }

    public void addMapName(ActionEvent event) {
        if (mapNameEntry.getText().length() <= 20 && mapNameEntry.getText().length() >= 1) {
            if (getHBoxFromLV(mapNameEntry.getText(), mapListView) != null) {
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

    public void editImagePath(String name, ListView<HBox> listView) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files", "*.png"),
                new ExtensionFilter("JPG Files", "*.jpg"));
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
        for(HBox hbox : listView.getItems()) {
            Label curItem =  (Label) hbox.getChildren().get(0);
            System.out.println(curItem.getText()+" "+name);
            if(curItem.getText().equals(name)) {
                
                return hbox;
            }
        }
        return null;
    }


    private void removeItemFromListView(HBox hbox, ListView<HBox> listView) {
        listView.getItems().remove(hbox);
    }

    private HBox generateHBox(String name,ListView<HBox> listView) {
        HBox hbox = new HBox();
        Label label = new Label();
        label.setText(name);
        Button editPathButton = new Button();
        editPathButton.setText("Set Image");
        editPathButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                editImagePath(toDirectorySafeString(name),listView);
            }
        });
        Button removeButton = new Button();
        removeButton.setText("Remove");
        removeButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                removeItemFromListView(hbox,listView);
            }
        });
        Label pathValue = new Label();

        hbox.getChildren().addAll(label,editPathButton,removeButton,pathValue);
        return hbox;
    }

    public void submit(ActionEvent event) {
        String subGameName = gameName.getText();
        //Path
        int subCharNumPerSide = 1;
        if(charRadio2.isSelected()) {
            subCharNumPerSide = 2;
        } else if(charRadio3.isSelected()) {
            subCharNumPerSide = 3;
        }
        boolean subMap = true;
        if(mapRadio2.isSelected()) {
            subMap = false;
        }

        
    }
    
}
