package src.main.java.controllers;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.event.MenuDragMouseEvent;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.main.java.Character;
import src.main.java.CharacterStat;
import src.main.java.FilterList;
import src.main.java.Game;
import src.main.java.Map;
import src.main.java.Match;
import src.main.java.Set;
import src.main.java.SetList;
import src.main.java.Statistics;
import src.main.java.filters.DateFilter;
import src.main.java.filters.Filter;
import src.main.java.filters.MapFilter;
import src.main.java.filters.OpponentCharacterFilter;
import src.main.java.filters.OpponentFilter;
import src.main.java.filters.OpponentScoreFilter;
import src.main.java.filters.PlayerCharacterFilter;
import src.main.java.filters.PlayerScoreFilter;
import src.main.java.filters.TeammateFilter;
import src.main.java.filters.TournamentFilter;

public class mainController implements Initializable {

    @FXML
    private ImageView gameImageView;
    @FXML
    private Label gameNameLabel;
    @FXML
    private VBox matchHistoryVBox, reportSetVBox, filterPlayerVBox, filterOpponentVBox;
    @FXML
    private ToggleGroup reportWinner;
    @FXML
    private ListView<HBox> reportListView;
    @FXML
    private TextField reportOpponentInput, reportTournamentInput, reportTeammateInput, filterOpponentTextField,
            filterTournamentTextField, filterTeammateTextField;
    @FXML
    private DatePicker reportDatePicker, filterDatePicker;
    @FXML
    private HBox dateTeammateHBox, filterBottomHBox, filterHBox, matchHistoryHBox;
    @FXML
    private ChoiceBox<String> filterMapChoiceBox;
    @FXML
    private ChoiceBox<Integer> filterPlayerScoreChoiceBox, filterOpponentScoreChoiceBox;
    @FXML
    private ScrollPane tableScrollPane;
    @FXML
    private TableView<CharacterStat> statsTableView;
    @FXML
    private TableColumn<CharacterStat, Integer> tableTotalSetsColumn, tableTotalMatchesColumn;
    @FXML
    private TableColumn<CharacterStat, String> tableCharacterNameColumn, tableBestMapColumn, tableWorstMapColumn,
            tableBestMatchupColumn, tableWorstMatchupColumn;
    @FXML
    private TableColumn<CharacterStat, Double> tableSetWinRatioColumn, tableMatchWinRatioColumn;

    private Game game;
    private ArrayList<Match> tempMatches = new ArrayList<>();
    private HBox matchEntryHBox;
    private SetList filteredSetList;

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
        generateMatchHistoryDisplay(false);
        generateReportSetForm();
        generateFilterBox();
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

