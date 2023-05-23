package edu.ucdenver.app;

import edu.ucdenver.server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class ChooseController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public Button btnPublic;
    public Button btnAdmin;

    /**
     * Constructor that also creates a client socket and read/write objects for the client to use when
     *   communicating with the server
     * @throws IOException if any of the objects can't be initialized
     */
    //Sets dropdown menu with all Matches in Tournament object
    public void initialize(){
    }

    /**
     * Changes the active scene to be the Admin App
     * @param event when the user Clicks the Admin Button
     * @throws IOException if FXML file can't be loaded or the stage can't be set properly
     */
    public void switchChoosetoAdmin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("tournament_admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tournament Administrator");
        stage.show();
    }

    /**
     * Changes the active scene to be the General Public App
     * @param event when the user clicks the Public Button
     * @throws IOException if FXML file can't be loaded or the stage can't be set properly
     */
    public void switchChoosetoGenPub(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GenPub.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Information App");
        stage.show();
    }


}