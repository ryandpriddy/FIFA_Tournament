package edu.ucdenver.server;

import edu.ucdenver.tournament.Tournament;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable, Serializable {
    private Tournament tournament;
    private int port;
    private int backlog;
    private ServerSocket socketServer;
    private ArrayList<ClientWorker> clientWorkersList;
    private int connectionCounter;
    private boolean keepServerRunning;

    /**
     * Constructor for Server class
     * @param port int that server uses
     * @param backlog number of connections server can handle
     * @param tournament Tournament object
     */
    public Server(int port, int backlog, Tournament tournament){
        try {
            this.port = port;
            this.backlog = backlog;
            this.socketServer = new ServerSocket(port, backlog);
            this.clientWorkersList = new ArrayList<>();
            this.keepServerRunning = true;
            this.connectionCounter = 0;
            this.tournament = tournament;
            System.out.println("Server created!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Getter for clientWorkersList
     * @return list of clients
     */
    public ArrayList<ClientWorker> getClientWorkersList(){
        return this.clientWorkersList;
    }
    /**
     * Getter for tournament object
     * @return tournament object
     */
    public Tournament getTournament() {
        return tournament;
    }
    /**
     * Getter for port number
     * @return port number used by server
     */
    public int getPort() {
        return port;
    }

    /**
     * Wait for new client to connect
     * @return client after new socket connection
     */
    private Socket waitForConnection(){
        Socket client = null;
        try {
            client = this.socketServer.accept();
            this.connectionCounter++;
            System.out.printf("Client connection #%d accepted from %s%n", connectionCounter, client.getInetAddress().getHostName());
        }
        catch (Exception e) {
            System.err.print(e);
        }
        return client;
    }
    /**
     * Shutdown clients and empty clientWorkersList
     */
    public void shutdown(){
        this.keepServerRunning = false;
        for (int i = 0; i < clientWorkersList.size(); i++) {
            try {
                clientWorkersList.get(i).forceShutdown();
                clientWorkersList.remove(i);
                connectionCounter--;
            }
            catch (Exception e){}
        }
    }

    //Receive AdminController request, pass off to ClientWorker, get updated tournament, send back to AdminController?

    /**
     * Keep server listening, make new clientworker for new connection, execute thread
     */
    @Override
    public void run() {
        //Create executor server
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (keepServerRunning) {
            System.out.println("Server waiting for client connection...");
            //Connect to client, return the socket client connects to
            Socket clientSocket = waitForConnection();
            //Create new clientWorker for new client, add to server's List of clientWorkers
            ClientWorker clientWorker = new ClientWorker(clientSocket, this.tournament);
            clientWorkersList.add(clientWorker);
            //Execute thread
            Thread thread = new Thread(clientWorker);
            executorService.execute(thread);
        }
        executorService.shutdown();
    }
}