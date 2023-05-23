package edu.ucdenver.server;

import edu.ucdenver.app.ServerApplication;
import edu.ucdenver.tournament.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

public class ClientWorker implements Runnable {
    private final Socket clientSocket;
    private final Tournament tournament;
    private volatile boolean keepRunningClient;
    private BufferedReader input;
    private PrintWriter output;
    /**
     * Constructor for ClientWorker class
     * @param clientSocket that client uses
     * @param tournament Tournament object
     */
    public ClientWorker(Socket clientSocket, Tournament tournament){
        this.clientSocket = clientSocket;
        System.out.println("ClientWorker connected to Server...");
        this.tournament = tournament;
        this.keepRunningClient = true;
    }
    /**
     * Getter for clientSocket
     * @return clientSocket
     */
    public Socket getClientSocket(){
        return this.clientSocket;
    }
    /**
     * Generate output of I/O Stream
     * @param clientSocket that client uses
     * @return output for sending
     */
    private PrintWriter getOutputStream(Socket clientSocket){
        PrintWriter output = null;
        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return output;
    }
    /**
     * Generate input of I/O Stream
     * @param clientSocket that client uses
     * @return input for receiving
     */
    private BufferedReader getInputStream(Socket clientSocket){
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return input;
    }
    /**
     * Read in string and process request from client
     * @return response notifying client if request was received correctly
     */
    private String processRequest() {
        String request = null;
        String response = "";
        String[] params = null;
        try {
            request = this.input.readLine();
            System.out.println("Raw Request|" + request);
            params = request.split(",");
        } catch (Exception e) {
            //System.out.println("Could not receive message from server");
            if(request == null){
                ServerApplication.menuPrompt();
            }
        }
        //Adders
        if (params[0].equalsIgnoreCase("addTeam")) {
            try {
                if (tournament.getTeamFromList(params[1]) == null) {
                    tournament.addTeam(params[1], params[2]); //String teamName, String countryName
                    response = "Success";
                }
                else if (tournament.getTeamFromList(params[1]).getName().equalsIgnoreCase(params[1])) {
                    response = "Found";
                }
                else {
                    response = "Fail";
                }
            } catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("addCountry")) {
            try {
                tournament.addCountry(params[1]); //String countryName
                response = "Success";
            } catch (Exception e){
                response = "Found";
            }
        }
        else if (params[0].equalsIgnoreCase("addReferee")) {
            try {
                tournament.addReferee(params[1], params[2]); //String refereeName, String countryName
                response = "Success";
            } catch (Exception e) {
                response = "Found";
            }
        }
        else if (params[0].equalsIgnoreCase("addNewMatch")) {
            try {
                tournament.addMatch(LocalDateTime.parse(params[1]), params[2], params[3]); //DateTime, String TeamAName, String TeamBName
                response = "Success";
            } catch (Exception e) {
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("addNewMatchReferee")){
            try {
                for (Referee r: tournament.getListReferees()){
                    if (r.getName().equalsIgnoreCase(params[1])){
                        response = r.toString();
                    }
                }
            } catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("addRefereeToNewMatch")){
            try {
                for (Match m: tournament.getListMatches()){
                    if (m.getDateTime().isEqual(LocalDateTime.parse(params[1]))){
                        for (Referee r: tournament.getListReferees()){
                            if (r.getName().equalsIgnoreCase(params[2])){
                                m.addReferee(r);
                                response = "Success";
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("addPlayer")) {
            try {
                if (tournament.getTeamFromList(params[1]).getSquadMember(params[2]) == null
                        && tournament.getTeamFromList(params[1]).getSquad().size() < 35){
                    tournament.getTeamFromList(params[1]).addPlayer(params[2], Integer.parseInt(params[3]),
                            Double.parseDouble(params[4]), Double.parseDouble(params[5]));
                    response = "Success";
                }
                else if (tournament.getTeamFromList(params[1]).getSquad().size() >= 35){
                    response = "Full";
                }
                else {
                    response = "Found";
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (params[0].equalsIgnoreCase("addPlayerToLUA")){
            try {
                for (Match m: tournament.getListMatches()){
                    if (m.getDateTime().isEqual(LocalDateTime.parse(params[2]))){
                        try { //Check if in Team A already
                            if (m.getTeamA().getPlayer(params[1]).equals(m.getTeamA().getTeam().getSquadMember(params[1]))) {
                                response = "Found";
                            }
                        } catch (Exception e) {
                            m.getTeamA().addPlayer(params[1]);  //String playerName
                            response = "Success";
                        }
                    }
                }
            } catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("addPlayerToLUB")){
            try {
                for (Match m: tournament.getListMatches()){
                    if (m.getDateTime().isEqual(LocalDateTime.parse(params[2]))){
                        try { //Check if in TeamB already
                            if (m.getTeamB().getPlayer(params[1]).equals(m.getTeamB().getTeam().getSquadMember(params[1]))) {
                                response = "Found";
                            }
                        } catch (Exception e) {
                            m.getTeamB().addPlayer(params[1]); //String playerName
                            response = "Success";
                        }
                    }
                }
            } catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("addRefereeToMatch")){
            try {
                for (Match m: tournament.getListMatches()){
                    //toString has '\n', '\r' for pretty print, but breaks Socket I/O, so must remove
                    //'@' is char delim for each LineUp returned (for calling Controller fxn)
                    LocalDateTime date = LocalDateTime.parse(params[1]);
                    String refObject = params[2];
                    String[] refParse = refObject.split("-");
                    String refName = refParse[0].strip();
                    if (m.getDateTime().isEqual(date)) {
                        boolean found = false;
                        for (Referee r : m.getMatchReferees()) {
                            if (r.getName().equalsIgnoreCase(refName)) {
                                response = "Referee already registered for this Match";
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            tournament.addRefereeToMatch(date, refName);
                            response = "Success";
                        }

                    }
                }
            } catch (Exception e){
                response = e.toString();
            }
        }
        //Remove
        else if (params[0].equalsIgnoreCase("deleteCountry")){
            try {
                for (Country c: tournament.getParticipatingCountries()){
                    if (c.getCountryName().equalsIgnoreCase(params[1])){
                        tournament.getParticipatingCountries().remove(tournament.getCountryFromList(params[1]));
                        tournament.getListTeam().remove(tournament.getTeamFromList(String.format("%s National Team", params[1])));
                        response = "Success";
                        break;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (params[0].equalsIgnoreCase("deleteTeam")){
            try {
                for (Team t: tournament.getListTeam()){
                    if (t.getName().equalsIgnoreCase(params[1])){
                        tournament.getListTeam().remove(tournament.getTeamFromList(params[1]));
                        response = "Success";
                        break;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (params[0].equalsIgnoreCase("deletePlayer")) {
            for (Team t : tournament.getListTeam()) {
                try {
                    if (t.getSquadMember(params[1]).getName().equalsIgnoreCase(params[1]) && t.getName().equalsIgnoreCase(params[2])) {
                        t.getSquad().remove(t.getSquadMember(params[1]));
                        response = "Success";
                        break;
                    }
                } catch (Exception e) {
                }
            }
        }
        else if (params[0].equalsIgnoreCase("removePlayerFromLUA")){
            try {
                for (Match m: tournament.getListMatches()){ //Find the correct Match
                    if (m.getDateTime().isEqual(LocalDateTime.parse(params[2]))){
                        m.getTeamA().removePlayer(params[1]); //Remove from TeamA LineUp
                        response = "Success";
                    }
                }
            } catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("removePlayerFromLUB")){
            try {
                for (Match m: tournament.getListMatches()){ //Find the correct Match
                    if (m.getDateTime().isEqual(LocalDateTime.parse(params[2]))){
                        m.getTeamB().removePlayer(params[1]); //Remove from TeamB LineUp
                        response = "Success";
                    }
                }
            } catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("removeRefFromMatch")){
            try {
                for (Match m: tournament.getListMatches()){
                    if (m.getDateTime().isEqual(LocalDateTime.parse(params[2]))){
                        for (Referee r : m.getMatchReferees()){
                            if (r.getName().equalsIgnoreCase(params[1])){
                                m.getMatchReferees().remove(r);
                                break;
                            }
                        }
                        response = "Success";
                    }
                }
            } catch (Exception e){
                System.out.println(e);
            }
        }
        //Getters
        else if (params[0].equalsIgnoreCase("getCountries")){
            try {
                List<Country> allCountries = tournament.getParticipatingCountries();
                for (Country c: allCountries){
                    response += c.getCountryName() + ",";
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (params[0].equalsIgnoreCase("getTeams")){
            try {
                List<Team> allTeams = tournament.getListTeam();
                for (Team t: allTeams) {
                    response += t.getName() + ",";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (params[0].equalsIgnoreCase("getReferees")){
            try {
                List<Referee> allRefs = tournament.getListReferees();
                for (Referee r: allRefs){
                    response += r.getName() + ":" + r.getCountry().getCountryName() + ",";
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (params[0].equalsIgnoreCase("getMatches")){
            try {
                for (Match m: tournament.getListMatches()) {
                    response += m.toString() + "##"; //'##' is char delim foreach Match returned (for calling Controller fxn parsing)
                }
            } catch (Exception e){
                response = "Fail";
            }
            return response;
        }
        else if (params[0].equalsIgnoreCase("getMatchScore")){
            try {
                for (Match m : tournament.getListMatches()){
                    //Validate correct Match
                    if (LocalDateTime.parse(params[1]).isEqual(m.getDateTime())){
                        response += m.getScoreTeamA() + "," + m.getScoreTeamB();
                    }
                }
            }
            catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("getRefsForMatch")){
            try {
                for (Match m: tournament.getListMatches()){
                    //toString has '\n', '\r' for pretty print, but breaks Socket I/O, so must remove
                    //'@' is char delim for each LineUp returned (for calling Controller fxn)
                    if (m.getDateTime().isEqual(LocalDateTime.parse(params[1]))) {
                        String[] refBuilder = new String[4];
                        for (int i = 0; i < 4; i++) {
                            refBuilder[i] = "Referee Name-Country Name,";
                        }
                        int i = 0;
                        for (Referee r : m.getMatchReferees()) {
                            refBuilder[i] = String.format("%s-%s,",r.getName(), r.getCountry().getCountryName());
                            i++;
                        }
                        for (i = 0; i < 4; i++) {
                            response += refBuilder[i];
                        }

                    }
                }
                if (response.equalsIgnoreCase("")) {
                    response += "";

                }
            } catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("getLineUp")){
            try {
                for (Match m: tournament.getListMatches()){
                    //toString has '\n', '\r' for pretty print, but breaks Socket I/O, so must remove
                    //'@' is char delim for each LineUp returned (for calling Controller fxn)
                    if (m.getDateTime().isEqual(LocalDateTime.parse(params[1]))) {
                        response += m.getTeamA().toString().replace("\r","").replace("\n", "/") + "@";
                        response += m.getTeamB().toString().replace("\r","").replace("\n", "/");
                    }
                }
            } catch (Exception e){
                response = "Fail";
            }
        }
        else if (params[0].equalsIgnoreCase("getSquad")){
            try {
                for (Team t: tournament.getListTeam()){
                    //toString has '\n', '\r' for pretty print, but breaks Socket I/O, so must remove
                    //',' is char delim for each LineUp returned (for calling Controller fxn using .split(",",""))
                    if (t.getName().equalsIgnoreCase(params[1])){
                        String rawResponse = t.getSquad().toString();
                        rawResponse = rawResponse.replace(String.format("Team Name: %s, ", params[1]), "")
                                .replace("\n", "/")
                                .replace("\r","")
                                .replace("[", "")
                                .replace("]", "")
                                .replace("Name: ", "Name:");
                        response += rawResponse;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        //Setters
        else if (params[0].equalsIgnoreCase("setMatchScore")) {
            try {
                for (Match m : tournament.getListMatches()) { //Validate correct Match
                    if (LocalDateTime.parse(params[5]).isEqual(m.getDateTime())) {
                        m.setScoreTeamA(Integer.parseInt(params[2]));
                        m.setScoreTeamB(Integer.parseInt(params[4]));
                    }
                }
                response = "Success";
            }
            catch (Exception e){
                response = "Fail";
            }
        }

        System.out.println("Response|" + response);
        return response;
    }
    /**
     * Close I/O Stream and clientSocket
     */
    public void closeConnection(){
        try {
            this.output.close();
            this.input.close();
            this.clientSocket.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * Force shutdown of client connection
     */
    public void forceShutdown(){
        try {
            this.clientSocket.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * Loop to receive requests and send responses to client
     */
    @Override
    public void run() {
        try {
            this.input = getInputStream(clientSocket);
            this.output = getOutputStream(clientSocket);
            System.out.println("ClientWorker I/O Streams created..");
            while (keepRunningClient) {
                String response = processRequest();
                System.out.printf("Processed Result|%s%n", response);
                output.println(response);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }
}