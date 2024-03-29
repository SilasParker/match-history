package src.main.java.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import src.main.java.Character;
import src.main.java.CharacterStat;
import src.main.java.CharacterStatColumn;
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
    private VBox matchHistoryVBox, reportSetVBox, filterPlayerVBox, filterOpponentVBox, filterMapVBox, suggestionVBox;
    @FXML
    private ToggleGroup reportWinner;
    @FXML
    private ListView<HBox> reportListView;
    @FXML
    private TextField reportOpponentInput, reportTournamentInput, reportTeammateInput, filterOpponentTextField,
            filterTournamentTextField, filterTeammateTextField, teammateSuggestionTextField,
            opponentSuggestionTextField;
    @FXML
    private DatePicker reportDatePicker, filterDatePicker;
    @FXML
    private HBox dateTeammateHBox, filterBottomHBox, filterHBox, matchHistoryHBox, chartHBox, filterMiddleHBox, tabHBox;
    @FXML
    private ChoiceBox<String> filterMapChoiceBox, playerCharSuggestionChoice1, playerCharSuggestionChoice2,
            playerCharSuggestionChoice3, mapSuggestionChoice, opponentCharSuggestionChoice1,
            opponentCharSuggestionChoice2, opponentCharSuggestionChoice3;
    @FXML
    private ChoiceBox<Integer> filterPlayerScoreChoiceBox, filterOpponentScoreChoiceBox;
    @FXML
    private ScrollPane tableScrollPane;
    @FXML
    private TableView<CharacterStatColumn> statsTableView;
    @FXML
    private TableColumn<CharacterStatColumn, Integer> tableNumColumn, tableTotalSetsColumn, tableTotalMatchesColumn;
    @FXML
    private TableColumn<CharacterStatColumn, String> tableCharacterNameColumn, tableBestMapColumn, tableWorstMapColumn,
            tableBestMatchupColumn, tableWorstMatchupColumn;
    @FXML
    private TableColumn<CharacterStatColumn, Double> tableSetWinRatioColumn, tableMatchWinRatioColumn;
    @FXML
    private PieChart characterMatchPieChart;
    @FXML
    private StackedBarChart<String, Double> mapRatioBarChart, matchupRatioBarChart;
    @FXML
    private LineChart<String, Double> setRatioLineChart;
    @FXML
    private CategoryAxis mapRatioXAxis;
    @FXML
    private ListView<Label> suggestTeammateList, suggestPlayerCharList, suggestMapList, suggestOpponentCharList,
            suggestOpponentList;

    private Game game;
    private ArrayList<Match> tempMatches = new ArrayList<>();
    private HBox matchEntryHBox;
    private SetList filteredSetList;

    // Ran upon loading the window, highlights the starting tab
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        highlightTab(0);
    }

    // Visually highlights the tab selected
    // tabSelected: The position of the button to highlight in the tab row
    private void highlightTab(int tabSelected) {
        for (int i = 0; i < 4; i++) {
            Button btn = (Button) tabHBox.getChildren().get(i);
            btn.setStyle(null);
            if (tabSelected == i) {
                btn.setStyle("-fx-background-color: #878787;");
            }

        }
    }

    // Dynamically generates the match history GUI
    // filtered: Whether the match history has been filtered or not
    private void generateMatchHistoryDisplay(boolean filtered) {
        matchHistoryVBox.getChildren().clear();
        ArrayList<Set> useSets = game.getSetList().getAllSets();
        if (filtered) {
            useSets = filteredSetList.getAllSets();
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
                setContainerHBox.setStyle("-fx-background-color: #FF6C70;");
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

    // Deletes a set from the match history
    // set: The Set instance to delete
    // filtered: Whether the match history is currently filtered or not
    private void deleteSetFromHistory(Set set, boolean filtered) {
        game.getSetList().removeSet(set);
        generateMatchHistoryDisplay(filtered);
        game.setListJsonToFile();
    }

    // Dynamically generates a match instance's GUI in the form of a VBox (to go
    // within the Set instance's GUI)
    // match: Match to process
    // Returns: VBox containing the matches information in graphical form
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
                matchCircle.setFill(javafx.scene.paint.Color.rgb(255, 0, 0));
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

    // Returns to the Game Select Screen
    // event: ActionEvent passed from a mouse click
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

    // Adds a match to the list of matches being recorded for a new Set
    // event: ActionEvent passed from a mouse click
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
                    matchValid = false;
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
                }
            }
            if (matchValid) {
                Character[] playerCharsArray = playerChars.toArray(new Character[0]);
                Character[] opponentCharsArray = opponentChars.toArray(new Character[0]);
                Match match = new Match(playerCharsArray, opponentCharsArray, mapSelected, playerWin);
                tempMatches.add(match);
                reportListView.getItems().add(getHBoxFromMatch(match));
                RadioButton radBut = (RadioButton) reportWinner.getSelectedToggle();
                radBut.setSelected(false);
            } else {
                Alert invalidMatchError = new Alert(AlertType.ERROR, "Please ensure all match details are filled in");
                invalidMatchError.show();
            }
        }
    }

    // Processes a newly submitted set when the "Submit" button is pressed
    // event: ActionEvent passed from a mouse click
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
            populateStatisticsTable(false);
            populateCharts(false);
            VBox playerCharactersVBox = (VBox) matchEntryHBox.getChildren().get(0);
            for (Node node : playerCharactersVBox.getChildren()) {
                if (node instanceof ChoiceBox<?>) {
                    ChoiceBox<String> choiceBox = (ChoiceBox<String>) node;
                    choiceBox.getSelectionModel().clearSelection();
                }
            }
            VBox opponentCharactersVBox;
            opponentCharactersVBox = (VBox) matchEntryHBox.getChildren().get(1);
            if (game.isMap()) {
                opponentCharactersVBox = (VBox) matchEntryHBox.getChildren().get(2);
            }
            for (Node node : opponentCharactersVBox.getChildren()) {
                if (node instanceof ChoiceBox<?>) {
                    ChoiceBox<String> choiceBox = (ChoiceBox<String>) node;
                    choiceBox.getSelectionModel().clearSelection();
                }
            }
            if (game.isMap()) {
                VBox mapVBox = (VBox) matchEntryHBox.getChildren().get(1);
                ChoiceBox<String> mapChoiceBox = (ChoiceBox<String>) mapVBox.getChildren().get(1);
                mapChoiceBox.getSelectionModel().clearSelection();
            }
            reportListView.getItems().clear();
            tempMatches.clear();
            reportOpponentInput.setText("");
        } else {
            Alert setReportAlert = new Alert(AlertType.ERROR, error);
            setReportAlert.show();
        }
    }

    // Populates the statistics table on the table tab
    // filtered: Whether the match history is currently filtered or not
    private void populateStatisticsTable(boolean filtered) {
        SetList setListToUse = game.getSetList();
        if (filtered) {
            setListToUse = filteredSetList;
        }
        Statistics stats = new Statistics(setListToUse, game);
        stats.sortByMatchCount();
        ObservableList<CharacterStatColumn> list = stats.getCharacterStatColumnObservableList();

        tableNumColumn.setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, Integer>("num"));
        tableCharacterNameColumn
                .setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, String>("characterName"));
        tableSetWinRatioColumn
                .setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, Double>("setWinRatio"));
        tableMatchWinRatioColumn
                .setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, Double>("matchWinRatio"));
        if (game.isMap()) {
            if (!statsTableView.getColumns().get(4).equals(tableBestMapColumn)) {
                statsTableView.getColumns().add(4, tableBestMapColumn);
                statsTableView.getColumns().add(5, tableWorstMapColumn);
            }
            tableBestMapColumn.setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, String>("bestMap"));
            tableWorstMapColumn.setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, String>("worstMap"));
        }
        tableBestMatchupColumn
                .setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, String>("bestMatchup"));
        tableWorstMatchupColumn
                .setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, String>("worstMatchup"));
        tableTotalSetsColumn.setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, Integer>("setsPlayed"));
        tableTotalMatchesColumn
                .setCellValueFactory(new PropertyValueFactory<CharacterStatColumn, Integer>("matchesPlayed"));
        statsTableView.setItems(list);
    }

    // Populates the graphs and charts on the graphs tab
    // filtered: Whether the match history is currently filtered or not
    private void populateCharts(boolean filtered) {
        SetList setListToUse = game.getSetList();
        if (filtered) {
            setListToUse = filteredSetList;
        }
        Statistics stats = new Statistics(setListToUse, game);
        characterMatchPieChart.getData().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (CharacterStat charStat : stats.getCharacterStats()) {
            if (charStat.calculateTotalMatchesPlayed() > 0) {
                pieChartData.add(
                        new PieChart.Data(charStat.getCharacter().getName(), charStat.calculateTotalMatchesPlayed()));
            }
        }
        characterMatchPieChart.setData(pieChartData);
        mapRatioBarChart.getData().clear();
        if (game.isMap()) {
            int[] mapWins = new int[game.getMaps().length];
            int[] mapLosses = new int[game.getMaps().length];
            boolean set = false;
            for (CharacterStat charStat : stats.getCharacterStats()) {
                if (!set) {
                    mapWins = charStat.getMapWins();
                    mapLosses = charStat.getMapLosses();
                    set = true;
                } else {
                    for (int i = 0; i < mapWins.length; i++) {
                        mapWins[i] += charStat.getMapWins()[i];
                        mapLosses[i] += charStat.getMapLosses()[i];
                    }
                }
            }
            double[] mapRatioArray = new double[game.getMaps().length];
            for (int i = 0; i < mapRatioArray.length; i++) {
                if (mapWins[i] == 0) {
                    mapRatioArray[i] = 0.0;
                } else {
                    mapRatioArray[i] = 100 * (double) ((double) mapWins[i] / (double) (mapWins[i] + mapLosses[i]));
                }
            }
            int count = 0;
            for (double mapRatio : mapRatioArray) {
                XYChart.Series<String, Double> series = new XYChart.Series<>();
                XYChart.Data<String, Double> mapRatioBar = new XYChart.Data<>();
                String mapName = game.getMaps()[count].getName();
                if (mapName.length() > 17) {
                    mapName = mapName.substring(0, 15) + "...";
                }
                mapRatioBar.XValueProperty().set(mapName);
                mapRatioBar.YValueProperty().set(mapRatio);
                series.getData().add(mapRatioBar);
                count++;
                mapRatioBarChart.getData().add(series);
            }
        } else {
            mapRatioBarChart.setTitle("Character Match Win Ratio");
            mapRatioXAxis.setLabel("Character");
            for (CharacterStat charStat : stats.getCharacterStats()) {
                XYChart.Series<String, Double> series = new XYChart.Series<>();
                XYChart.Data<String, Double> matchRatioBar = new XYChart.Data<>();
                matchRatioBar.XValueProperty().set(charStat.getCharacter().getName());
                matchRatioBar.YValueProperty().set(charStat.calculateMatchWinPercentage());
                series.getData().add(matchRatioBar);
                mapRatioBarChart.getData().add(series);
            }
        }
        int[] charWins = new int[game.getCharacters().length];
        int[] charLosses = new int[game.getCharacters().length];
        boolean set = false;
        for (CharacterStat charStat : stats.getCharacterStats()) {
            if (!set) {
                charWins = charStat.getCharacterWins();
                charLosses = charStat.getCharacterLosses();
                set = true;
            } else {
                for (int i = 0; i < charWins.length; i++) {
                    charWins[i] += charStat.getCharacterWins()[i];
                    charLosses[i] += charStat.getCharacterLosses()[i];
                }
            }
        }
        matchupRatioBarChart.getData().clear();
        double[] charRatioArray = new double[game.getCharacters().length];
        for (int i = 0; i < charRatioArray.length; i++) {
            if (charWins[i] == 0) {
                charRatioArray[i] = 0.0;
            } else {
                charRatioArray[i] = 100 * (double) ((double) charWins[i] / (double) (charWins[i] + charLosses[i]));
            }
        }
        int count = 0;
        for (double charRatio : charRatioArray) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            XYChart.Data<String, Double> charRatioBar = new XYChart.Data<>();
            charRatioBar.XValueProperty().set(game.getCharacters()[count].getName());
            charRatioBar.YValueProperty().set(charRatio);
            series.getData().add(charRatioBar);
            count++;
            matchupRatioBarChart.getData().add(series);
        }
        setRatioLineChart.getData().clear();
        ArrayList<ArrayList<Object>> ratioOverMonthsArray = stats.getSetWinRatioOverMonths();
        ArrayList<Object> monthAndYearArray = ratioOverMonthsArray.get(0);
        ArrayList<Object> setRatios = ratioOverMonthsArray.get(1);
        XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
        if (monthAndYearArray.size() > 1) {
            for (int i = 0; i < ratioOverMonthsArray.size(); i++) {
                String monthYear = (String) monthAndYearArray.get(i);
                Double setRatio = (Double) setRatios.get(i);
                System.out.println(monthYear + ": " + setRatio);
                series.getData().add(new XYChart.Data<String, Double>(monthYear, setRatio * 100.0));

            }
            setRatioLineChart.getData().add(series);
        }
    }

    // Processes the filters selected when the "Filter" button is pressed
    // event: ActionEvent passed from a mouse click
    public void processFilter(ActionEvent event) {
        String opponentName = filterOpponentTextField.getText().toString();
        String tournamentName = filterTournamentTextField.getText().toString();
        Character[] playerCharacters = new Character[game.getCharactersPerSide()];
        int count = 0;
        for (Node choiceBoxNode : filterPlayerVBox.getChildren()) {
            if (choiceBoxNode instanceof ChoiceBox) {
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
            if (choiceBoxNode instanceof ChoiceBox) {
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
        this.filteredSetList = game.getSetList().applyFilters(filterList);
        generateMatchHistoryDisplay(true);
        populateStatisticsTable(true);
        populateCharts(true);
    }

    // Clears the filters to default values when the "Clear" button is pressed
    // event: ActionEvent passed from a mouse click
    public void clearFilter(ActionEvent event) {
        for (Node choiceBoxNode : filterPlayerVBox.getChildren()) {
            if (choiceBoxNode instanceof ChoiceBox) {
                ChoiceBox<String> characterChoiceBox = (ChoiceBox<String>) choiceBoxNode;
                characterChoiceBox.getSelectionModel().clearSelection();
            }
        }
        filterOpponentTextField.clear();
        filterTournamentTextField.clear();
        filterPlayerScoreChoiceBox.getSelectionModel().clearSelection();
        filterMapChoiceBox.getSelectionModel().clearSelection();
        filterOpponentScoreChoiceBox.getSelectionModel().clearSelection();
        filterDatePicker.getEditor().clear();
        filterTeammateTextField.clear();
        for (Node choiceBoxNode : filterOpponentVBox.getChildren()) {
            if (choiceBoxNode instanceof ChoiceBox<?>) {
                ChoiceBox<String> characterChoiceBox = (ChoiceBox<String>) choiceBoxNode;
                characterChoiceBox.getSelectionModel().clearSelection();
            }
        }
        generateMatchHistoryDisplay(false);
        populateStatisticsTable(false);
        populateCharts(false);
    }

    // Displays the Match History tab
    // event: ActionEvent passed from a mouse click
    public void showMatchHistory(ActionEvent event) {
        matchHistoryHBox.toBack();
        highlightTab(0);
    }

    // Displays the Table tab
    // event: ActionEvent passed from a mouse click
    public void showTable(ActionEvent event) {
        tableScrollPane.toBack();
        highlightTab(1);
    }

    // Displays the Graphs tab
    // event: ActionEvent passed from a mouse click
    public void showCharts(ActionEvent event) {
        chartHBox.toBack();
        highlightTab(2);
    }

    // Displays the AI Suggestions tab
    // event: ActionEvent passed from a mouse click
    public void showSuggestions(ActionEvent event) {
        suggestionVBox.toBack();
        highlightTab(3);
        initialiseSuggestionsTab();
    }

    // Opens a filechooser and lets the user attempt to import a valid set list to
    // the current Game when the "Import" button is pressed
    // event: ActionEvent passed from a mouse click
    public void importSetList(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"));
        File selectedJson = fc.showOpenDialog(null);
        if (!Objects.isNull(selectedJson)) {
            String filePath = selectedJson.getAbsolutePath();
            Alert replaceImport = new Alert(AlertType.CONFIRMATION,
                    "Would you like to replace your set list with the imported file or add contained sets to current set list?");
            ButtonType replaceButton = new ButtonType("REPLACE");
            ButtonType addButton = new ButtonType("ADD TO EXISTING");
            replaceImport.getButtonTypes().setAll(replaceButton, addButton);
            Optional<ButtonType> confirm = replaceImport.showAndWait();
            if (confirm.get() == replaceButton) {
                game.importSetList(filePath, true);
            } else if (confirm.get() == addButton) {
                game.importSetList(filePath, false);
            }
            generateMatchHistoryDisplay(false);
            populateCharts(false);
            populateStatisticsTable(false);
        }
    }

    // Dynamically generates the form to submit a set through on the Match History
    // tab
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
            playerChoiceBox.setMaxWidth(80.0);
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
            mapChoiceBox.setMaxWidth(80.0);
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
            opponentChoiceBox.setMaxWidth(80.0);
            opponentChoiceBox.getItems().setAll(allCharacterStrings);
            opponentCharactersVBox.getChildren().add(opponentChoiceBox);
        }
        if (!game.isTeammate()) {
            dateTeammateHBox.getChildren().remove(reportTeammateInput);
        }
    }

    // Dynamically generates the filter box, seen throughout the Main screen
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
        if (game.isMap()) {
            ArrayList<String> allMapNames = new ArrayList<>();
            for (Map map : game.getMaps()) {
                allMapNames.add(map.getName());
            }
            filterMapChoiceBox.getItems().setAll(allMapNames);
        } else {
            filterMiddleHBox.getChildren().remove(filterMapVBox);
        }
        if (!game.isTeammate()) {
            filterBottomHBox.getChildren().remove(filterTeammateTextField);
        }
    }

    // Dynamically generates an HBox representing a single Match to add to the Match
    // list when submitting a new Set instance
    // match: Match to turn into an HBox
    // Returns: the HBox GUI representing the Match submitted
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
        removeButton.setText("X");
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

    // Removes a Match from the Match List when submitting a new Set instance
    // hbox: The HBox to remove that represents a Match
    private void removeMatch(HBox hbox) {
        int index = reportListView.getItems().indexOf(hbox);
        tempMatches.remove(index);
        reportListView.getItems().remove(hbox);
    }

    // Centers the image within an ImageView
    // imgView: The ImageView to center the source image in
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

    // Initialises the screen based on the Game selecte
    // game: Game selected
    public void initData(Game game) {
        this.game = game;
        gameImageView.setImage(new Image("src/local/games/" + game.toDirectorySafeString(game.getName()) + "/"
                + game.toDirectorySafeString(game.getName()) + ".png"));
        centerImageInImageView(gameImageView);
        gameNameLabel.setText(game.getName());
        generateMatchHistoryDisplay(false);
        populateStatisticsTable(false);
        populateCharts(false);
        generateReportSetForm();
        generateFilterBox();
        if (!game.isMap()) {
            statsTableView.getColumns().remove(tableBestMapColumn);
            statsTableView.getColumns().remove(tableWorstMapColumn);
        }
    }

    //Initialises the Suggestion Tab
    private void initialiseSuggestionsTab() {
        ArrayList<String> allCharacterNames = new ArrayList<>();
        for (Character character : game.getCharacters()) {
            allCharacterNames.add(character.getName());
        }
        ArrayList<String> allMapNames = new ArrayList<>();
        for (Map map : game.getMaps()) {
            allMapNames.add(map.getName());
        }
        this.playerCharSuggestionChoice1.getItems().setAll(allCharacterNames);
        this.playerCharSuggestionChoice2.getItems().setAll(allCharacterNames);
        this.playerCharSuggestionChoice3.getItems().setAll(allCharacterNames);
        this.opponentCharSuggestionChoice1.getItems().setAll(allCharacterNames);
        this.opponentCharSuggestionChoice2.getItems().setAll(allCharacterNames);
        this.opponentCharSuggestionChoice3.getItems().setAll(allCharacterNames);
        this.mapSuggestionChoice.getItems().setAll(allMapNames);
        if (!this.game.isTeammate()) {
            this.teammateSuggestionTextField.setDisable(true);
        }
        if (game.getCharactersPerSide() < 2) {
            this.playerCharSuggestionChoice2.setDisable(true);
            this.opponentCharSuggestionChoice2.setDisable(true);
        }
        if (game.getCharactersPerSide() < 3) {
            this.playerCharSuggestionChoice3.setDisable(true);
            this.opponentCharSuggestionChoice3.setDisable(true);
        }
        if (!game.isMap()) {
            this.mapSuggestionChoice.setDisable(true);
        }
    }

    //Calculates suggestions based on the user's input and displays the results to them
    //event: ActionEvent passed from a mouse click
    public void calculateSuggestions(ActionEvent event) {
        suggestTeammateList.getItems().clear();
        suggestPlayerCharList.getItems().clear();
        suggestMapList.getItems().clear();
        suggestOpponentCharList.getItems().clear();
        suggestOpponentList.getItems().clear();
        SetList tempSetList = game.getSetList().getSetListByDate();
        ArrayList<Filter> filters = new ArrayList<>();
        ArrayList<Object> filterData = new ArrayList<>();
        ArrayList<String> playerChars = new ArrayList<>();
        ArrayList<String> opponentChars = new ArrayList<>();
        String map = null;
        //BASICALLY teammate needs to take account of characters and maps
        //playerchars needs to take account of opponent chars and maps
        //map needs to take account of player chars and opponent chars
        //opponent chars needs to take account of player chars and maps
        //opponent is the same as teammate
        //pass these as arguments to the suggest functions and check the individual matches against these arguments
        if (!this.teammateSuggestionTextField.getText().equals("")) {
            filters.add(new TeammateFilter());
            filterData.add(this.teammateSuggestionTextField.getText());
        }
        if (!this.playerCharSuggestionChoice1.getSelectionModel().isEmpty()) {
            playerChars.add(this.playerCharSuggestionChoice1.getSelectionModel().getSelectedItem());
            filters.add(new PlayerCharacterFilter());
            filterData.add(
                    game.getCharacterByString(this.playerCharSuggestionChoice1.getSelectionModel().getSelectedItem()));
        }
        if (!this.playerCharSuggestionChoice2.getSelectionModel().isEmpty()) {
            playerChars.add(this.playerCharSuggestionChoice2.getSelectionModel().getSelectedItem());
            filters.add(new PlayerCharacterFilter());
            filterData.add(
                    game.getCharacterByString(this.playerCharSuggestionChoice2.getSelectionModel().getSelectedItem()));
        }
        if (!this.playerCharSuggestionChoice3.getSelectionModel().isEmpty()) {
            playerChars.add(this.playerCharSuggestionChoice3.getSelectionModel().getSelectedItem());
            filters.add(new PlayerCharacterFilter());
            filterData.add(
                    game.getCharacterByString(this.playerCharSuggestionChoice3.getSelectionModel().getSelectedItem()));
        }
        if (!this.mapSuggestionChoice.getSelectionModel().isEmpty()) {
            map = mapSuggestionChoice.getSelectionModel().getSelectedItem();
            filters.add(new MapFilter());
            filterData.add(game.getMapByString(this.mapSuggestionChoice.getSelectionModel().getSelectedItem()));
        }
        if (!this.opponentCharSuggestionChoice1.getSelectionModel().isEmpty()) {
            opponentChars.add(this.opponentCharSuggestionChoice1.getSelectionModel().getSelectedItem());
            filters.add(new OpponentCharacterFilter());
            filterData.add(game
                    .getCharacterByString(this.opponentCharSuggestionChoice1.getSelectionModel().getSelectedItem()));
        }
        if (!this.opponentCharSuggestionChoice2.getSelectionModel().isEmpty()) {
            opponentChars.add(this.opponentCharSuggestionChoice2.getSelectionModel().getSelectedItem());
            filters.add(new OpponentCharacterFilter());
            filterData.add(game
                    .getCharacterByString(this.opponentCharSuggestionChoice2.getSelectionModel().getSelectedItem()));
        }
        if (!this.opponentCharSuggestionChoice3.getSelectionModel().isEmpty()) {
            opponentChars.add(this.opponentCharSuggestionChoice3.getSelectionModel().getSelectedItem());
            filters.add(new OpponentCharacterFilter());
            filterData.add(game
                    .getCharacterByString(this.opponentCharSuggestionChoice3.getSelectionModel().getSelectedItem()));
        }
        if(!this.opponentSuggestionTextField.getText().equals("")) {
            filters.add(new OpponentFilter());
            filterData.add(this.opponentSuggestionTextField.getText());
        }
        Filter[] filterArray = (Filter[]) filters.toArray(new Filter[filters.size()]);
        Object[] filterDataArray = filterData.toArray();
        FilterList filterList = new FilterList(filterArray, filterDataArray);
        tempSetList = tempSetList.applyFilters(filterList);
        Statistics tempStatistics = new Statistics(tempSetList, game);
        if (game.isTeammate() && teammateSuggestionTextField.getText().equals("")) {
            ArrayList<String> teammateSuggestions = tempStatistics.suggestTeammates(playerChars,opponentChars,map);
            for (String suggestion : teammateSuggestions) {
                Label suggestLabel = new Label(suggestion);
                suggestTeammateList.getItems().add(suggestLabel);
            }
        }
        if ((playerCharSuggestionChoice1.getSelectionModel().isEmpty() && !playerCharSuggestionChoice1.isDisabled())
                || (playerCharSuggestionChoice2.getSelectionModel().isEmpty()
                        && !playerCharSuggestionChoice2.isDisabled())
                || (playerCharSuggestionChoice3.getSelectionModel().isEmpty()
                        && !playerCharSuggestionChoice3.isDisabled())) {
            ArrayList<String> playerCharSuggestions = tempStatistics.suggestCharacters(true,playerChars,opponentChars,map);
            for (String suggestion : playerCharSuggestions) {
                suggestPlayerCharList.getItems().add(new Label(suggestion));
            }
        }
        if ((opponentCharSuggestionChoice1.getSelectionModel().isEmpty() && !opponentCharSuggestionChoice1.isDisabled())
                || (opponentCharSuggestionChoice2.getSelectionModel().isEmpty()
                        && !opponentCharSuggestionChoice2.isDisabled())
                || (opponentCharSuggestionChoice3.getSelectionModel().isEmpty()
                        && !opponentCharSuggestionChoice3.isDisabled())) {
            ArrayList<String> opponentCharSuggestions = tempStatistics.suggestCharacters(false,playerChars,opponentChars,map);
            for (String suggestion : opponentCharSuggestions) {
                suggestOpponentCharList.getItems().add(new Label(suggestion));
            }
        }
        if (game.isMap() && mapSuggestionChoice.getSelectionModel().isEmpty()) {
            ArrayList<String> mapSuggestions = tempStatistics.suggestMaps(playerChars,opponentChars,map);
            for (String suggestion : mapSuggestions) {
                Label suggestLabel = new Label(suggestion);
                suggestMapList.getItems().add(suggestLabel);
            }
        }
        if (opponentSuggestionTextField.getText().equals("")) {
            ArrayList<String> opponentSuggestions = tempStatistics.suggestOpponents(playerChars,opponentChars,map);
            for (String suggestion : opponentSuggestions) {
                Label suggestLabel = new Label(suggestion);
                suggestOpponentList.getItems().add(suggestLabel);
            }
        }
    }

    //Clears the suggestions entry fields
    //event: ActionEvent passed from a mouse click
    public void clearSuggestions(ActionEvent event) {
        teammateSuggestionTextField.clear();
        opponentSuggestionTextField.clear();
        playerCharSuggestionChoice1.getSelectionModel().clearSelection();
        playerCharSuggestionChoice2.getSelectionModel().clearSelection();
        playerCharSuggestionChoice3.getSelectionModel().clearSelection();
        opponentCharSuggestionChoice1.getSelectionModel().clearSelection();
        opponentCharSuggestionChoice2.getSelectionModel().clearSelection();
        opponentCharSuggestionChoice3.getSelectionModel().clearSelection();
        mapSuggestionChoice.getSelectionModel().clearSelection();
        suggestTeammateList.getItems().clear();
        suggestPlayerCharList.getItems().clear();
        suggestMapList.getItems().clear();
        suggestOpponentCharList.getItems().clear();
        suggestOpponentList.getItems().clear();
    } 
}
