package edu.ucdenver.app;

import edu.ucdenver.server.Server;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.time.LocalDateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class PublicController {
    public AnchorPane apnWelcome;
    public AnchorPane apnMatchScores;
    public AnchorPane apnUpcomingMatches;
    public AnchorPane apnDates;
    public AnchorPane apnGamesOfTeam;
    public AnchorPane apnLineups;
    public AnchorPane apnBackground;
    public ListView listUpcomingMatches;
    public ListView listTeamGames;
    public ListView listDateGames;
    public ListView listLineupA;
    public ListView listLineupB;
    public ListView listTeamA;
    public ListView listTeamB;
    public ListView cbxSelectMatch;
    public VBox btnBackgroundPn;
    public ComboBox selTeamSearch;
    public ComboBox selMatch;
    public DatePicker dtpGameSearch;
    public Button btnMatchScores;
    public Button btnUpcomingMatches;
    public Button btnDates;
    public Button btnGamesOfTeam;
    public Button btnLineups;
    public Button btnSearchTeams;
    public Button btnSearchDate;
    public Button btnWelcome;
    public Button btnSearchMatch;

    private Server server;
    private Socket clientConnection;
    private boolean isConnected;
    private BufferedReader input;
    private PrintWriter output;

    /**
     * Constructor that also creates a client socket and read/write objects for the client to use when
     * communicating with the server
     * @throws IOException if any of the objects can't be initialized
     */
    public PublicController() throws IOException {
        this.clientConnection = new Socket("127.0.0.1", 9000);
        this.isConnected = true;
        this.output = new PrintWriter(this.clientConnection.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.clientConnection.getInputStream()));
    }
    /**
     * Brings a Welcomepane to front at initialization
     */
    public void initialize(){
        apnWelcome.toFront();
    }

    /**
     * Brings a different pane in the SwitchPane to the front for display, depending on which button is pressed
     * @param event When a Button is clicked to change the tab/Pane
     */
    public void tabSwitch(ActionEvent event) {
        if (event.getSource() == btnWelcome) {
            apnWelcome.toFront();
        } else if (event.getSource() == btnUpcomingMatches) {
            apnUpcomingMatches.toFront();
        } else if (event.getSource() == btnDates) {
            apnDates.toFront();
        } else if (event.getSource() == btnGamesOfTeam) {
            apnGamesOfTeam.toFront();
        } else if (event.getSource() == btnLineups) {
            apnLineups.toFront();
        }

        listUpcomingMatches.setItems(FXCollections.observableArrayList(seeAllMatches()));
        selTeamSearch.setItems(FXCollections.observableArrayList(getTeams()));
        selMatch.setItems(FXCollections.observableArrayList(seeAllMatches()));
    }
    /**
     * Send request to get current list of teams in tournament
     * @return teams
     */
    public ArrayList<String> getTeams(){
        ArrayList<String> teams = new ArrayList<>();
        try {
            String request = "getTeams,";
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
     * Brings backgroundpane to front
     */
    public void homeBackground(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == btnBackgroundPn) {
            apnBackground.toFront();
        }
    }
    /**
     * List of matches in which selected team is participating
     * @param event select Search
     */
    public void searchTeam(ActionEvent event) {
        ArrayList<String> teamGames = new ArrayList<>();
        String teamName = this.selTeamSearch.getSelectionModel().getSelectedItem().toString();

        for(String x: seeAllMatches()){
            if(x.contains(teamName))
                teamGames.add(x);
        }
        listTeamGames.setItems(FXCollections.observableArrayList(teamGames));
    }
    /**
     * List of matches on chosen date
     * @param event select Search
     */
    public void searchDate(ActionEvent event) {
        ArrayList<String> dateGames = new ArrayList<>();
        String date = dtpGameSearch.getValue().toString();

        for(String x: seeAllMatches()){
            if(x.contains(date))
                dateGames.add(x);
        }
        listDateGames.setItems(FXCollections.observableArrayList(dateGames));
    }
    /**
     * Request updated list of matches from server
     * @return matchSelection
     */
    public ArrayList<String> seeAllMatches(){
        ArrayList<String> matchSelections = new ArrayList<>();
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
                LocalDateTime date = LocalDateTime.parse(teams[0].replace("Date: ", ""));
                LocalDateTime present = LocalDateTime.of(2022,11,27,12,00,00);
                if (present.isAfter(date)){
                    matchSelections.add(String.format("%s vs. %s @ %s - Score: %s to %s", teams[1].replace("TeamA: ", ""),
                            teams[2].replace("TeamB: ", ""), teams[0].replace("T", " ").replace("Date: ", ""),
                            teams[3].replace("TeamA Score:", ""), teams[4]).replace("TeamB Score", ""));
                }
                else {
                    matchSelections.add(String.format("%s vs. %s @ %s", teams[1].replace("TeamA: ", ""),
                            teams[2].replace("TeamB: ", ""), teams[0].replace("T", " ").replace("Date: ", "")));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return matchSelections;
    }

    public ArrayList<String> seeUpcomingMatches() {
        ArrayList<String> upcoming = seeAllMatches();
        for (String match : upcoming) {
            String[] matchParse = match.split("@ ");
            String date = matchParse[1].replace(" ","T");
            if (LocalDateTime.parse(date).isBefore(LocalDateTime.now())) {
                upcoming.remove(match);
            }
        }
        return upcoming;
    }

    /**
     * Present team names and team lineups for chosen match
     * @param event selecting Search
     */
    public void searchMatch(ActionEvent event) {
        String match = this.selMatch.getSelectionModel().getSelectedItem().toString();
        String[] split1 = match.split("@");
        String[] split2 = split1[0].split(" vs. ");
        split2[1] = split2[1].substring(0,split2[1].length()-1);
        ArrayList<ArrayList<String>> lineUps = new ArrayList<>();
        try {
            String request = "getLineUp," + split1[1].stripLeading().replace(" ", "T") +"," + split2[0] + "," + split2[1];
            output.println(request);
            listTeamA.setItems(FXCollections.observableArrayList(split2[0]));
            listTeamB.setItems(FXCollections.observableArrayList(split2[1]));
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
                    listLineupA.setItems(FXCollections.observableArrayList(new ArrayList<>()));
                    listLineupB.setItems(FXCollections.observableArrayList(new ArrayList<>()));
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
                        listLineupA.setItems(FXCollections.observableArrayList(teamAList));
                        listLineupB.setItems(FXCollections.observableArrayList(teamBList));
                    } catch (ArrayIndexOutOfBoundsException e) { //This occurs when TeamB has its entire LineUp deleted
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}