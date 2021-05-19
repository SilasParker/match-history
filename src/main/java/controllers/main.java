package src.main.java.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//main class that is executed start the application
public class main extends Application {

    // Opens the Game Select window
    // primaryStage: Stage represents the window that is opened
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/src/resources/fxml/gameSelect.fxml"));
            Scene scene = new Scene(root, 1200, 750);
            scene.getStylesheets().add(getClass().getResource("/src/resources/css/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("HitStat");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // main function that is ran to start the application
    public static void main(String[] args) {
        launch(args);
    }

}
