package src.main.java.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import src.main.java.Game;

public class mainController implements Initializable {

    @FXML ImageView gameImageView;

    private Game game;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

    public void initData(Game game) {
        System.out.println(game.getName());
        this.game = game;

        String imageDirectory = "/src/local/games/"+game.toDirectorySafeString(game.getName())+".png";
        System.out.println(imageDirectory);
        File file = new File("/src/local/games/"+game.toDirectorySafeString(game.getName())+".png");
        Image image = new Image(file.toURI().toString());
        gameImageView.setImage(image);
    }
    
}
