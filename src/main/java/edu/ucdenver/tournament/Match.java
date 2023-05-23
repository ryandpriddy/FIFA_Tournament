package edu.ucdenver.tournament;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Match implements Serializable {
    private final LocalDateTime dateTime;
    private LineUp teamA;
    private LineUp teamB;
    private int scoreTeamA;
    private int scoreTeamB;
    private final List<Referee> matchReferees;

    /**
     * 3 param Constructor for the Match class
     * @param dateTime the Start Date/Time of the Match
     * @param teamA the first Team playing in the Match
     * @param teamB the second Team playing in the Match
     */
    public Match(LocalDateTime dateTime, Team teamA, Team teamB) {
        this.dateTime = dateTime;
        this.teamA = new LineUp(teamA);
        this.teamB = new LineUp(teamB);
        this.scoreTeamA = 0;
        this.scoreTeamB = 0;
        this.matchReferees = new ArrayList<>(4);
    }

    /**
     * Adds a referee to a Match if they are not already part of the Match
     * @param ref a Referee to add to the Match
     */
    public void addReferee(Referee ref){
        if (!this.matchReferees.contains(ref)){
            matchReferees.add(ref);
        }
    }

    /**
     * Getter for the start Date/Time of the match
     * @return the start date/time of the match as a DateTime
     */
    public LocalDateTime getDateTime() { return dateTime; }

    /**
     * Getter for the matches Referees
     * @return A List of Referees
     */
    public List<Referee> getMatchReferees() { return matchReferees; }

    /**
     * Getter for Team A's LineUp
     * @return A LineUp
     */
    public LineUp getTeamA() { return teamA; }

    /**
     * Getter for Team B's LineUp
     * @return A LineUp
     */
    public LineUp getTeamB() { return teamB; }

    /**
     * Setter for Team A's score for the Match
     * @param teamAScore int representing Team A's Score
     */
    public synchronized void setScoreTeamA(int teamAScore){ this.scoreTeamA = teamAScore; }

    /**
     * Setter for Team B's score for the Match
     * @param teamBScore int representing Team B's Score
     */
    public synchronized void setScoreTeamB(int teamBScore){ this.scoreTeamB = teamBScore; }

    /**
     * Getter for Team A's Score for the Match
     * @return an int representing Team A's Score
     */
    public int getScoreTeamA(){ return this.scoreTeamA; }

    /**
     * Getter for Team B's Score for the Match
     * @return an int representing Team  B's Score
     */
    public int getScoreTeamB() { return this.scoreTeamB;}

    /**
     * Formats a Match object content for output as a String
     * @return A formatted String containing info for a Match
     */
    @Override
    public String toString() {
        String pattern = "yyyy-dd-MM HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String refs = "";
        for (Referee ref: matchReferees) {
            refs += ref.toString();
        }
        return String.format("Date: %s, TeamA: %s, TeamB: %s, TeamA Score: %d, TeamB Score %d, Match Referees: %s",
                dateTime, this.getTeamA().getTeam().getName(), this.getTeamB().getTeam().getName(), this.scoreTeamA, this.scoreTeamA,
                this.matchReferees.toString());
    }
}