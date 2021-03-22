package src.main.java.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

    public void generateMatchHistoryDisplay() {
        for(Set set : game.getSetList().getAllSets()) {
            HBox setContainerHBox = new HBox();
            setContainerHBox.setPrefHeight(125);
            setContainerHBox.setMaxHeight(125);

            VBox playerContainerVBox = new VBox();
            playerContainerVBox.setAlignment(Pos.CENTER);
            playerContainerVBox.setPrefWidth(150);
            playerContainerVBox.setMaxWidth(150);
            //TODO CONTINUE getMostPlayeCharacters(boolean) function in Set class 
            ImageView playerImage = new ImageView();
        }
    }
    
}
