package edu.ucdenver.tournament;

import java.io.Serializable;

public class Country implements Serializable {
    private final String countryName;

    /**
     * Constructor for Country class
     * @param countryName as a String
     */
    public Country(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Getter for countryName
     * @return countryName as a String
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Compare this Country to a given (that) Country
     * @param c Country
     * @return True if the Country objects are equal, False if not
     */
    public boolean equals(Country c) {
        return this.countryName.equalsIgnoreCase(c.getCountryName());
    }

    /**
     * override toString for specific class output
     * @return String in format: "Country Name: %countryName"
     */
    @Override
    public String toString() {
        return String.format("Country Name: %s%n", this.countryName);
    }
}