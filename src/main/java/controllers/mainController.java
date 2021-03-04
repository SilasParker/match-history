package src.main.java.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class mainController implements Initializable {

    @FXML ImageView gameImageView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Stage thisStage = (Stage) gameImageView.getScene().getWindow();
        thisStage.setTitle("LETS GOOOOO");
    }
    
}
