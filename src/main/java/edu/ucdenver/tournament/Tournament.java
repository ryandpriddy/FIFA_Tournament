package edu.ucdenver.tournament;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Tournament implements Serializable {
    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final List<Match> listMatches;
    private final List<Team> listTeam;
    private final List<Country> participatingCountries;
    private final List<Referee> listReferees;

    /**
     * 3 param constructor for the Tournament Class
     * @param name of Tournament
     * @param startDate as a LocalDate
     * @param endDate as a LocalDate
     */
    public Tournament(String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listMatches = new ArrayList<>();
        this.listReferees = new ArrayList<>();
        this.participatingCountries = new ArrayList<>();
        this.listTeam = new ArrayList<>();
    }

    /**
     * Default Constructor for the Tournament class
     * sets up empty lists , the name to "", the start date to 11/28/2022 00:00:00, & end dat to 12/21/2022 23:59:59
     */
    public Tournament() {
        this.name = "";
        this.startDate = LocalDateTime.of(2022,11,28,00,00,00);
        this.endDate = LocalDateTime.of(2022,12,21,23,59,59);
        this.listMatches = new ArrayList<>();
        this.listReferees = new ArrayList<>();
        this.participatingCountries = new ArrayList<>();
        this.listTeam = new ArrayList<>();
    }

    /**
     * Getter for the name of the Tournament
     * @return name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the Start Date of the Tournament
     * @return startDate as a LocalDateTime
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Getter for the End Date of the Tournament
     * @return endDate as a LocalDateTime
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Getter for a List of Matches in the Tournament
     * @return List<Match>
     */
    public List<Match> getListMatches(){
        return this.listMatches;
    }

    /**
     * Getter for a List of Referees in the Tournament
     * @return List<Referees>
     */
    public List<Referee> getListReferees(){
        return this.listReferees;
    }

    /**
     * Getter for a List of Countries in the Tournament
     * @return List<Country>
     */
    public List<Country> getParticipatingCountries(){
        return this.participatingCountries;
    }

    /**
     * Getter for an individual Country from List of Countries in the Tournament
     * @return Country
     */
    public Country getCountryFromList(String countryName) {
        Country foundCountry = null;
        for (Country c: participatingCountries){
            if (c.getCountryName().equalsIgnoreCase(countryName)){
                foundCountry = c;
                break;
            }
        }
        return foundCountry;
    }

    /**
     * Getter for a List of Teams in the Tournament
     * @return List<Team>
     */
    public List<Team> getListTeam(){
        return this.listTeam;
    }

    /**
     * Lookup a Team from the Tournament's List of Teams based on its name given as a String
     * @param teamName as a String
     * @return the Team if it is present in the Tournament, null if not
     */
    public Team getTeamFromList(String teamName) {
        Team foundTeam = null;
        for (Team t: this.listTeam) {
            if (t.getName().equalsIgnoreCase(teamName)){
                foundTeam = t;
                break;
            }
        }
        return foundTeam;
    }

    // 1
    /**
     * Loads a Tournament object from a .ser file
     * @param fileName Path to file to be loaded
     * @return The named Tournament object stored in the project resources folder (throws error if the file doesn't exist)
     */
    public static Tournament loadFromFile(String fileName) {
        ObjectInputStream ois = null;
        Tournament tournament = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(buildTournamentFilePath(fileName)));
            tournament = (Tournament) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            tournament = new Tournament();
        }
        finally {
            if (ois != null) {
                try { ois.close(); }
                catch (IOException ioe){ioe.printStackTrace();}
            }
        }
        return tournament;
    }

    // 2
    /**
     * TODO Implement saveToFile
     * Saves Tournament object to a .ser file.
     */
    public void saveToFile() {
        String fileName = getFileName();
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(this);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            if (oos != null) {
                try {
                    oos.close();
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    /**
     * Generates a .ser fileName to serialize to disk based on the Tournament's name
     * @return A file path: /[tournament name].ser
     */
    private String getFileName() {
        return String.format("./src/main/resources/edu/ucdenver/app/%s.ser",this.getName());
    }

    /**
     * Takes the name of a Tournament and builds the filePath to the project resource save file for that Tournament
     * @param fileName the Name of the Tournament to be initialized
     * @return the filePath to the resources this project utilizes as a String
     */
    private static String buildTournamentFilePath(String fileName) {
        return String.format("./src/main/resources/edu/ucdenver/app/%s.ser",fileName);
    }

    // TODO 3 Create a new Tournament - not sure if we need?

    // 4

    /**
     * Add country to the list if not a duplicate
     *  - Country check is a helper function that returns a Country if it is in the Tournament
     * @param countryName as a String
     * @throws IllegalArgumentException if Country is a duplicate
     */
    public synchronized void addCountry(String countryName) throws IllegalArgumentException {
        Country c;
        boolean found = false;
        try {
            c = countryCheck(countryName);
            found = true;
        } catch (IllegalArgumentException iae) {
            participatingCountries.add(new Country(countryName));
        }
        if (found) {
            throw new IllegalArgumentException("Country is already in the Tournament");
        }
    }

    // 6

    /**
     * Validates inputs then adds Team to the Tournament
     * - Country check is a helper function that returns a Country if it is in the Tournament
     * @param name of team as a String
     * @param country String representing the name of the Country that this team hails from
     * @throws IllegalArgumentException if team is already in the Tournament or if the Country is not
     */
    public synchronized void addTeam(String name, String country) throws IllegalArgumentException {
        boolean teamDup = false;
        //check to see if given countryName is valid/registered
        // holds onto it if so to create the team object
        Country c = null;
        try {
            c = countryCheck(country);
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(iae);
        }
        //check to see if the team is in the tournament
        for (Team teamIte : listTeam) {
            if (teamIte.getName().equalsIgnoreCase(name)) {
                teamDup = true;
                break;
            }
        }
        if (!teamDup) {
            listTeam.add(new Team(name,c));
        } else {
            throw new IllegalArgumentException("Team is already in the Tournament");
        }
    }

    // 7

    /**
     * Adds a referee to the Tournament if a duplicate doesn't exist
     * - Country check is a helper function that returns a Country if it is in the Tournament
     * @param name First and Last Name of Ref to add
     * @param country that the ref is from
     * @throws IllegalArgumentException is the ref is already in the tournament
     */
    public synchronized void addReferee(String name, String country) throws IllegalArgumentException {
        boolean refDup = false;
        //check to see if given countryName is valid/registered
        // holds onto it if so to create the team object
        Country c = null;
        try {
            c = countryCheck(country);
        } catch (IllegalArgumentException iae) {
            c = new Country(country);
        }
        //check to see if the referee is in the tournament
        for (Referee refIte : listReferees) {
            if (refIte.getName().equalsIgnoreCase(name)) {
                refDup = true;
                break;
            }
        }
        if (!refDup) {
            listReferees.add(new Referee(name,c));
        } else {
            throw new IllegalArgumentException("Referee is already in the Tournament");
        }
    }

    // 8

    /**
     * Validates inputs to given specifications and adds a match to the list of matches
     * @param dateTime the startTime of the Match
     * @param teamAName as a String
     * @param teamBName as a String
     * @throws IllegalArgumentException if the team names are the same or invalid or if there is already a match at the
     *                          given time
     */
    public synchronized void addMatch(LocalDateTime dateTime, String teamAName, String teamBName) throws IllegalArgumentException {
        Team teamA = null;
        Team teamB = null;
        boolean teamAFound = false;
        boolean teamBFound = false;
        if (teamAName.equalsIgnoreCase(teamBName)) {
            throw new IllegalArgumentException("Both team names are the same.");
        }
        for (Team t : listTeam) {
            if (teamAName.equalsIgnoreCase(t.getName())) {
                teamAFound = true;
                teamA = t;
                break;
            }
        }
        for (Team t : listTeam) {
            if (teamBName.equalsIgnoreCase(t.getName())) {
                teamBFound = true;
                teamB = t;
                break;
            }
        }
        for (Match m : listMatches) {
            if (m.getDateTime().isEqual(dateTime)) {
                throw new IllegalArgumentException("Match already exists at that time.");
            }
        }

        if (teamAFound && teamBFound) {
            listMatches.add(new Match(dateTime, teamA, teamB));
        }
        else if (!teamAFound) {
            throw new IllegalArgumentException(String.format("%s not registered in %s",teamAName,this.getName()));
        }
        else if (!teamBFound) {
            throw new IllegalArgumentException(String.format("%s not registered in %s",teamBName,this.getName()));
        }

    }

    // 9

    /**
     * Takes in a Date/Time and the Name of the referee and tries to match the inputs up with their matching objects in
     *   the <code>Tournament</code> (the <code>Match</code> and the <code>Referee</code>, respectively). Also, checks
     *   to make sure the Referee isn't from a <code>Country</code> that has a <code>Team</code> Playing in that Match
     * @param date the Start Date/Time of the match as a LocalDateTIme
     * @param refereeName as a String
     * @throws IllegalArgumentException when a Match at the given LocalDateTime doesn't exist, the Referee doesn't exist,
     *   or if the Referee is from a Country with a Team Playing in the Match
     */
    public synchronized void addRefereeToMatch(LocalDateTime date, String refereeName) throws IllegalArgumentException {
        boolean foundRef = false;
        boolean countryConflict = false;
        boolean matchFound = false;
        Referee ref = new Referee("",new Country(""));
        Match match = null;
        //check to see if the referee is in the tournament
        for (Referee refIte : listReferees) {
            if (refIte.getName().equalsIgnoreCase(refereeName)) {
                ref = refIte;
                foundRef = true;
                break;
            }
        }
        for (Match m : listMatches) {
            if (m.getDateTime().isEqual(date)) {
                match = m;
                matchFound = true;
                if (m.getTeamA().getTeam().getCountry().getCountryName().equals(ref.getCountry().getCountryName()) ||
                     m.getTeamB().getTeam().getCountry().getCountryName().equals(ref.getCountry().getCountryName())) {
                    countryConflict = true;
                    break;
                }
            }
        }
        if (foundRef && matchFound && !countryConflict) {
            match.addReferee(ref);
        }
        else if (!foundRef) {
            throw new IllegalArgumentException("That referee is not registered in the Tournament");
        }
        else if (!matchFound) {
            throw new IllegalArgumentException("That match doesn't exist in the Tournament");
        }
        else {
            throw new IllegalArgumentException("That referee has a conflict of interest based on the Teams playing in the Match");
        }

    }

    // 10

    // TODO addPlayer JavaDoc
    /**
     * Adds a Player object to a Team
     * @param teamName as a String
     * @param playerName as a String
     * @param age as an Integer
     * @param height as a Double
     * @param weight as a Double
     * @throws IllegalArgumentException if the given teamName cannot be found
     */
    public synchronized void addPlayer(String teamName, String playerName, int age, double height, double weight) throws IllegalArgumentException{
        for (Team team: listTeam){
            if (team.getName().equalsIgnoreCase(teamName)){
                team.addPlayer(playerName, age, height, weight);
            }
        }
    }

    /**
     * Adds a Player object to a Match
     * @param dateTime as a LocalDate
     * @param teamName as a String
     * @param playerName as a String
     * @throws IllegalArgumentException if the given Player is not on a Team
     */
    public synchronized void addPlayerToMatch(LocalDateTime dateTime, String teamName, String playerName) throws IllegalArgumentException {
        for (Match m: listMatches){
            if (m.getDateTime().isEqual(dateTime)){
                if (m.getTeamA().getTeam().getName().equalsIgnoreCase(teamName)){
                    m.getTeamA().addPlayer((m.getTeamA().getTeam().getSquadMember(playerName)));
                }
                else if (m.getTeamB().getTeam().getName().equalsIgnoreCase(teamName)){
                    m.getTeamB().addPlayer((m.getTeamB().getTeam().getSquadMember(playerName)));
                }
            }
        }
    }

    /**
     * Takes a Country and removes it from the Tournament
     * @param c the Country to be deleted
     * @throws IllegalArgumentException if the given Country is not in the Tournament
     */
    public synchronized void deleteCountry(Country c) throws IllegalArgumentException {
        boolean found = this.participatingCountries.remove(c);
        //System.out.println(found);
        if (!found) {
            throw new IllegalArgumentException("Country not found in Tournament");
        }
    }

    /**
     * Represents object's instance variables as formatted String
     */
    @Override
    public String toString() {
        String pattern = "yyyy-dd-MM HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String startDate = formatter.format(this.startDate);
        String endDate = formatter.format(this.endDate);
        String matches = "";
        String countries = "";
        String teams = "";
        String refs = "";
        for (Country c: participatingCountries) {
            countries += c.toString();
        }
        for (Team t: listTeam){
            teams += t.toString();
        }
        for (Match m: listMatches) {
            matches += m.toString();
        }
        for (Referee r: listReferees){
            refs += r.toString();
        }
        return String.format("Name: %s%nStartDate: %s%nEndDate: %s%nCountries:%n%s%nTeams:%n%sReferees:%n%s",
                this.name, startDate, endDate, countries, teams, refs);
    }

    /**
     *  Helper function that check to see if a Country is already in the list, returning it if so
     * @param countryName search the country objects for a matching name
     * @return the matching country from the Tournament list
     * @throws IllegalArgumentException if Country is not in the Tournament list
     */
    private Country countryCheck(String countryName) throws IllegalArgumentException {
        for (Country c : participatingCountries) {
            if (c.getCountryName().equalsIgnoreCase(countryName)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Country is not in the Tournament");
    }
}