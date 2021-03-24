package src.main.java.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.event.MenuDragMouseEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.main.java.Character;
import src.main.java.Game;
import src.main.java.Match;
import src.main.java.Set;

public class mainController implements Initializable {

    @FXML
    private ImageView gameImageView;
    @FXML
    private Label gameNameLabel;
    @FXML
    private VBox matchHistoryVBox, reportSetVBox;

    private Game game;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void initData(Game game) {
        System.out.println(game.getName());
        this.game = game;

        String imageDirectory = "/src/local/games/" + game.toDirectorySafeString(game.getName()) + ".png";
        System.out.println(imageDirectory);

        gameImageView.setImage(new Image("src/local/games/" + game.toDirectorySafeString(game.getName()) + "/"
                + game.toDirectorySafeString(game.getName()) + ".png"));
        centerImageInImageView(gameImageView);
        System.out.println(gameImageView.getX() + "," + gameImageView.getY());
        gameNameLabel.setText(game.getName());
        generateMatchHistoryDisplay();
    }

    private void centerImageInImageView(ImageView imgView) {
        Image img = imgView.getImage();
        double w = 0;
        double h = 0;
        double xRatio = imgView.getFitWidth() / img.getWidth();
        double yRatio = imgView.getFitHeight() / img.getHeight();
        double reductionCoefficient = 0;
        if (xRatio >= yRatio) {
            reductionCoefficient = yRatio;
        } else {
            reductionCoefficient = xRatio;
        }
        w = img.getWidth() * reductionCoefficient;
        h = img.getHeight() * reductionCoefficient;
        imgView.setX((imgView.getFitWidth() - w) / 2);
        imgView.setY((imgView.getFitHeight() - h) / 2);

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
        // FOR TESTING PURPOSES

        Match match1 = new Match(new Character[] { game.getCharacters()[0] },
                new Character[] { game.getCharacters()[1] }, game.getMaps()[0], true);
        Match match2 = new Match(new Character[] { game.getCharacters()[0] },
                new Character[] { game.getCharacters()[1] }, game.getMaps()[1], false);
        Match match3 = new Match(new Character[] { game.getCharacters()[2] },
                new Character[] { game.getCharacters()[1] }, game.getMaps()[2], true);
        Date date = new Date(2021, 6, 21);
        Set setTest = new Set(new Match[] { match1, match2, match3 }, "Boohbah", "10QuidShoes", "King of the Hill 4",
                date);
        this.game.getSetList().addSet(setTest);
        // FOR TESTING PURPOSES

        for (Set set : game.getSetList().getAllSets()) {
            HBox setContainerHBox = new HBox();
            setContainerHBox.setPrefHeight(125);
            setContainerHBox.setMaxHeight(125);
            if (set.getWin()) {
                setContainerHBox.setStyle("-fx-background-color: #90EE90;");
            } else {
                setContainerHBox.setStyle("-fx-background-color: #FF0000;");
            }

            VBox playerContainerVBox = new VBox();
            playerContainerVBox.setAlignment(Pos.CENTER);
            playerContainerVBox.setPrefWidth(150);
            playerContainerVBox.setMaxWidth(150);
            setContainerHBox.getChildren().add(playerContainerVBox);

            ArrayList<Character> playerMostPlayed = set.getMostPlayedCharacters(false);
            ImageView playerImageView, teammateImageView;
            if (!game.isTeammate()) {
                Character mostPlayed = playerMostPlayed.get(0);
                System.out.println(mostPlayed.getImagePath().toString());
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
                imageViewGridPaneContainer.add(playerImageView, 0, 0);
                imageViewGridPaneContainer.add(teammateImageView, 1, 1);
                GridPane.setHalignment(playerImageView, HPos.RIGHT);
                GridPane.setHalignment(teammateImageView, HPos.LEFT);
                playerContainerVBox.getChildren().add(imageViewGridPaneContainer);
            }

            if (game.isTeammate()) {
                Label teammateLabel = new Label(set.getTeammate());
                playerContainerVBox.getChildren().add(teammateLabel);
            }

            VBox mainInfoVBox = new VBox();
            mainInfoVBox.setAlignment(Pos.CENTER);
            mainInfoVBox.setPrefWidth(600);
            mainInfoVBox.setMaxWidth(600);
            setContainerHBox.getChildren().add(mainInfoVBox);

            Label tournamentAndDateLabel = new Label(set.getTournament() + " - " + set.getDate().toString());
            tournamentAndDateLabel.setStyle("-fx-font-weight: bold");
            tournamentAndDateLabel.setFont(new Font(12.0));
            mainInfoVBox.getChildren().add(tournamentAndDateLabel);

            HBox setInfoHBox = new HBox();
            setInfoHBox.setAlignment(Pos.CENTER);
            mainInfoVBox.getChildren().add(setInfoHBox);

            for (int i = 0; i < 5; i++) {
                VBox generatedMatchVBox;
                if (i + 1 > set.getMatches().length) {
                    generatedMatchVBox = generateMatchInfoVBox(null);
                } else {
                    generatedMatchVBox = generateMatchInfoVBox(set.getMatches()[i]);
                }
                setInfoHBox.getChildren().add(generatedMatchVBox);
            }

            VBox opponentContainerBox = new VBox();
            opponentContainerBox.setAlignment(Pos.CENTER);
            opponentContainerBox.setPrefWidth(150);
            opponentContainerBox.setMaxWidth(150);
            setContainerHBox.getChildren().add(opponentContainerBox);

            ArrayList<Character> opponentMostPlayed = set.getMostPlayedCharacters(true);
            ImageView opponentImageView, oppTeammateImageView;
            if (!game.isTeammate()) {
                Character mostPlayed = opponentMostPlayed.get(0);
                opponentImageView = new ImageView(mostPlayed.getImagePath().toString());
                opponentImageView.setFitHeight(90.0);
                opponentImageView.setFitWidth(90.0);
                opponentContainerBox.getChildren().add(opponentImageView);
            } else {
                GridPane imageViewGridPaneContainer = new GridPane();
                Character mostPlayed = opponentMostPlayed.get(0);
                Character secondMostPlayed = opponentMostPlayed.get(1);
                opponentImageView = new ImageView(mostPlayed.getImagePath().toString());
                opponentImageView.setFitHeight(60.0);
                opponentImageView.setFitWidth(60.0);
                oppTeammateImageView = new ImageView(secondMostPlayed.getImagePath().toString());
                oppTeammateImageView.setFitHeight(60.0);
                oppTeammateImageView.setFitWidth(60.0);
                imageViewGridPaneContainer.add(opponentImageView, 0, 0);
                imageViewGridPaneContainer.add(oppTeammateImageView, 1, 1);
                opponentContainerBox.getChildren().add(imageViewGridPaneContainer);
            }

            if (game.isTeammate()) {
                Label oppTeamLabel = new Label(set.getOpponent());
                opponentContainerBox.getChildren().add(oppTeamLabel);
            }

            matchHistoryVBox.getChildren().add(setContainerHBox);

        }
    }

