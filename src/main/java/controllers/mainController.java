package src.main.java.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.main.java.Character;
import src.main.java.Game;
import src.main.java.Set;

public class mainController implements Initializable {

    @FXML private ImageView gameImageView;
    @FXML private Label gameNameLabel;

    private Game game;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

    public void initData(Game game) {
        System.out.println(game.getName());
        this.game = game;

        String imageDirectory = "/src/local/games/"+game.toDirectorySafeString(game.getName())+".png";
        System.out.println(imageDirectory);

        gameImageView.setImage(new Image("src/local/games/"+game.toDirectorySafeString(game.getName())+"/"+game.toDirectorySafeString(game.getName())+".png"));
        centerImageInImageView(gameImageView);
        System.out.println(gameImageView.getX()+","+gameImageView.getY());
        gameNameLabel.setText(game.getName());
    }



    private void centerImageInImageView(ImageView imgView) {
        Image img = imgView.getImage();
        double w = 0;
        double h = 0;
        double xRatio = imgView.getFitWidth()/img.getWidth();
        double yRatio = imgView.getFitHeight()/img.getHeight();
        double reductionCoefficient = 0;
        if(xRatio >= yRatio) {
            reductionCoefficient = yRatio;
        } else {
            reductionCoefficient = xRatio;
        }
        w = img.getWidth()*reductionCoefficient;
        h = img.getHeight()*reductionCoefficient;
        imgView.setX((imgView.getFitWidth()-w)/2);
        imgView.setY((imgView.getFitHeight()-h)/2);
        

    }

    public void backToGameSelect(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL fxmlURL = getClass().getResource("/src/resources/fxml/gameSelect.fxml");
        loader.setLocation(fxmlURL);
        Parent gameSelectViewParent = loader.load();
        Scene gameSelectViewScene = new Scene(gameSelectViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(gameSelectViewScene);
        window.show();
    }

    private void generateMatchHistoryDisplay() {
        for(Set set : game.getSetList().getAllSets()) {
            HBox setContainerHBox = new HBox();
            setContainerHBox.setPrefHeight(125);
            setContainerHBox.setMaxHeight(125);

            VBox playerContainerVBox = new VBox();
            playerContainerVBox.setAlignment(Pos.CENTER);
            playerContainerVBox.setPrefWidth(150);
            playerContainerVBox.setMaxWidth(150);
            setContainerHBox.getChildren().add(playerContainerVBox);
            
            ArrayList<Character> playerMostPlayed = set.getMostPlayedCharacters(false);
            ImageView playerImageView, teammateImageView;
            if(!game.isTeammate()) {
                Character mostPlayed = playerMostPlayed.get(0);
                playerImageView = new ImageView(mostPlayed.getImagePath().toString());
                playerImageView.setFitHeight(90.0);
                playerImageView.setFitWidth(90.0);
                playerContainerVBox.getChildren().add(playerImageView);
            } else {
                GridPane imageViewGridPaneContainer = new GridPane();
                Character mostPlayed = playerMostPlayed.get(0);
                Character secondMostPlayed = playerMostPlayed.get(1);
                playerImageView = new ImageView(mostPlayed.getImagePath().toString());
                playerImageView.setFitHeight(60.0);
                playerImageView.setFitWidth(60.0);
                teammateImageView = new ImageView(secondMostPlayed.getImagePath().toString());
                teammateImageView.setFitHeight(60.0);
                teammateImageView.setFitWidth(60.0);
                imageViewGridPaneContainer.add(playerImageView,0,0);
                imageViewGridPaneContainer.add(teammateImageView,1,1);
                GridPane.setHalignment(playerImageView, HPos.RIGHT);
                GridPane.setHalignment(teammateImageView, HPos.LEFT);
                playerContainerVBox.getChildren().add(imageViewGridPaneContainer);
            }

            if(game.isTeammate()) {
                Label teammateLabel = new Label(set.getTeammate());
                playerContainerVBox.getChildren().add(teammateLabel);
            }
            
            VBox mainInfoVBox = new VBox();
            mainInfoVBox.setAlignment(Pos.CENTER);
            mainInfoVBox.setPrefWidth(600);
            mainInfoVBox.setMaxWidth(600);
            setContainerHBox.getChildren().add(mainInfoVBox);

            Label tournamentAndDateLabel = new Label(set.getTournament()+" - "+set.getDate().toString());
            tournamentAndDateLabel.setStyle("-fx-font-weight: bold");
            tournamentAndDateLabel.setFont(new Font(12.0));
            mainInfoVBox.getChildren().add(tournamentAndDateLabel);

            HBox gameInfoHBox = new HBox();
            gameInfoHBox.setAlignment(Pos.CENTER);
            mainInfoVBox.getChildren().add(gameInfoHBox);
            
        }
    }

    
    
}
