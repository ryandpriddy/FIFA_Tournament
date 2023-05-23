package edu.ucdenver.tournament;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int age;
    private double height;
    private double weight;

    /**
     * 4 param constructor for the Player class
     * @param name as a String
     * @param age as an int
     * @param height as a double
     * @param weight as a double
     */
    public Player(String name, int age, double height, double weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    /**
     * Get the Player's name
     * @return String representing the name variable
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the name variable
     * @param name the new name as a String
     */
    public synchronized void setName(String name) {
        this.name = name;
    }

    /**
     * Get the Player's age
     * @return int representing the age variable
     */
    public int getAge() {
        return age;
    }

    /**
     * Changes the age variable
     * @param age the new age as an int
     */
    public synchronized void setAge(int age) {
        this.age = age;
    }

    /**
     * Get the Player's height
     * @return double representing the height variable
     */
    public double getHeight() {
        return height;
    }

    /**
     * Changes the height variable
     * @param height the new height as a double
     */
    public synchronized void setHeight(double height) {
        this.height = height;
    }

    /**
     * Get the Player's weight
     * @return double representing the weight variable
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Changes the weight variable
     * @param weight the new weight as a double
     */
    public synchronized void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Defines a function to determine equality between two player by comparing their member variables
     * @param player The player to compare to this player
     * @return true if they are equal, false if not
     */
    public boolean equals(Player player) {
        return (this.age == player.getAge() && this.name.equalsIgnoreCase(player.getName()) &&
                this.height == player.getHeight() && this.weight == player.getWeight());
    }

    /**
     * Formats the content of the class for output as a string
     * @return A formatted String representing a Player
     */
    @Override
    public String toString() {
        return String.format("Name: %s, Age:%s, Height:%s, Weight:%s%n", this.name,
                Integer.toString(this.age), Double.toString(this.height), Double.toString(this.weight));
    }
}