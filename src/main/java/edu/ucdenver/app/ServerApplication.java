package edu.ucdenver.app;

import edu.ucdenver.server.Server;
import edu.ucdenver.tournament.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.exit;

public class ServerApplication implements Serializable {
    private static Server server;
    private static Tournament tournament;
    private static boolean timeToSave;

    /**
     * Constructor for ServerApplication
     */
    public ServerApplication(){
        //changed this around a little so that we can initialize different tournament by changing the method call to TourTester
        tournament = TourTester.initTourLite();

        System.out.print(tournament);
        System.out.println("===============================================================");
    }

    /**
     * Getter for tournament
     * @return tournament object
     */
    public Tournament getTournament() {
        return tournament;
    }
    /**
     * Setter for tournament
     * @param newTournament
     */
    public void setTournament(Tournament newTournament) {
        tournament = newTournament;
    }
    /**
     * Getter for server
     * @return server
     */
    public Server getServer() { return server;}
    /**
     * Setter for server
     * @param newServer
     */
    public void setServer(Server newServer){
        server = newServer;
    }

    //Load tournament from file

    /**
     * load Tournament from previous file
     */
    public static void loadTournament() throws IOException {
        System.out.println("Please enter the Tournament name:");
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();

        Object result = null;
        try {
            tournament = Tournament.loadFromFile(fileName);
            System.out.println("Tournament successfully loaded from file...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Save tournament from file

    /**
     * save tournament option turned on for when server terminated
     */
    public static void saveTournament() throws IOException {
        timeToSave = true;
    }
    //Start server

    /**
     * Start server on port 9000
     */
    public static void startServer(){
        int port;
        System.out.println("Starting server on port: 9000");
        server = new Server(9000, 20, tournament);
        server.run();
        System.out.println("Server successfully started...");
    }
    //Stop server

    /**
     * Terminate server and verify if tournament needs to be saved
     */
    public static void terminateServer() throws IOException {
        if(timeToSave){
            tournament.saveToFile();
            System.out.println("Tournament successfully saved!");
        }
        System.out.println("Terminating server...");
        server.shutdown();
        System.out.println("Server successfully terminated..");
    }
    //Menu prompt

    /**
     * Prompt user for input on server
     */
    public static void menuPrompt(){
        System.out.println("Please enter a number for selection.");
        System.out.println("====================================");
        System.out.println("       Main Menu                    ");
        System.out.println("====================================");
        System.out.println("[1] Load Tournament from File");
        System.out.println("[2] Save Tournament to File");
        System.out.println("[3] Start Server");
        System.out.println("[4] Terminate Server");
        System.out.println("[5] Exit Application");

        boolean isSelecting = true;
        int selection = 0;
        while (isSelecting) {
            try {
                Scanner input = new Scanner(System.in);
                selection = input.nextInt();
                isSelecting = false;
            } catch (Exception e){
                System.out.println("Invalid selection -- please try again.");
            }
        }
        try {
            processSelection(selection);
        } catch (Exception e){
            System.out.println(e);
        }

    }
    //Process Main Menu selection

    /**
     * Process selection made by user
     */
    public static void processSelection(int selection) throws IOException {
        if (selection == 1){
            loadTournament();
            menuPrompt();
        } //load tournament
        else if (selection == 2){
            saveTournament();
            menuPrompt();
        } //save tournament
        else if (selection == 3){ //start server
            startServer();
            menuPrompt();
        } //load tournament
        else if (selection == 4){ //terminate server
            terminateServer();
            menuPrompt();
        } //terminate server
        else if (selection == 5){ //exit application
            terminateServer();
            System.out.println("Closing this application...");
            System.out.println("Goodbye..");
            exit(0);
        } //exit application
        else {
            System.out.println("Error occurred -- please try again");
            menuPrompt();
        }
    }

    /**
     * Main for server with initial prompting of user for input
     */
    public static void main(String[] args){
        ServerApplication serverApplication = new ServerApplication();
        System.out.println("Welcome to the ServerApplication!");
        menuPrompt();
    }
}