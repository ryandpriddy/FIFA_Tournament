package edu.ucdenver.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseApplication extends Application {
    /**
     * Start Choose application
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Choose.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("material-fx-v0_3.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Tournament App Selector");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Main for ChooseApplication
     */
    public static void main(String[] args) {
        launch();
    }
}