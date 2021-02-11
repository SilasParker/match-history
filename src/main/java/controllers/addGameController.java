package src.main.java.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class addGameController implements Initializable {

    @FXML private TextField gameName, charNameEntry, mapNameEntry;
    @FXML private Button gameImage, charNameAdd, mapNameAdd, submit, cancel;
    @FXML private RadioButton charRadio1, charRadio2, charRadio3, mapRadio1, mapRadio2, teammateRadio1, teammateRadio2;
    @FXML private ListView<HBox> charListView, mapListView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

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
