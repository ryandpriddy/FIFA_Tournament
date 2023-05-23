package edu.ucdenver.tournament;

import java.io.Serializable;

public class Referee implements Serializable {
    private String name;
    private final Country country;

    /**
     * 2 param constructor for the Referee class
     * @param name as a String
     * @param country as a String
     */
    public Referee(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    /**
     * Getter for the Referee's name
     * @return the name of the Referee as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the Referee
     * @param name as a String
     */
    public synchronized void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the Country member
     * @return the Country member object
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Override toString to format object content into a String for output
     * @return formatted String representing the Referee object
     */
    @Override
    public String toString() {
        return String.format("Name: %s, Country: %s", this.name, this.country.getCountryName());
    }
}