    private VBox generateMatchInfoVBox(Match match) {
        VBox matchInfoVBoxContainer = new VBox();
        matchInfoVBoxContainer.setAlignment(Pos.CENTER);
        matchInfoVBoxContainer.setMaxWidth(120);
        matchInfoVBoxContainer.setPrefWidth(120);

        if (match != null) {
            VBox topHalfVBoxContainer = new VBox();
            topHalfVBoxContainer.setAlignment(Pos.CENTER);
            matchInfoVBoxContainer.getChildren().add(topHalfVBoxContainer);

            for (int i = 0; i < game.getCharactersPerSide(); i++) {
                Label playerCharacterNameLabel = new Label(match.getPlayerCharacters()[i].getName());
                playerCharacterNameLabel.setFont(new Font(10.0));
                topHalfVBoxContainer.getChildren().add(playerCharacterNameLabel);
            }

            VBox middleVBoxContainer = new VBox();
            middleVBoxContainer.setAlignment(Pos.CENTER);
            topHalfVBoxContainer.getChildren().add(middleVBoxContainer);

            Circle matchCircle = new Circle();
            matchCircle.setRadius(15.0);
            if (match.isWin()) {
                matchCircle.setFill(javafx.scene.paint.Color.rgb(56, 93, 56));
            } else {
                matchCircle.setFill(javafx.scene.paint.Color.rgb(220, 20, 60));
            }
            middleVBoxContainer.getChildren().add(matchCircle);

            Label mapLabel = new Label(match.getMap().getName());
            mapLabel.setUnderline(true);
            middleVBoxContainer.getChildren().add(mapLabel);

            for (int i = 0; i < game.getCharactersPerSide(); i++) {
                Label opponentCharacterNameLabel = new Label(match.getOpponentCharacters()[i].getName());
                opponentCharacterNameLabel.setFont(new Font(10.0));
                matchInfoVBoxContainer.getChildren().add(opponentCharacterNameLabel);
            }
        } else {
            Circle emptyCircle = new Circle();
            emptyCircle.setRadius(15.0);
            emptyCircle.setFill(javafx.scene.paint.Color.rgb(0, 0, 0));
            matchInfoVBoxContainer.getChildren().add(emptyCircle);
        }
        return matchInfoVBoxContainer;
    }

    public void generateReportSetForm(ActionEvent event) { // rushed to test match history generation, redo

        RadioButton sourceButton = (RadioButton) event.getSource();
        int bestOfNum;
        if (sourceButton.getText().equals("Bo3")) {
            bestOfNum = 3;
        } else {
            bestOfNum = 5;
        }

        HBox scoreOrderHBox = new HBox();
        scoreOrderHBox.setAlignment(Pos.CENTER);
        scoreOrderHBox.setPrefHeight(30);
        scoreOrderHBox.setMaxHeight(30);
        reportSetVBox.getChildren().add(scoreOrderHBox);

        for (int i = 1; i <= bestOfNum; i++) {
            ChoiceBox<String> scoreOrderChoiceBox = new ChoiceBox<String>();
            scoreOrderChoiceBox.getItems().add("Player");
            scoreOrderChoiceBox.getItems().add("Opponent");
            scoreOrderChoiceBox.getItems().add("Unplayed");
            scoreOrderChoiceBox.setValue("Game " + i);
            scoreOrderHBox.getChildren().add(scoreOrderChoiceBox);
        }

        HBox playerCharacterHBox = new HBox();
        playerCharacterHBox.setAlignment(Pos.CENTER);
        playerCharacterHBox.setPrefHeight(30);
        playerCharacterHBox.setMaxHeight(30);
        reportSetVBox.getChildren().add(playerCharacterHBox);

        HBox opponentCharacterHBox = new HBox();
        opponentCharacterHBox.setAlignment(Pos.CENTER);
        opponentCharacterHBox.setPrefHeight(30);
        opponentCharacterHBox.setMaxHeight(30);
        reportSetVBox.getChildren().add(opponentCharacterHBox);

        for (int i = 1; i <= bestOfNum; i++) {
            ChoiceBox<String> characterChoiceBox = new ChoiceBox<String>();
            for (Character character : game.getCharacters()) {
                characterChoiceBox.getItems().add(character.getName());
            }
            characterChoiceBox.setValue("Game " + i);
            playerCharacterHBox.getChildren().add(characterChoiceBox);
            opponentCharacterHBox.getChildren().add(characterChoiceBox);
        }

    }

}
