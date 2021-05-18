package src.main.java.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import src.main.java.*;
import src.main.java.Character;
import src.main.java.filters.*;
import src.main.java.filters.Filter;

import java.util.Date;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class main extends Application {
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

    public static void main(String[] args) {
        launch(args);
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("DD-MM-yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
