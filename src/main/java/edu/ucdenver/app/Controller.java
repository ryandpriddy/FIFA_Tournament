package edu.ucdenver.app;

import edu.ucdenver.server.Server;
import edu.ucdenver.tournament.Match;
import edu.ucdenver.tournament.Team;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    public ComboBox cbxSelectMatch;
    public Button btnCreateNewMatch;
    public Label lblTeamAScore;
    public TextField txtTeamAScore;
    public Label lblTeamBScore;
    public TextField txtTeamBScore;
    public Button btnRecordScore;
    public Label lblTeamALineUp;
    public ComboBox cbxTeamAPlayers;
    public ComboBox cbxTeamBPlayers;
    public ListView lstTeamAPlayers;
    public Label lblTeamBLineUp;
    public AnchorPane apnNewMatch;
    public ImageView btnCloseNewMatch;
    public AnchorPane apnRefereeMatch;
    public ImageView btnCloseRefereeMatch;
    public Button btnRefereeMatch;
    public ListView lstTeamBPlayers;
    public ImageView btnRemovePlayerFromLUB;
    public ImageView btnAddPlayerToLineUpB;
    public ImageView btnRemovePlayerFromLUA;
    public ImageView btnAddPlayerToLineUpA;
    public DatePicker dtpNewMatchStartDate;
    public ComboBox cbxNewMatchTeamAName;
    public ComboBox cbxNewMatchTeamBName;
    public ChoiceBox cbxNewMatchHr;
    public ChoiceBox cbxNewMatchSec;
    public ChoiceBox cbxNewMatchMin;
    public ComboBox cbxTeamName;
    public AnchorPane apnCountry;
    public Button btnMatchScores;
    public Button btnUpcomingMatches;
    public Button btnDates;
    public Button btnGamesOfTeam;
    public Button btnLineups;
    public AnchorPane apnMatchScores;
    public AnchorPane apnUpcomingMatches;
    public AnchorPane apnDates;
    public AnchorPane apnGamesOfTeam;
    public AnchorPane apnLineups;
    public AnchorPane apnBackground;
    public Button btnPublic;
    public Button btnAdmin;
    public Button btnCountryPn;
    public TextField txtTeamPrint;
    public Button btnAddTeam;
    public ComboBox selCountryForTeam;
    public TextField txtCountryName;
    public Button btnAddCountry;
    public ImageView btnBackgroundPn;
    public ImageView btnAddRefereeToMatch;
    public ListView lstRegisteredCountries;
    public ComboBox cbxDelCountry;
    public Button btnDeleteCountry;
    public ListView lstRegisteredTeams;
    public ComboBox cbxDeleteTeam;
    public Button btnDeleteTeam;

    private Server server;
    private Socket clientConnection;
    private boolean isConnected;
    private BufferedReader input;
    private PrintWriter output;

    public TextField txtPlayerName;
    public DatePicker dtpPlayerDOB;
    public TextField txtPlayerHeight;
    public TextField txtPlayerWeight;
    public TextField txtTeamName;
    public Button btnAddPlayer;
    @FXML
    private Button btnMatchesPn;
    @FXML
    private Button btnPlayerPn;
    @FXML
    private Button btnTeamsPn;
    @FXML
    private Button btnTournamentPn;
    @FXML
    private AnchorPane apnMatches;
    @FXML
    private AnchorPane apnPlayers;
    @FXML
    private AnchorPane apnTeams;
    @FXML
    private AnchorPane apnTournament;
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Constructor that also creates a client socket and read/write objects for the client to use when
     *   communicating with the server
     * @throws IOException if any of the objects can't be initialized
     */
    public Controller() throws IOException {
        this.clientConnection = new Socket("127.0.0.1", 9000);
        this.isConnected = true;
        this.output = new PrintWriter(this.clientConnection.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.clientConnection.getInputStream()));
    }
    //Sets dropdown menu with all Matches in Tournament object
    public void initialize(){
        apnBackground.toFront();
    }

    /**
     * Brings a different pane in the SwitchPane to the front for display, depending on which button is pressed
     * @param event When a Button is clicked to change the tab/Pane
     */
    public void tabSwitch(ActionEvent event) {
        if(event.getSource() == btnTournamentPn) {
            apnTournament.toFront();
        } else if(event.getSource() == btnPlayerPn) {
            apnPlayers.toFront();
            //Add Player -- Team Name dropdown populate
            this.cbxTeamName.setItems(FXCollections.observableArrayList(getTeams()));
        } else if(event.getSource() == btnTeamsPn) {
            apnTeams.toFront();
            this.selCountryForTeam.setItems(FXCollections.observableArrayList(getCountries()));
            this.lstRegisteredTeams.setItems(FXCollections.observableArrayList(getTeams()));
            this.cbxDeleteTeam.setItems(FXCollections.observableArrayList(getTeams()));
        } else if(event.getSource() == btnMatchesPn) {
            apnMatches.toFront();
            //Select a Match dropdown populate
            this.cbxSelectMatch.setItems(FXCollections.observableArrayList(getMatchesTeams()));
        } else if(event.getSource() == btnCreateNewMatch) {
            apnNewMatch.toFront();
        } else if(event.getSource() == btnRefereeMatch) {
            apnRefereeMatch.toFront();
        } else if(event.getSource() == btnCountryPn) {
            apnCountry.toFront();
            this.lstRegisteredCountries.setItems(FXCollections.observableArrayList(getCountries()));
            this.cbxDelCountry.setItems(FXCollections.observableArrayList(getCountries()));
        }


    }
    /**
     * Sends a given Pane in the SwitchPane to the back, depending on which button is pressed
     * @param mouseEvent When the mouse is clicked
     */
    public void exitSubMenu(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnCloseNewMatch) {
            apnNewMatch.toBack();
        } else if (mouseEvent.getSource() == btnCloseRefereeMatch) {
            apnRefereeMatch.toBack();
        }
    }
    /**
     * Moves background pane to front
     * @param mouseEvent When the mouse is clicked
     */
    public void homeBackground(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == btnBackgroundPn) {
            apnBackground.toFront();
        }
    }
    /**
     * Opens admin application
     * @param event When Admin is selected
     */
    public void switchChoosetoAdmin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("tournament_admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Opens public application
     * @param event When Public is selected
     */
    public void switchChoosetoGenPub(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GenPub.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //function for when the Match is selected, it will populate the Scene with data
    public void onMatchSelect(ActionEvent actionEvent){
        //Match selector dropdown
        String output = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
        String teamA = output.substring(0, output.indexOf("v")).strip();
        String teamB = output.substring(output.indexOf("."), output.indexOf("@")).replace(". ", "").strip();
        String date = output.substring(output.indexOf("@")).replace("@ ", "").replace(" ", "T");

        //Match score Teams label
        lblTeamAScore.setText(teamA);
        lblTeamBScore.setText(teamB);
        String[] matchScore = getMatchScore(date);
        txtTeamAScore.setText(matchScore[0]);
        txtTeamBScore.setText(matchScore[1]);
        //List View LineUp header labels
        lblTeamALineUp.setText(teamA);
        lblTeamBLineUp.setText(teamB);
        cbxTeamAPlayers.setItems(FXCollections.observableArrayList(getSquad(teamA)));
        cbxTeamBPlayers.setItems(FXCollections.observableArrayList(getSquad(teamB)));
        //List View LineUps
        ArrayList<ArrayList<String>> lineUps = getLineUp(date);
        lstTeamAPlayers.setItems(FXCollections.observableArrayList(getLineUp(date).get(0))); //TeamA lineup
        lstTeamBPlayers.setItems(FXCollections.observableArrayList(getLineUp(date).get(1))); //TeamB lineup
    }
    public void onNewMatchSelect(MouseEvent mouseEvent){
        //Add New Match Dropdown Population
        ArrayList<String> hrs = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11","12"));
        ArrayList<String> min = new ArrayList<>(Arrays.asList("00", "15", "30", "45"));
        ArrayList<String> sec = new ArrayList<>(Arrays.asList("00", "15", "30", "45"));
        this.cbxNewMatchHr.setItems(FXCollections.observableArrayList(hrs));
        this.cbxNewMatchMin.setItems(FXCollections.observableArrayList(min));
        this.cbxNewMatchSec.setItems(FXCollections.observableArrayList(sec));

        this.cbxNewMatchTeamAName.setItems(FXCollections.observableArrayList(getTeams()));
        this.cbxNewMatchTeamBName.setItems(FXCollections.observableArrayList(getTeams()));
    }
    //Getters
    public ArrayList<String> getCountries(){
        ArrayList<String> countries = new ArrayList<>();
        try {
            String request = "getCountries";
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                String[] splitResponse = response.split(",");
                for (String country: splitResponse) {          //type conversion from String[] to ArrayList<String>
                    countries.add(country);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return countries;
    }
    public ArrayList<String> getSquad(String teamName){
        ArrayList<String> squad = new ArrayList<>();
        try {
            String request = "getSquad," + teamName;
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                String[] squadMembers = response.split("/");
                for (String member: squadMembers) {                  //',' char delimiter, split to get individual attributes
                    String [] memberData = member.split(",");
                    for (int i = 0; i < memberData.length; i++){ //Extract: 'David Beckham' substring from "Name: David Beckham"
                        if (memberData[i].contains("Name:")){
                            squad.add(memberData[i].replace("Name: ", "")); //Add to an ArrayList<String> for ObservableArrayList JavaFX collection
                        }
                    }
                }
            }
            catch (Exception e){
                    e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        for (String s: squad){
            System.out.println(s);
        };
        return squad;
    }
    public ArrayList<String> getMatchesTeams(){
        ArrayList<String> matchSelections = new ArrayList<>();
        try {
            String request = "getMatches,";
            output.println(request);

            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                String[] matches = response.split("##"); //'##" char delimiter -- Split string of all Matches [match1, match2, match3, match4]
                for (String match: matches){
                    System.out.printf("Match|%s%n", match);
                    String[] teams = match.split(", "); //',' char delimiter -- get individual Match attributes
                    //Create "USA vs England" string for presentation
                    matchSelections.add(String.format("%s vs. %s @ %s", teams[1].replace("TeamA: ", ""),
                            teams[2].replace("TeamB: ", ""), teams[0].replace("T", " ").replace("Date: ", "")));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return matchSelections;
    }
    public String[] getMatchScore(String dateTime) {
        String[] scores = null;
        try {
            String request = "getMatchScore," + dateTime;
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                scores = response.split(",");
                txtTeamAScore.setText(scores[0]);
                txtTeamBScore.setText(scores[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scores;
    }
    public ArrayList<String> getTeams(){
        ArrayList<String> teams = new ArrayList<>();
        try {
            String request = "getTeams";
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                String[] splitResponse = response.split(",");
                for (String team: splitResponse) {
                    teams.add(team);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return teams;

    }
    public ArrayList<ArrayList<String>> getLineUp(String dateTime) {
        ArrayList<ArrayList<String>> lineUps = new ArrayList<>();
        try {
            String request = "getLineUp," + dateTime;
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                if (response.equalsIgnoreCase("@")){
                    lineUps.add(new ArrayList<String>());
                    lineUps.add(new ArrayList<String>());
                }
                else {
                    String[] teams = response.split("@"); //splits 2 teams into [teamA LineUp, teamB LineUp (.toString)]
                    try {
                        String[] teamAplayers = teams[0].split("/"); //splits each team's players
                        String[] teamBplayers = teams[1].split("/");
                        ArrayList<String> teamAList = new ArrayList<>(); //Java FX ObservableCollection's requiring ArrayList. Must type-cast.
                        ArrayList<String> teamBList = new ArrayList<>();
                        //convert String[] array to ArrayList
                        for (String player : teamAplayers) {
                            teamAList.add(player);
                        }
                        for (String player : teamBplayers) {
                            teamBList.add(player);
                        }
                        //Add ArrayList<String> to ArrayList container
                        lineUps.add(teamAList);
                        lineUps.add(teamBList);
                    } catch (ArrayIndexOutOfBoundsException e) { //This occurs when TeamB has its entire LineUp deleted
                        ArrayList<String> teamList = new ArrayList<>();
                        for (String player : teamList) {
                            teamList.add(player);
                        }
                        lineUps.add(teamList);  //Type cast Exception case to ArrayList
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return lineUps;
    }
    public ArrayList<String> getReferees(){
        ArrayList<String> refereeList = new ArrayList<>();
        try {
            String request = "getReferees,";
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                String[] referees = response.split(",");
                for (String ref: referees){ //Type cast String[] from split() return to ArrayList
                    refereeList.add(ref);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return refereeList;
    }
    //Setters
    public void recordMatchScore(ActionEvent actionEvent) {
        try {
            String selection = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
            String date = selection.substring(selection.indexOf("@")).replace("@ ", "").replace(" ", "T");
            //Example Request String format is: "setMatchScore,US National Team,2,France National Team,0,dateTime"
            String request = "setMatchScore," +
                    lblTeamAScore.getText() + "," +
                    txtTeamAScore.getText() + "," +
                    lblTeamBScore.getText() + "," +
                    txtTeamBScore.getText() + "," +
                    date;
            output.println(request);

            //Get response from Server
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                Alert alert;
                alert = new Alert(Alert.AlertType.CONFIRMATION, "New score successfully recorded!", ButtonType.OK);
                alert.show();
                //Set score presentation
                txtTeamAScore.setText(txtTeamAScore.getText());
                txtTeamBScore.setText(txtTeamBScore.getText());
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK);
            alert.showAndWait();
        }
    }
    //Adders
    public void addReferee (ActionEvent actionEvent) {
        try {
            //name =
            //country =
            //String request = "addReferee," + name + "," + country;
            //output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                if (response.equalsIgnoreCase("Success")) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("%s successfully added!", response), ButtonType.OK);
                    alert.show();
                } else if (response.equalsIgnoreCase("Found")) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR, String.format("Referee already in system", response), ButtonType.OK);
                    alert.show();
                } else {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR, String.format("Referee could not be added. Please try again.", response), ButtonType.OK);
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void addPlayer(ActionEvent actionEvent){
        try {
            //Example Request String format is: "addPlayer,USA,Christian Pulisk,70,170"
            int age = 2022 - this.dtpPlayerDOB.getValue().getYear();
            String request = "addPlayer," +
                    cbxTeamName.getSelectionModel().getSelectedItem().toString() + "," +
                    txtPlayerName.getText() + "," +
                    age + "," +
                    txtPlayerHeight.getText() + "," +
                    txtPlayerWeight.getText();
            output.println(request);

            //Get response from Server
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                if (response.equalsIgnoreCase("Success")) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("%s successfully added!", response), ButtonType.OK);
                    alert.show();
                }
                else if (response.equalsIgnoreCase("Found")){
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR, String.format("Player already on team"), ButtonType.OK);
                    alert.show();
                }
                else if (response.equalsIgnoreCase("Full")){
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR, String.format("Team at full capacity, please remove a player and try again"), ButtonType.OK);
                    alert.show();
                }
                else {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR, String.format("Could not add Player, please try again"), ButtonType.OK);
                    alert.show();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void addNewMatch(ActionEvent actionEvent) {
        try {
            LocalDateTime dtp = LocalDateTime.of(dtpNewMatchStartDate.getValue(),
                    LocalTime.of(Integer.parseInt(cbxNewMatchHr.getSelectionModel().getSelectedItem().toString()),
                            Integer.parseInt(cbxNewMatchMin.getSelectionModel().getSelectedItem().toString()),
                            Integer.parseInt(cbxNewMatchSec.getSelectionModel().getSelectedItem().toString())));
            String dateTime = dtp.format(DateTimeFormatter.ISO_DATE_TIME);
            //Example Request String format is: "addNewMatch,US National Team,England National Team
            String request = "addNewMatch," + dateTime + ",";
            String teamA = (String) cbxNewMatchTeamAName.getSelectionModel().getSelectedItem() + ",";
            String teamB = (String) cbxNewMatchTeamBName.getSelectionModel().getSelectedItem() + ",";
            if (teamA.equalsIgnoreCase(teamB)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Please choose two different teams"), ButtonType.OK);
                alert.show();
            } else if (teamA == "" || teamB == "" || dateTime == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Please choose two different teams"), ButtonType.OK);
                alert.show();
            } else {
                request += teamA + teamB;
                System.out.println(request);
                output.println(request);
                //Get response from Server
                try {
                    String response = null;
                    boolean flag = true;
                    while (flag) {
                        response = input.readLine();
                        if (response != null) {
                            flag = false;
                        }
                    }
                    Alert alert;
                    alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("New match successfully added!", response), ButtonType.OK);
                    alert.show();
                    this.cbxSelectMatch.setItems(FXCollections.observableArrayList(getMatchesTeams()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void addCountry (ActionEvent actionEvent){
        try {
            String countryName = txtCountryName.getText();
            if (countryName.equalsIgnoreCase("")){
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR, String.format("Please enter a country name"), ButtonType.OK);
                alert.show();
            }
            else {
                String request = "addCountry," + countryName;
                output.println(request);
                try {
                    String response = null;
                    boolean flag = true;
                    while (flag) {
                        response = input.readLine();
                        if (response != null) {
                            flag = false;
                        }
                    }
                    if (response.equalsIgnoreCase("Success")) {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("%s successfully added!", response), ButtonType.OK);
                        alert.show();
                        this.lstRegisteredCountries.setItems(FXCollections.observableArrayList(getCountries()));
                    } else if (response.equalsIgnoreCase("Found")) {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.ERROR, String.format("Country already in system", response), ButtonType.OK);
                        alert.show();
                    } else {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.ERROR, String.format("Country could not be added. Please try again.", response), ButtonType.OK);
                        alert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void addTeam (ActionEvent actionEvent){
        try {
            String teamName = txtTeamPrint.getText().toString();
            String countryName = selCountryForTeam.getSelectionModel().getSelectedItem().toString();
            String request = "addTeam," + teamName + "," + countryName;
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                if (response.equalsIgnoreCase("Success")) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("%s successfully added to country!", response), ButtonType.OK);
                    alert.show();
                    this.lstRegisteredTeams.setItems(FXCollections.observableArrayList(getTeams()));
                } else if (response.equalsIgnoreCase("Found")) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR, String.format("Team already in system or it's Country does not exist", response), ButtonType.OK);
                    alert.show();
                } else {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR, String.format("Team could not be added. Please try again.", response), ButtonType.OK);
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void addRefereeToMatch(MouseEvent mouseEvent) {
    }
    public void addPlayerToLineUpB(MouseEvent mouseEvent) {
        try {
            String playerName = this.cbxTeamBPlayers.getSelectionModel().getSelectedItem().toString();
            String match = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
            String date = match.substring(match.indexOf("@")).replace("@ ", "").replace(" ", "T");
            String request = "addPlayerToLUB," + playerName + "," + date;
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                if (response.equalsIgnoreCase("Found")){
                    Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Player already in lineup"), ButtonType.OK);
                    alert.show();
                }
                else { //Set ListView to ArrayList<String> containing [TeamALineUp, TeamBLineUp].. .get(1) gets TeamBLineUp
                    lstTeamBPlayers.setItems(FXCollections.observableArrayList(getLineUp(date).get(1)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addPlayerToLineupA(MouseEvent mouseEvent) {
        try {
            String playerName = this.cbxTeamAPlayers.getSelectionModel().getSelectedItem().toString();
            String match = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
            String date = match.substring(match.indexOf("@")).replace("@ ", "").replace(" ", "T");
            String request = "addPlayerToLUA," + playerName + "," + date;
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                if (response.equalsIgnoreCase("Found")){
                    Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Player already in lineup"), ButtonType.OK);
                    alert.show();
                }
                else { //Set ListView to ArrayList<String> containing [TeamALineUp, TeamBLineUp].. .get(1) gets TeamALineUp
                  lstTeamAPlayers.setItems(FXCollections.observableArrayList(getLineUp(date).get(0)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Deletors
    public void deleteCountry(ActionEvent actionEvent) {
        String country = this.cbxDelCountry.getSelectionModel().getSelectedItem().toString();
        if (country.equalsIgnoreCase("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a country from dropdown menu", ButtonType.OK);
            alert.show();
        }
        else {
            try {
                String request = "deleteCountry," + country;
                output.println(request);
                try {
                    String response = null;
                    boolean flag = true;
                    while (flag) {
                        response = input.readLine();
                        if (response != null) {
                            flag = false;
                        }
                    }
                    if (response.equalsIgnoreCase("Success")) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("%s successfully deleted!", country), ButtonType.OK);
                        alert.show();
                        lstRegisteredCountries.setItems(FXCollections.observableArrayList(getCountries()));
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void deleteTeam(ActionEvent actionEvent){
        String team = this.cbxDeleteTeam.getSelectionModel().getSelectedItem().toString();
        if (team.equalsIgnoreCase("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a team from dropdown menu", ButtonType.OK);
            alert.show();
        }
        try {
            String request = "deleteTeam," + team;
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                if (response.equalsIgnoreCase("Success")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("%s successfully deleted!", team), ButtonType.OK);
                    alert.show();
                    lstRegisteredTeams.setItems(FXCollections.observableArrayList(getTeams()));
                    cbxDeleteTeam.setItems(FXCollections.observableArrayList(getTeams()));
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deletePlayer(ActionEvent actionEvent) {
        String playerName = txtPlayerName.getText();
        Object teamName = cbxTeamName.getSelectionModel().getSelectedItem();
        if (playerName.equalsIgnoreCase("") || teamName == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter the Player name and Team name", ButtonType.OK);
            alert.show();
        }
        else {
            try {
                String request = "deletePlayer," + playerName + "," + teamName.toString();
                output.println(request);
                try {
                    String response = null;
                    boolean flag = true;
                    while (flag) {
                        response = input.readLine();
                        if (response != null) {
                            flag = false;
                        }
                    }
                    if (response.equalsIgnoreCase("Success")) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("%s successfully deleted!", playerName), ButtonType.OK);
                        alert.show();
                    } else if (response.equalsIgnoreCase("Fail")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, String.format("%s not found on %s", playerName, teamName), ButtonType.OK);
                        alert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void removePlayerFromLUA(MouseEvent mouseEvent) {
        try {
            String playerName = this.cbxTeamAPlayers.getSelectionModel().getSelectedItem().toString();
            String match = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
            String date = match.substring(match.indexOf("@")).replace("@ ", "").replace(" ", "T");
            String request = "removePlayerFromLUA," + playerName + "," + date;
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                try { //Set ListView to ArrayList<String> containing [TeamALineUp, TeamBLineUp].. .get(1) gets TeamBLineUp
                    lstTeamAPlayers.setItems(FXCollections.observableArrayList(getLineUp(date).get(0)));
                } catch (IndexOutOfBoundsException e){ //when removing creates empty TeamALineUp
                    lstTeamAPlayers.setItems(FXCollections.observableArrayList());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removePlayerFromLUB(MouseEvent mouseEvent) {
        try {
            String playerName = this.cbxTeamBPlayers.getSelectionModel().getSelectedItem().toString();
            String match = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
            String date = match.substring(match.indexOf("@")).replace("@ ", "").replace(" ", "T");
            String request = "removePlayerFromLUB," + playerName + "," + date;
            output.println(request);
            try {
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                try { //Set ListView to ArrayList<String> containing [TeamALineUp, TeamBLineUp].. .get(1) gets TeamBLineUp
                    lstTeamBPlayers.setItems(FXCollections.observableArrayList(getLineUp(date).get(1)));
                } catch (IndexOutOfBoundsException e){ //when removing creates empty TeamBLineUp
                    lstTeamBPlayers.setItems(FXCollections.observableArrayList());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}