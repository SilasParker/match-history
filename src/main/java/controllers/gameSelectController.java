package src.main.java.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.main.java.GameList;

public class gameSelectController implements Initializable {

    @FXML private Button addGameButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        GameList gameList = new GameList();

    }

    public void addNewGame(ActionEvent event) throws IOException {
        Parent addGameParent = FXMLLoader.load(getClass().getResource("../../../resources/fxml/addGame.fxml"));
        Stage addGameStage = new Stage();
        addGameStage.setScene(new Scene(addGameParent));
        addGameStage.setResizable(false);
        addGameStage.setTitle("Add New Game");
        addGameStage.initModality(Modality.APPLICATION_MODAL);
        addGameStage.show();


    }
    

}
