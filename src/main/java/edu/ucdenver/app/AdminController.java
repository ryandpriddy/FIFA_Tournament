package edu.ucdenver.app;

import edu.ucdenver.server.Server;
import edu.ucdenver.tournament.Referee;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class AdminController {

    public AnchorPane apnNewMatch;
    public AnchorPane apnRefereeMatch;
    public AnchorPane apnReferees;
    public AnchorPane apnCountry;
    public AnchorPane apnBackground;
    public ImageView btnRemovePlayerFromLUB;
    public ImageView btnAddPlayerToLineUpB;
    public ImageView btnRemovePlayerFromLUA;
    public ImageView btnAddPlayerToLineUpA;
    public ImageView btnCloseRefereeMatch;
    public ImageView btnBackgroundPn;
    public Button btnAddRefereeToMatch;
    public ImageView btnCloseNewMatch;
    public ComboBox cbxTeamName;
    public ComboBox cbxSelectMatch;
    public ComboBox cbxTeamAPlayers;
    public ComboBox cbxTeamBPlayers;
    public ComboBox cbxNewMatchTeamAName;
    public ComboBox cbxNewMatchTeamBName;
    public ComboBox selCountryForTeam;
    public ComboBox cbxDelCountry;
    public ComboBox cbxTournamentList;
    public ComboBox selCountryForReferees;
    public ComboBox cbxRefsForMatchAdd;
    public ComboBox cbxRefsForMatchDel;
    public ComboBox cbxDeleteTeam;
    public ComboBox cbxNewMatchReferees;
    public ChoiceBox cbxNewMatchHr;
    public ChoiceBox cbxNewMatchSec;
    public ChoiceBox cbxNewMatchMin;
    public DatePicker dtpNewMatchStartDate;
    public DatePicker dtpPlayerDOB;
    public TextField txtTeamAScore;
    public TextField txtTeamBScore;
    public TextField txtTeamPrint;
    public TextField txtNewTournamentName;
    public TextField txtPlayerHeight;
    public TextField txtPlayerWeight;
    public TextField txtPlayerName;
    public TextField txtCountryName;
    public Button btnAddCountry;
    public TextField txtRefereePrint;
    public ListView lstTeamAPlayers;
    public ListView lstTeamBPlayers;
    public ListView lstRegisteredCountries;
    public ListView lstRegTeams;
    public ListView lstRegReferees;
    public ListView lstRegisteredTeams;
    public ListView lstMatchReferees;
    public ListView lstNewMatchReferees;
    public Button btnCreateNewMatch;
    public Button btnRecordScore;
    public Button btnRefereeMatch;
    public Button btnCountryPn;
    public Button btnAddTeam;
    public Button btnDeleteCountry;
    public Button btnRefereePn;
    public Button btnNewTournament;
    public Button btnAddReferee;
    public Button btnAddRefToMatch;
    public Button btnDelRefFromMatch;
    public Button btnDeleteTeam;
    public Button btnAddNewReferee;
    public Button btnDeletePlayer;
    public Label lblTeamAScore;
    public Label lblTeamBScore;
    public Label lblTeamALineUp;
    public Label lblTeamBLineUp;
    public Label txtMatchRef1;
    public Label txtMatchRef2;
    public Label txtMatchRef3;
    public Label txtMatchRef4;
    public Label txtMatchRefC1;
    public Label txtMatchRefC2;
    public Label txtMatchRefC3;
    public Label txtMatchRefC4;
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


    private Server server;
    private Socket clientConnection;
    private boolean isConnected;
    private BufferedReader input;
    private PrintWriter output;

    /**
     * Constructor that also creates a client socket and read/write objects for the client to use when
     *   communicating with the server
     * @throws IOException if any of the objects can't be initialized
     */
    public AdminController() throws IOException {
        this.clientConnection = new Socket("127.0.0.1", 9000);
        this.isConnected = true;
        this.output = new PrintWriter(this.clientConnection.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.clientConnection.getInputStream()));
    }
    /**
     * Brings background pane to front during initializing
     */
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
            updateTeamList();
        } else if(event.getSource() == btnTeamsPn) {
            apnTeams.toFront();
            updateTeamList();
            updateCountryList();
        } else if(event.getSource() == btnMatchesPn) {
            apnMatches.toFront();
            updateTeamList();
            updateMatchList();
        } else if(event.getSource() == btnCreateNewMatch) {
            apnNewMatch.toFront();
            updateMatchList();
        } else if(event.getSource() == btnRefereeMatch) {
            apnRefereeMatch.toFront();
            updateRefereeList();
            updateMatchRefList();
        } else if(event.getSource() == btnCountryPn) {
            apnCountry.toFront();
            updateCountryList();
        } else if(event.getSource() == btnRefereePn) {
            apnReferees.toFront();
            updateRefereeList();
        }


    }

    /**
     * Sends a given Pane in the SwitchPane to the back, depending on which button is pressed
     * @param mouseEvent When the mouse is clicked
     */
    public void exitSubMenu(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnCloseNewMatch) {
            lstNewMatchReferees.getItems().clear();
            updateMatchList();
            apnNewMatch.toBack();
        } else if (mouseEvent.getSource() == btnCloseRefereeMatch) {
            apnRefereeMatch.toBack();
        }
    }

    /**
     * Fill details of match that was clicked on
     * @param actionEvent When combobox selection clicked for existing match
     */
    public void onMatchSelect(ActionEvent actionEvent){
        //Match selector dropdown
        String output = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
        if (output != null) {
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
            lstTeamAPlayers.setItems(FXCollections.observableArrayList(lineUps.get(0))); //TeamA lineup
            lstTeamBPlayers.setItems(FXCollections.observableArrayList(lineUps.get(1))); //TeamB lineup
        }
    }
    /**
     * Update comboboxes on match pop-up pane
     * @param mouseEvent When Create New Match selected
     */
    public void onNewMatchSelect(MouseEvent mouseEvent){
        //Add New Match Dropdown Population
        ArrayList<String> hrs = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11","12"));
        ArrayList<String> min = new ArrayList<>(Arrays.asList("00", "15", "30", "45"));
        ArrayList<String> sec = new ArrayList<>(Arrays.asList("00", "15", "30", "45"));
        this.cbxNewMatchHr.setItems(FXCollections.observableArrayList(hrs));
        this.cbxNewMatchMin.setItems(FXCollections.observableArrayList(min));
        this.cbxNewMatchSec.setItems(FXCollections.observableArrayList(sec));
        ArrayList<String> teamList = getTeams();
        // Match tab - Selection boxes to choose teams for the Add Match function
        this.cbxNewMatchTeamAName.setItems(FXCollections.observableArrayList(teamList));
        this.cbxNewMatchTeamBName.setItems(FXCollections.observableArrayList(teamList));
        this.cbxNewMatchReferees.setItems(FXCollections.observableArrayList(getReferees()));
    }
    /**
     * Getter to retrieve updated list of countries
     * @return countries list
     */
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
    /**
     * Getter to retrieve updated list of team players
     * @return squad
     */
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
                String[] squadMembers = response.split("/");    //'/' char delimiter, split to get individuals
                for (String member: squadMembers) {                  //',' char delimiter, split to get individual attributes
                    String [] memberData = member.split(",");
                    for (int i = 0; i < memberData.length; i++){ //Extract: 'David Beckham' substring from "Name: David Beckham"
                        if (memberData[i].contains("Name:") && !memberData[i].contains("Team") && !memberData[i].contains("Country")){
                            squad.add(memberData[i].replace("Name:", "").stripLeading()); //Add to an ArrayList<String> for ObservableArrayList JavaFX collection
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
        return squad;
    }
    /**
     * Getter to retrieve matches for teams
     * @return matchSelection
     */
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
    /**
     * Getter to retrieve score of matches
     * @return scores
     */
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
    /**
     * Getter to retrieve updated list of teams
     * @return teams
     */
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
    /**
     * Getter to retrieve updated list of team lineups
     * @return lineUps
     */
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
                        String[] teamAplayers = teams[0].split("/"); //splits each team's players
                        for (String player : teamAplayers) {
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
        //return ArrayList<ArrayList<String>>
        return lineUps;
    }
    /**
     * Getter to retrieve updated list of Referees
     * @return refereeList
     */
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
                    String[] refSplit = ref.split(":");
                    String formattedRef = String.format("%-10s - %s",refSplit[0],refSplit[1]);
                    refereeList.add(formattedRef);
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
    /**
     * Getter to retrieve updated refs of matches
     * @return refereeList
     */
    public ArrayList<String> getRefsForMatch() {
        ArrayList<String> refereeList = new ArrayList<>();
        String match = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
        String date = match.substring(match.indexOf("@")).replace("@ ", "").replace(" ", "T");
        try {
            String request = "getRefsForMatch," + date;
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
                String[] refsData = response.split(",");
                for (String refEntry: refsData){ //Type cast String[] from split() return to ArrayList
                    refereeList.add(refEntry);
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
    /**
     * Setter to record score of match
     * @param actionEvent when Update Score selected
     */
    public void recordMatchScore(ActionEvent actionEvent) {
        try {
            String selection = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
            String date = selection.substring(selection.indexOf("@")).replace("@ ", "").replace(" ", "T");
            //Example Request String format is: "setMatchScore,US National Team,2,France National Team,0"
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
    /**
     * Add referee to tournament
     * @param actionEvent when Add Referee selected
     */
    public void addReferee (ActionEvent actionEvent) {
        try {
            String name = txtRefereePrint.getText();
            String country = selCountryForReferees.getValue().toString();
            String request = "addReferee," + name + "," + country;
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
                    updateRefereeList();
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
    /**
     * Add player to tournament
     * @param actionEvent when Register New Player selected
     */
    public void addPlayer(ActionEvent actionEvent){
        try {
            //Example Request String format is: "addPlayer,USA,Christian Pulisk,70,170"
            int age = LocalDateTime.now().getYear() - this.dtpPlayerDOB.getValue().getYear();
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
    /**
     * Add new match to tournament
     * @param actionEvent when Add Match selected
     */
    public void addNewMatch(ActionEvent actionEvent) {
        try {
            LocalDateTime dtp = LocalDateTime.of(dtpNewMatchStartDate.getValue(),
            LocalTime.of(Integer.parseInt(cbxNewMatchHr.getSelectionModel().getSelectedItem().toString()),
                        Integer.parseInt(cbxNewMatchMin.getSelectionModel().getSelectedItem().toString()),
                        Integer.parseInt(cbxNewMatchSec.getSelectionModel().getSelectedItem().toString())));
            String dateTime = dtp.format(DateTimeFormatter.ISO_DATE_TIME);
            String teamA = (String) cbxNewMatchTeamAName.getSelectionModel().getSelectedItem() + ",";
            String teamB = (String) cbxNewMatchTeamBName.getSelectionModel().getSelectedItem() + ",";
            //Example Request String format is: "addNewMatch,US National Team,England National Team
            String request = "addNewMatch," + dateTime + ",";
            if (teamA.equalsIgnoreCase(teamB)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Please choose two different teams"), ButtonType.OK);
                alert.show();
            } else if (teamA == "" || teamB == "" || dateTime == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Please choose two different teams"), ButtonType.OK);
                alert.show();
            } else if (lstNewMatchReferees.getItems().size() != 4){
                Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Please add 4 referees to Match"), ButtonType.OK);
                alert.show();
            } else {
                request += teamA + teamB;
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
                    if (response.equalsIgnoreCase("Fail")) {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.ERROR, String.format("Match already exists at that Date and Time"), ButtonType.OK);
                        alert.show();
                    } else {
                        //Now to add Referee from lstNewMatchReferee to newly created Match object
                        try {
                            ArrayList<String> referees = new ArrayList<>();
                            for (Object ref : this.lstNewMatchReferees.getItems()){
                                String refRequest = "addRefereeToNewMatch,";
                                refRequest += dateTime + ",";
                                refRequest += ref.toString();
                                refRequest = refRequest.replace("      - ", ","); //remove formatting
                                output.println(refRequest);
                                try {
                                    response = null;
                                    flag = true;
                                    while (flag) {
                                        response = input.readLine();
                                        if (response != null) {
                                            flag = false;
                                        }
                                    }
                                    if (response.equalsIgnoreCase("Fail")) {
                                        Alert alert;
                                        alert = new Alert(Alert.AlertType.ERROR, String.format("Referee List could not be added to new Match"), ButtonType.OK);
                                        alert.show();
                                    }
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //addMatch successful, addRefereeToMatch successful, show Client a Success message
                        Alert alert;
                        alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("New match successfully added!", response), ButtonType.OK);
                        alert.show();
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch(Exception e){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK);
            alert.showAndWait();
        }
    }
    /**
     * Add new country to tournament
     * @param actionEvent when Register Country selected
     */
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
                        updateCountryList();
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
    /**
     * Add new team to tournament
     * @param actionEvent when Add Team selected
     */
    public void addTeam (ActionEvent actionEvent){
        try {
            String countryName = selCountryForTeam.getSelectionModel().getSelectedItem().toString();
            String teamName = countryName + " National Team";
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
                    this.lstRegTeams.setItems(FXCollections.observableArrayList(getTeams()));
                    this.cbxDeleteTeam.setItems(FXCollections.observableArrayList(getTeams()));
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
    /**
     * Add referee to match
     * @param actionEvent when Add to Match selected
     */
    public void addNewMatchReferee(ActionEvent actionEvent) {
        String[] selection = this.cbxNewMatchReferees.getSelectionModel().getSelectedItem().toString().split("-");
        ArrayList<String> refereeList = new ArrayList<>();
        selection[1] = selection[1].replace(" ", ""); //Strip leading whitespace
        selection[1] += " National Team"; //Add 'National Team' for Country conflict comparison
        String teamA = cbxNewMatchTeamAName.getSelectionModel().getSelectedItem().toString();
        String teamB = cbxNewMatchTeamBName.getSelectionModel().getSelectedItem().toString();
        for (Object ref : this.lstNewMatchReferees.getItems()){
            refereeList.add(ref.toString());
        }
        if (refereeList.size() < 4){ //No more than 4 Refs per Match
            if (!selection[1].equalsIgnoreCase(teamA) && !selection[1].equalsIgnoreCase(teamB)) {
                refereeList.add(this.cbxNewMatchReferees.getSelectionModel().getSelectedItem().toString());
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Referee's Country of Origin cannot be same as either Team", ButtonType.OK);
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Only four referees can be selected per Match!", ButtonType.OK);
            alert.show();
        }
        this.lstNewMatchReferees.setItems(FXCollections.observableArrayList(refereeList));
    }
    /**
     * Add referee to match
     * @param mouseEvent when Add Referee selected
     */
    public void addRefereeToMatch(ActionEvent mouseEvent) {
        String refName = this.cbxRefsForMatchAdd.getSelectionModel().getSelectedItem().toString();
        String match = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
        String date = match.substring(match.indexOf("@")).replace("@ ", "").replace(" ", "T");
        if (!txtMatchRef4.getText().equalsIgnoreCase("Referee Name") && !txtMatchRefC4.getText().equalsIgnoreCase("Country Name")){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, String.format("Please delete an existing Referee from Match before adding a new one."), ButtonType.OK);
            alert.show();
        }
        else {
            try {
                String request = "addRefereeToMatch," + date + "," + refName;
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
                        alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("Referee successfully added!"), ButtonType.OK);
                        alert.show();
                        updateMatchRefList();
                    } else {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.ERROR, response, ButtonType.OK);
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
    /**
     * Add player to Lineup B
     * @param mouseEvent when + selected
     */
    public void addPlayerToLineUpB(MouseEvent mouseEvent) {
        try {
            String playerName = this.cbxTeamBPlayers.getSelectionModel().getSelectedItem().toString().stripLeading();
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
    /**
     * Add player to Lineup A
     * @param mouseEvent when + selected
     */
    public void addPlayerToLineupA(MouseEvent mouseEvent) {
        try {
            String playerName = this.cbxTeamAPlayers.getSelectionModel().getSelectedItem().toString().stripLeading();
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

    /**
     * Delete country from tournament
     * @param actionEvent when Delete selected
     */
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
    /**
     * Delete team from tournament
     * @param actionEvent when Delete selected
     */
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
                    lstRegTeams.setItems(FXCollections.observableArrayList(getTeams()));
                    cbxDeleteTeam.setItems(FXCollections.observableArrayList(getTeams()));
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Delete player from tournament
     * @param actionEvent when Delete selected
     */
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
    /**
     * Delete referee from tournament
     * @param actionEvent when Delete selected
     */
    public void delRefFromMatch(ActionEvent actionEvent) {
        String[] refInfo = this.cbxRefsForMatchDel.getSelectionModel().getSelectedItem().toString().split("-");
        String refName = refInfo[0];
        String match = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
        String dateTime = match.substring(match.indexOf("@")).replace("@ ", "").replace(" ", "T");
        String request = "removeRefFromMatch," + refName + "," + dateTime;
        if (refName.equalsIgnoreCase("Referee Name")){
            Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Please add a new Referee instead of deleting the default Referees"), ButtonType.OK);
            alert.show();
        }
        else {
            try {
                output.println(request);
                String response = null;
                boolean flag = true;
                while (flag) {
                    response = input.readLine();
                    if (response != null) {
                        flag = false;
                    }
                }
                if (response.equalsIgnoreCase("Success")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("%s successfully deleted!", refName), ButtonType.OK);
                    alert.show();
                    cbxRefsForMatchDel.setItems(FXCollections.observableArrayList(getRefsForMatch()));
                    if (txtMatchRef1.getText().equalsIgnoreCase(refName)) {
                        txtMatchRef1.setText("Referee Name");
                        txtMatchRefC1.setText("Country Name");
                    } else if (txtMatchRef2.getText().equalsIgnoreCase(refName)) {
                        txtMatchRef2.setText("Referee Name");
                        txtMatchRefC2.setText("Country Name");
                    } else if (txtMatchRef3.getText().equalsIgnoreCase(refName)) {
                        txtMatchRef3.setText("Referee Name");
                        txtMatchRefC3.setText("Country Name");
                    } else if (txtMatchRef4.getText().equalsIgnoreCase(refName)) {
                        txtMatchRef4.setText("Referee Name");
                        txtMatchRefC4.setText("Country Name");
                    } else {
                        System.out.println("Reset failed");
                    }
                } else if (response.equalsIgnoreCase("Fail")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, String.format("%s not found in Tournament", refName), ButtonType.OK);
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Delete player from Lineup A
     * @param mouseEvent when - selected
     */
    public void removePlayerFromLUA(MouseEvent mouseEvent) {
        try {
            String playerName = this.cbxTeamAPlayers.getSelectionModel().getSelectedItem().toString();
            String match = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
            String date = match.substring(match.indexOf("@")).replace("@ ", "").replace(" ", "T");
            String request = "removePlayerFromLUA," + playerName.stripLeading() + "," + date;
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
    /**
     * Delete player from Lineup B
     * @param mouseEvent when - selected
     */
    public void removePlayerFromLUB(MouseEvent mouseEvent) {
        try {
            String playerName = this.cbxTeamBPlayers.getSelectionModel().getSelectedItem().toString();
            String match = (String) cbxSelectMatch.getSelectionModel().getSelectedItem();
            String date = match.substring(match.indexOf("@")).replace("@ ", "").replace(" ", "T");
            String request = "removePlayerFromLUB," + playerName.stripLeading() + "," + date;
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

    /**
     * Change the active pane to the Home/Welcome Background
     * @param mouseEvent when button pointing to apnBackground is clicked
     */
    public void homeBackground(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == btnBackgroundPn) {
            apnBackground.toFront();
        }
    }

    /**
     * Sends a request to the server to update the countryList and then passes it to each field that needs it.
     */
    private void updateCountryList() {
        ArrayList<String> countryList = getCountries();
        //Country Tab -> display list of registered countries
        this.lstRegisteredCountries.setItems(FXCollections.observableArrayList(countryList));
        // Country Tab -> selection tab to delete registered countries
        this.cbxDelCountry.setItems(FXCollections.observableArrayList(countryList));
        // Team Tab -> country selection list for Add Team function
        this.selCountryForTeam.setItems(FXCollections.observableArrayList(countryList));
        // Referee Tab -> country selection list for Add Referee function
        this.selCountryForReferees.setItems(FXCollections.observableArrayList(countryList));

    }

    /**
     * Sends a request to update the list of registered teams and passes it everywhere that needs it.
     */
    private void updateTeamList() {
        ArrayList<String> teamList = getTeams();
        // Match tab - Selection boxes to choose teams for the Add Match function
        this.cbxNewMatchTeamAName.setItems(FXCollections.observableArrayList(teamList));
        this.cbxNewMatchTeamBName.setItems(FXCollections.observableArrayList(teamList));
        // Player Tab -> Add Player -- Team Name dropdown populate
        this.cbxTeamName.setItems(FXCollections.observableArrayList(teamList));
        // Team Tab -> Display a list of all registered teams
        this.lstRegTeams.setItems(FXCollections.observableArrayList(teamList));
        this.cbxDeleteTeam.setItems(FXCollections.observableArrayList(teamList));
    }

    /**
     * Sends a request to update the list of registered referees and passes it everywhere that needs it.
     */
    private void updateRefereeList() {
        ArrayList<String> refList = getReferees();
        // Referee Tab -> display list of registered referees
        this.lstRegReferees.setItems(FXCollections.observableArrayList(refList));
        // Match Ref Tab -> selection list of refs to add to a match
        this.cbxRefsForMatchAdd.setItems(FXCollections.observableArrayList(refList));
    }
    /**
     * Sends a request to update the list of matches and sets new items
     */
    private void updateMatchList() {
        ArrayList<String> matchList = getMatchesTeams();
        this.cbxSelectMatch.setItems(FXCollections.observableArrayList(matchList));
    }
    /**
     * Sends a request to update the list of referees and sets new items
     */
    private void updateMatchRefList() {
        ArrayList<String> refMatchList = getRefsForMatch();
        // Match Ref Tab -> Select a Match to add refs to
        this.cbxSelectMatch.setItems(FXCollections.observableArrayList(getMatchesTeams()));
        this.cbxRefsForMatchDel.setItems(FXCollections.observableArrayList(refMatchList));
        //loop not used since all the text fields are different
        String refRow = refMatchList.get(0);
        String[] refLabels = refRow.split("-");
        txtMatchRef1.setText(refLabels[0].strip());
        txtMatchRefC1.setText(refLabels[1].strip());
        refRow = refMatchList.get(1);
        refLabels = refRow.split("-");
        txtMatchRef2.setText(refLabels[0].strip());
        txtMatchRefC2.setText(refLabels[1].strip());
        refRow = refMatchList.get(2);
        refLabels = refRow.split("-");
        txtMatchRef3.setText(refLabels[0].strip());
        txtMatchRefC3.setText(refLabels[1].strip());
        refRow = refMatchList.get(3);
        refLabels = refRow.split("-");
        txtMatchRef4.setText(refLabels[0].strip());
        txtMatchRefC4.setText(refLabels[1].strip());
    }


    // TODO addTournament
    public void addTournament(ActionEvent actionEvent) {
    }
}