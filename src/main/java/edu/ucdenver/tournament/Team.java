package edu.ucdenver.tournament;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private final String name;
    private final Country country;
    private List<Player> squad;

    /**
     * 2 param Constructor for Team class
     * @param name The Player's name
     * @param country Their Country
     */
    public Team(String name, Country country) {
        this.name = name;
        this.country = country;
        this.squad = new ArrayList<>(35);


    }

    /**
     * Uses params to create a new player object and add it to a Team's squad list
     * @param name First & Last
     * @param age   (years)
     * @param height (cms)
     * @param weight (kgs)
     */
    public synchronized void addPlayer(String name, int age, double height, double weight) {
        squad.add(new Player(name, age, height, weight));
    }

    /**
     * Adds the given Player to the Team's squad list
     * @param p the Player to add
     */
    public synchronized void addPlayerToSquad(Player p){
        squad.add(p);
    }
    //Getters & Setters

    /**
     * Getter for the Team Name
     * @return the name of the Team as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the Country
     * @return the Country of the Team
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Getter for the Team's squad
     * @return the squad as a List of Players
     */
    public List<Player> getSquad() {
        return squad;
    }

    /**
     * Takes the name of a Player as a String and returns the Player if they are in the squad, returns null if not
     * @param playerName as a String
     * @return the Player in the squad matching the given String
     */
    public Player getSquadMember(String playerName) {
        Player squadMember = null;
        for (Player p: squad) {
            if (p.getName().equalsIgnoreCase(playerName)){
                squadMember = p;
            }
        }
        return squadMember;
    }

    /**
     * Setter to update/override the squad of a Team all at once
     * @param squad The updated squad to override the current squad
     */
    public synchronized void setSquad(List<Player> squad) {
        this.squad = squad;
    }

    /**
     * Override of toString for formatted output of class content
     * @return Object content as a String
     */
    @Override
    public String toString() {
        String squadMembers = "";
        for (Player player: this.squad) {
            squadMembers += player.toString();
        }
        return String.format("Team Name: %s, %sSquad:%s", this.name, this.country.toString(), squadMembers);
    }
}