    private void generateMatchHistoryDisplay(boolean filtered) {
        matchHistoryVBox.getChildren().clear();
        ArrayList<Set> useSets = game.getSetList().getAllSets();
        if (filtered) {
            useSets = filteredSetList.getAllSets();
            System.out.println("Number of Sets Filtered: " + useSets.size());
        }
        for (Set set : useSets) {
            HBox setContainerHBox = new HBox();
            setContainerHBox.setPrefHeight(125);
            setContainerHBox.setMaxHeight(125);
            setContainerHBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"), BorderStrokeStyle.SOLID,
                    new CornerRadii(0.0), new BorderWidths(1.0))));
            if (set.getWin()) {
                setContainerHBox.setStyle("-fx-background-color: #90EE90;");
            } else {
                setContainerHBox.setStyle("-fx-background-color: #FF0000;");
            }

            VBox removeButtonVBox = new VBox();
            removeButtonVBox.setAlignment(Pos.TOP_LEFT);
            removeButtonVBox.setPrefWidth(10.0);
            setContainerHBox.getChildren().add(removeButtonVBox);

            Button removeButton = new Button("X");
            removeButtonVBox.getChildren().add(removeButton);
            removeButton.setOnAction(event -> {
                Alert sureToDelete = new Alert(AlertType.CONFIRMATION, "Are you sure you'd like to delete this set?");
                Optional<ButtonType> confirm = sureToDelete.showAndWait();
                if (confirm.get() == ButtonType.OK) {
                    deleteSetFromHistory(set, filtered);
                }
            });

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
            opponentContainerBox.setPrefWidth(140);
            opponentContainerBox.setMaxWidth(140);
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
            Label oppTeamLabel = new Label(set.getOpponent());
            opponentContainerBox.getChildren().add(oppTeamLabel);

            matchHistoryVBox.getChildren().add(setContainerHBox);

        }
    }

    private VBox generateMatchInfoVBox(Match match) {
        VBox matchInfoVBoxContainer = new VBox();
        matchInfoVBoxContainer.setAlignment(Pos.CENTER);
        matchInfoVBoxContainer.setMaxWidth(115);
        matchInfoVBoxContainer.setPrefWidth(115);

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
            if (game.isMap()) {
                Label mapLabel = new Label(match.getMap().getName());
                mapLabel.setUnderline(true);
                middleVBoxContainer.getChildren().add(mapLabel);
            }
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

    private void generateReportSetForm() {
        HBox matchInfoInputHBox = new HBox();
        this.matchEntryHBox = matchInfoInputHBox;
        matchInfoInputHBox.setAlignment(Pos.CENTER);
        matchInfoInputHBox.setMaxHeight((game.getCharactersPerSide() * 50.0) + 25.0);
        matchInfoInputHBox.setPrefHeight((game.getCharactersPerSide() * 50.0) + 25.0);
        matchInfoInputHBox.setSpacing(10.0);

        reportSetVBox.getChildren().add(2, matchInfoInputHBox);

        VBox playerCharactersVBox = new VBox();
        playerCharactersVBox.setAlignment(Pos.CENTER);
        playerCharactersVBox.setPrefHeight(matchInfoInputHBox.getHeight());
        matchInfoInputHBox.getChildren().add(playerCharactersVBox);

        Label playerLabel = new Label("Player");
        playerCharactersVBox.getChildren().add(playerLabel);

        ArrayList<String> allCharacterStrings = new ArrayList<>();
        for (Character character : game.getCharacters()) {
            allCharacterStrings.add(character.getName());
        }

        for (int i = 1; i <= game.getCharactersPerSide(); i++) {
            ChoiceBox<String> playerChoiceBox = new ChoiceBox<String>();
            playerChoiceBox.getItems().setAll(allCharacterStrings);
            playerCharactersVBox.getChildren().add(playerChoiceBox);
        }

        if (game.isMap()) {
            VBox mapVBox = new VBox();
            mapVBox.setAlignment(Pos.CENTER);
            matchInfoInputHBox.getChildren().add(mapVBox);

            Label mapLabel = new Label("Map");
            mapVBox.getChildren().add(mapLabel);

            ArrayList<String> allMapsStrings = new ArrayList<>();
            for (Map map : game.getMaps()) {
                allMapsStrings.add(map.getName());
            }
            ChoiceBox<String> mapChoiceBox = new ChoiceBox<String>();
            mapChoiceBox.getItems().setAll(allMapsStrings);
            mapVBox.getChildren().add(mapChoiceBox);
        }

        VBox opponentCharactersVBox = new VBox();
        opponentCharactersVBox.setAlignment(Pos.CENTER);
        opponentCharactersVBox.setPrefHeight(matchInfoInputHBox.getHeight());
        matchInfoInputHBox.getChildren().add(opponentCharactersVBox);

        Label opponentLabel = new Label("Opponent");
        opponentCharactersVBox.getChildren().add(opponentLabel);

        for (int i = 1; i <= game.getCharactersPerSide(); i++) {
            ChoiceBox<String> opponentChoiceBox = new ChoiceBox<String>();
            opponentChoiceBox.getItems().setAll(allCharacterStrings);
            opponentCharactersVBox.getChildren().add(opponentChoiceBox);
        }

        if (!game.isTeammate()) {
            dateTeammateHBox.getChildren().remove(reportTeammateInput);
        }

    }

    public void addMatchToTempSet(ActionEvent event) {
        boolean matchValid = true;
        if (this.matchEntryHBox != null) {
            VBox playerCharactersVBox = (VBox) matchEntryHBox.getChildren().get(0);
            ArrayList<Character> playerChars = new ArrayList<>();
            for (Node node : playerCharactersVBox.getChildren()) {
                if (node instanceof ChoiceBox<?>) {
                    ChoiceBox<String> choiceBox = (ChoiceBox<String>) node;
                    String charStr = choiceBox.getValue();
                    for (Character character : game.getCharacters()) {
                        if (character.getName().equals(charStr)) {
                            playerChars.add(character);
                        }
                    }
                }
            }
            if (playerChars.size() != game.getCharactersPerSide()) {
                matchValid = false;
            }
            Map mapSelected = null;
            if (game.isMap() && matchValid) {
                VBox mapVBox = (VBox) matchEntryHBox.getChildren().get(1);
                ChoiceBox<String> mapChoiceBox = (ChoiceBox<String>) mapVBox.getChildren().get(1);
                for (Map map : game.getMaps()) {
                    if (map.getName().equals(mapChoiceBox.getValue())) {
                        mapSelected = map;
                    }
                }
                if (mapSelected == null) {
                    matchValid = false;
                }
            }
            ArrayList<Character> opponentChars = new ArrayList<>();
            if (matchValid) {
                VBox opponentCharactersVBox;
                opponentCharactersVBox = (VBox) matchEntryHBox.getChildren().get(1);
                if (game.isMap()) {
                    opponentCharactersVBox = (VBox) matchEntryHBox.getChildren().get(2);
                }
                for (Node node : opponentCharactersVBox.getChildren()) {
                    if (node instanceof ChoiceBox<?>) {
                        ChoiceBox<String> choiceBox = (ChoiceBox<String>) node;
                        String charStr = choiceBox.getValue();
                        for (Character character : game.getCharacters()) {
                            if (character.getName().equals(charStr)) {
                                opponentChars.add(character);
                            }
                        }
                    }
                }
                if (opponentChars.size() != game.getCharactersPerSide()) {
                    System.out.println(opponentChars.toString() + " ???");
                    matchValid = false;
                    System.out.println("Broke at 3 " + opponentChars.size() + " " + game.getCharactersPerSide());
                }
            }
            boolean playerWin = false;
            if (matchValid) {
                RadioButton selected = (RadioButton) reportWinner.getSelectedToggle();
                if (selected != null) {
                    if (selected.getText().equals("Me")) {
                        playerWin = true;
                    }
                } else {
                    matchValid = false;
                    System.out.println("Broke at 4");
                }
            }
            if (matchValid) {
                Character[] playerCharsArray = playerChars.toArray(new Character[0]);
                Character[] opponentCharsArray = opponentChars.toArray(new Character[0]);
                Match match = new Match(playerCharsArray, opponentCharsArray, mapSelected, playerWin);
                tempMatches.add(match);
                reportListView.getItems().add(getHBoxFromMatch(match));
            } else {
                Alert invalidMatchError = new Alert(AlertType.ERROR, "Please ensure all match details are filled in");
                invalidMatchError.show();
            }
        }
    }

    private HBox getHBoxFromMatch(Match match) {
        HBox matchHbox = new HBox();
        matchHbox.setAlignment(Pos.CENTER);
        matchHbox.setSpacing(10.0);

        String playerCharsString = match.getPlayerCharacters()[0].getName();
        String opponentCharsString = match.getOpponentCharacters()[0].getName();
        for (int i = 1; i < game.getCharactersPerSide(); i++) {
            playerCharsString += ", " + match.getPlayerCharacters()[i].getName();
            opponentCharsString += ", " + match.getOpponentCharacters()[i].getName();
        }
        String finalString = playerCharsString + " vs " + opponentCharsString;
        if (game.isMap()) {
            finalString += " on " + match.getMap().getName();
        }
        Label matchLabel = new Label(finalString);
        matchHbox.getChildren().add(matchLabel);

        Button removeButton = new Button();
        removeButton.setText("Remove");
        removeButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeMatch(matchHbox);
            }
        });
        matchHbox.getChildren().add(removeButton);
        if (match.isWin()) {
            matchHbox.setStyle("-fx-background-color: #90EE90;");
        } else {
            matchHbox.setStyle("-fx-background-color: #FF0000;");
        }

        return matchHbox;
    }

    private void removeMatch(HBox hbox) {
        int index = reportListView.getItems().indexOf(hbox);
        tempMatches.remove(index);
        reportListView.getItems().remove(hbox);
    }

    public void submitSet(ActionEvent event) {
        String error = "";
        if (reportOpponentInput.getText().equals("")) {
            error += "Opponent Name is empty\n";
        } else if (reportOpponentInput.getText().length() > 20) {
            error += "Opponent Name is too long (max 20 chars)\n";
        }

        if (reportTournamentInput.getText().equals("")) {
            error += "Tournament Name is empty\n";
        } else if (reportTournamentInput.getText().length() > 30) {
            error += "Tournament Name is too long (max 30 chars)\n";
        }

        if (reportDatePicker.getValue() == null) {
            error += "No Date Selected\n";
        } else if (reportDatePicker.getValue().isAfter(LocalDate.now())) {
            error += "Cannot select date in the future\n";
        }

        if (game.isTeammate()) {
            if (reportTeammateInput.getText().equals("")) {
                error += "Teammate Name is empty\n";
            } else if (reportTeammateInput.getText().length() > 20) {
                error += "Teammate Name is too long (max 20 chars)\n";
            }
        }

        if (tempMatches.size() == 0) {
            error += "No Matches Entered\n";
        } else {
            int pointCount = 0;
            for (Match match : tempMatches) {
                if (match.isWin()) {
                    pointCount++;
                } else {
                    pointCount--;
                }
            }
            if (pointCount == 0) {
                error += "Match wins and losses can't be equal (there must be a set winner)";
            }
        }

        if (error.equals("")) {
            Match[] matches = tempMatches.toArray(new Match[0]);
            String opponent = reportOpponentInput.getText();
            String teammate = "";
            if (game.isTeammate()) {
                teammate = reportTeammateInput.getText();
            }
            String tournament = reportTournamentInput.getText();
            LocalDate date = reportDatePicker.getValue();
            Set newSet = new Set(matches, opponent, teammate, tournament, date);
            game.getSetList().addSet(newSet);
            game.setListJsonToFile();
            generateMatchHistoryDisplay(false);
        } else {
            Alert setReportAlert = new Alert(AlertType.ERROR, error);
            setReportAlert.show();
        }
    }

    private void deleteSetFromHistory(Set set, boolean filtered) {
        game.getSetList().removeSet(set);
        generateMatchHistoryDisplay(filtered);
        game.setListJsonToFile();
    }

    private void generateFilterBox() {
        filterHBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"), BorderStrokeStyle.SOLID,
                new CornerRadii(0.0), new BorderWidths(1.0))));
        ArrayList<String> allCharactersNames = new ArrayList<>();
        for (Character character : game.getCharacters()) {
            allCharactersNames.add(character.getName());
        }
        for (int i = 0; i < game.getCharactersPerSide(); i++) {
            ChoiceBox<String> characterChoiceBox = new ChoiceBox<>();
            ChoiceBox<String> characterChoiceBox2 = new ChoiceBox<>();
            characterChoiceBox.setMaxWidth(100.0);
            characterChoiceBox2.setMaxWidth(100.0);
            characterChoiceBox.getItems().setAll(allCharactersNames);
            characterChoiceBox2.getItems().setAll(allCharactersNames);
            filterOpponentVBox.getChildren().add(characterChoiceBox);
            filterPlayerVBox.getChildren().add(characterChoiceBox2);

        }

        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(0);
        scores.add(1);
        scores.add(2);
        scores.add(3);
        filterPlayerScoreChoiceBox.getItems().setAll(scores);
        filterOpponentScoreChoiceBox.getItems().setAll(scores);

        ArrayList<String> allMapNames = new ArrayList<>();
        for (Map map : game.getMaps()) {
            allMapNames.add(map.getName());
        }
        filterMapChoiceBox.getItems().setAll(allMapNames);

        if (!game.isTeammate()) {
            filterBottomHBox.getChildren().remove(filterTeammateTextField);
        }
    }

    public void processFilter(ActionEvent event) {
        String opponentName = filterOpponentTextField.getText().toString();

        String tournamentName = filterTournamentTextField.getText().toString();

        Character[] playerCharacters = new Character[game.getCharactersPerSide()];
        int count = 0;
        for (Node choiceBoxNode : filterPlayerVBox.getChildren()) {
            ChoiceBox<String> characterChoice = (ChoiceBox<String>) choiceBoxNode;
            if (!characterChoice.getSelectionModel().isEmpty()) {
                for (Character character : game.getCharacters()) {
                    if (character.getName().equals(characterChoice.getValue())) {
                        playerCharacters[count] = character;
                        count++;
                    }
                }
            }
        }

        int playerScore = -1;
        if (!filterPlayerScoreChoiceBox.getSelectionModel().isEmpty()) {
            playerScore = Integer.valueOf(filterPlayerScoreChoiceBox.getValue().toString());
        }

        String mapSelectedString = filterMapChoiceBox.getValue();
        Map mapSelected = null;
        if (game.isMap()) {
            if (mapSelectedString != null) {
                for (Map map : game.getMaps()) {
                    if (map.getName().equals(mapSelectedString.toString())) {
                        mapSelected = map;
                    }
                }
            }
        }

        int opponentScore = -1;
        if (!filterOpponentScoreChoiceBox.getSelectionModel().isEmpty()) {
            opponentScore = Integer.valueOf(filterOpponentScoreChoiceBox.getValue().toString());
        }

        Character[] opponentCharacters = new Character[game.getCharactersPerSide()];
        count = 0;
        for (Node choiceBoxNode : filterOpponentVBox.getChildren()) {
            ChoiceBox<String> characterChoice = (ChoiceBox<String>) choiceBoxNode;
            if (!characterChoice.getSelectionModel().isEmpty()) {
                for (Character character : game.getCharacters()) {
                    if (character.getName().equals(characterChoice.getValue())) {
                        opponentCharacters[count] = character;
                        count++;
                    }
                }
            }
        }

        LocalDate dateSelected = filterDatePicker.getValue();

        String teammateName = null;
        if (game.isTeammate()) {
            teammateName = filterTeammateTextField.getText();
        }

        Filter[] filtersToSet = new Filter[13];
        Object[] filterData = new Object[13];
        int filterCount = 0;

        if (!opponentName.equals("")) {
            filtersToSet[filterCount] = new OpponentFilter();
            filterData[filterCount] = opponentName;
            filterCount++;
        }
        if (!tournamentName.equals("")) {
            filtersToSet[filterCount] = new TournamentFilter();
            filterData[filterCount] = tournamentName;
            filterCount++;
        }
        if (playerCharacters[0] != null) {
            for (Character character : playerCharacters) {
                filtersToSet[filterCount] = new PlayerCharacterFilter();
                filterData[filterCount] = character;
                filterCount++;
            }
        }
        if (playerScore != -1) {
            filtersToSet[filterCount] = new PlayerScoreFilter();
            filterData[filterCount] = playerScore;
            filterCount++;
        }
        if (mapSelected != null) {
            filtersToSet[filterCount] = new MapFilter();
            filterData[filterCount] = mapSelected;
            filterCount++;
        }
        if (opponentScore != -1) {
            filtersToSet[filterCount] = new OpponentScoreFilter();
            filterData[filterCount] = opponentScore;
            filterCount++;
        }
        if (opponentCharacters[0] != null) {
            for (Character character : opponentCharacters) {
                filtersToSet[filterCount] = new OpponentCharacterFilter();
                filterData[filterCount] = character;
                filterCount++;
            }
        }
        if (dateSelected != null) {
            filtersToSet[filterCount] = new DateFilter();
            filterData[filterCount] = dateSelected;
            filterCount++;
        }
        if (game.isTeammate()) {
            if (!teammateName.equals("")) {
                filtersToSet[filterCount] = new TeammateFilter();
                filterData[filterCount] = teammateName;
            }
        }
        FilterList filterList = new FilterList(filtersToSet, filterData);
        System.out.println(filtersToSet[0]);
        this.filteredSetList = game.getSetList().applyFilters(filterList);
        System.out.println(filteredSetList.getLength());
        generateMatchHistoryDisplay(true);
    }

    public void clearFilter(ActionEvent event) {
        for (Node choiceBoxNode : filterPlayerVBox.getChildren()) {
            ChoiceBox<String> characterChoiceBox = (ChoiceBox<String>) choiceBoxNode;
            characterChoiceBox.getSelectionModel().clearSelection();
        }
        filterOpponentTextField.clear();
        filterTournamentTextField.clear();
        filterPlayerScoreChoiceBox.getSelectionModel().clearSelection();
        filterMapChoiceBox.getSelectionModel().clearSelection();
        filterOpponentScoreChoiceBox.getSelectionModel().clearSelection();
        filterDatePicker.getEditor().clear();
        filterTeammateTextField.clear();
        for (Node choiceBoxNode : filterOpponentVBox.getChildren()) {
            ChoiceBox<String> characterChoiceBox = (ChoiceBox<String>) choiceBoxNode;
            characterChoiceBox.getSelectionModel().clearSelection();
        }
        generateMatchHistoryDisplay(false);
    }

    public void showMatchHistory(ActionEvent event) {
        matchHistoryHBox.toBack();
    }

    public void showTable(ActionEvent event) {
        populateStatisticsTable(false);
        tableScrollPane.toBack();
        
    }

    private void populateStatisticsTable(boolean filtered) {
        SetList setListToUse = game.getSetList();
        if (filtered) {
            setListToUse = filteredSetList;
        }
        Statistics stats = new Statistics(setListToUse, game);
        stats.fillCharacterStats();
        stats.sortByMatchCount();

        ObservableList<CharacterStat> list = stats.getCharacterStatObservableList();

        tableCharacterNameColumn.setCellValueFactory(new PropertyValueFactory<CharacterStat,String>("characterName"));
        tableSetWinRatioColumn.setCellValueFactory(new PropertyValueFactory<CharacterStat,Double>("setWinRatio"));
        tableMatchWinRatioColumn.setCellValueFactory(new PropertyValueFactory<CharacterStat,Double>("matchWinRatio"));
        tableBestMapColumn.setCellValueFactory(new PropertyValueFactory<CharacterStat,String>("bestMap"));
        tableWorstMapColumn.setCellValueFactory(new PropertyValueFactory<CharacterStat,String>("worstMap"));
        tableBestMatchupColumn.setCellValueFactory(new PropertyValueFactory<CharacterStat,String>("bestMatchup"));
        tableWorstMatchupColumn.setCellValueFactory(new PropertyValueFactory<CharacterStat,String>("worstMatchup"));
        tableTotalSetsColumn.setCellValueFactory(new PropertyValueFactory<CharacterStat,Integer>("setCount"));
        tableTotalMatchesColumn.setCellValueFactory(new PropertyValueFactory<CharacterStat,Integer>("matchCount"));
        statsTableView.setItems(list);
    }
}
