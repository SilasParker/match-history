package src.main.java.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.GatheringByteChannel;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
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
            
            gameImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {


                @Override
                public void handle(MouseEvent arg0) {
                    try {
                        openMainWindow(arg0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                
            });
            GridPane.setHalignment(gameImg, HPos.CENTER);
            gameGrid.add(gameImg,x,y);

            Button deleteButton = new Button("X");
            deleteButton.setPrefWidth(10);
            deleteButton.setPrefHeight(10);
            GridPane.setHalignment(deleteButton,HPos.RIGHT);
            GridPane.setValignment(deleteButton, VPos.TOP);
            deleteButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent arg0) {
                    deleteGame(arg0);

                }
                
            });
            gameGrid.add(deleteButton,x,y);

            x++;
        }
    }

    public void deleteGame(ActionEvent event) {
        Button btnClicked = (Button) event.getSource();
        int x = GridPane.getColumnIndex(btnClicked);
        int y = GridPane.getRowIndex(btnClicked);
        int arrayPos = (y*4)+x;
        Game gameToDelete = gameList.getAllGames().get(arrayPos);
        Alert alert = new Alert(AlertType.CONFIRMATION,"Delete "+gameToDelete.getName()+" permanently?",ButtonType.YES,ButtonType.CANCEL);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES) {
            File directoryToDelete = new File("src/local/games/"+gameToDelete.toDirectorySafeString(gameToDelete.getName()));
            File[] allContents = directoryToDelete.listFiles();
            if(allContents.length != 0) {
                for(File file : allContents) {
                    if(file.isDirectory()) {
                        for(File subFile : file.listFiles()) {
                            subFile.delete();
                        }
                    }
                    file.delete();
                }
                directoryToDelete.delete();
            } 
            generateGameDisplays(null);
        }
    }

    public void addNewGame(ActionEvent event) throws IOException {
        Parent addGameParent = FXMLLoader.load(getClass().getResource("../../../resources/fxml/addGame.fxml"));
        Stage addGameStage = new Stage();
        addGameStage.setScene(new Scene(addGameParent));
        addGameStage.setResizable(false);
        addGameStage.setTitle("Add New Game");
        addGameStage.initModality(Modality.APPLICATION_MODAL);
        addGameStage.showAndWait();
    }


    public GameList getGameList() {
        return this.gameList;
    }

    public void openMainWindow(MouseEvent event) throws IOException {
 
        ImageView imageClicked = (ImageView) event.getSource();
        int x = GridPane.getColumnIndex(imageClicked);
        int y = GridPane.getRowIndex(imageClicked);
        int arrayPos = (y*4)+x;
        Game gameToOpen = gameList.getAllGames().get(arrayPos);
        FXMLLoader loader = new FXMLLoader();
        URL fxmlURL = getClass().getResource("/src/resources/fxml/main.fxml");
        loader.setLocation(fxmlURL);
        Parent mainViewParent = loader.load();
        Scene mainViewScene = new Scene(mainViewParent);
        mainController controller = loader.getController();
        controller.initData(gameToOpen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }

}
