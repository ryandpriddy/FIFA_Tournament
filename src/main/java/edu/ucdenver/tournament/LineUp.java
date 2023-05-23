package edu.ucdenver.tournament;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LineUp implements Serializable {
    private final Team team;
    private final List<Player> listOfPlayers;

    /**
     * 1 param constructor to create a LineUp for a Team to use in a Match
     * @param team the Team needing a LineUp for it
     */
    public LineUp(Team team) {
        this.team = team;
        this.listOfPlayers = new ArrayList<>(11);
    }

    /**
     * Returns a Player if they are in the LineUp
     * @param playerName to search for as a String
     * @return the Player object referred to by the input, if present. Null if not
     */
    public Player getPlayer(String playerName){
        Player p = null;
        for (Player player: listOfPlayers) {
            if (player.getName().equalsIgnoreCase(playerName)){
                p = player;
            }
        }
        return p;
    }

    /**
     * Extract the Team this LineUp belongs to
     * @return the Team object
     */
    public Team getTeam() { return team; }

    /**
     * Extract a List of Player in this LineUp
     * @return a List of the Players
     */
    public List<Player> getPlayers() { return listOfPlayers; }

    /**
     * Add a player to the listOfPlayers
     * @param player the Player to be added
     */
    public synchronized void addPlayer(Player player) {
        listOfPlayers.add(player);
    }

    /**
     * Adds a new Player to the LineUp based on a String input of their name, throws exception if the Player if not
     *  a member of the LineUp's Team
     * @param playerName a String of the Team's Player to add to this LineUp
     * @throws IllegalArgumentException if the playerName does not refer to a Player on this LineUp's Team
     */
    public synchronized void addPlayer(String playerName) throws IllegalArgumentException {
        try {
            listOfPlayers.add(getTeam().getSquadMember(playerName));
        } catch (IllegalArgumentException e){
            System.out.println(playerName + " not found");
        }
    }

    /**
     * Tries to remove a player from the LineUp, throws an exception if the Player isn't part of the LineUp
     * @param playerName to search for in the LineUp as a String
     * @throws IllegalArgumentException if the Player specified isn't part of this LineUp
     */
    public synchronized void removePlayer(String playerName) throws IllegalArgumentException{
        try {
            for (int i = 0; i < listOfPlayers.size(); i++){
                if (listOfPlayers.get(i).getName().equalsIgnoreCase(playerName)){
                    listOfPlayers.remove(i);
                }
            }
        } catch (IllegalArgumentException e){
            System.out.println(playerName + " not found");
        }
    }

    /**
     * Tries to remove a Player from the List of Player in this LineUp
     * @param player the Player object to remove from the list
     */
    public synchronized void removePlayer(Player player){
        listOfPlayers.remove(player);
    }

    /**
     * Formats the LineUp for output as a String
     * @return the LineUp as a String
     */
    @Override
    public String toString() {
        String lineUp = "";
        for (Player p: listOfPlayers){
            lineUp += p.toString();
        }
        return lineUp;
    }
}