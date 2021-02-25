package src.main.java.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.main.java.Game;
import src.main.java.GameList;

public class gameSelectController implements Initializable {

    @FXML private Button addGameButton, refreshButton;
    @FXML private GridPane gameGrid;

    private GameList gameList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        gameList = new GameList();
        System.out.println(gameList.toString());
        generateGameDisplays(null);

    }

    public void generateGameDisplays(ActionEvent event) {
        gameList = new GameList();
        gameGrid.getChildren().clear();
        int x = 0;
        int y = 0;
        for (Game currentGame : gameList.getAllGames()) {
            if(x > 7) {
                y++;
                x = 0;
            }
            ImageView gameImg = new ImageView(
                    "src/local/games/" + currentGame.toDirectorySafeString(currentGame.getName()) + "/"
                            + currentGame.toDirectorySafeString(currentGame.getName()) + ".png");
            gameImg.setPreserveRatio(true);
            gameImg.setFitWidth(150.0);
            gameImg.setFitHeight(150.0);
            GridPane.setHalignment(gameImg, HPos.CENTER);
            gameGrid.add(gameImg,x,y);
            x++;
        }
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


    public GameList getGameList() {
        return this.gameList;
    }

}
