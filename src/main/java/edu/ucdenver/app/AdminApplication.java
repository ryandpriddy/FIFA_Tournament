package edu.ucdenver.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminApplication extends Application {

    /**
     * Overrides of a method in the <code>Application</code> class that will initialize the application window
     * @param stage A Stage class instance that enables the application to run, handled by the static method <code>launch</code>
     * @throws IOException if the fxml file cannot be opened
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tournament_admin.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("material-fx-v0_3.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Tournament Administrator");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Main for AdminApplication
     */
    public static void main(String[] args) {
        launch();
    }
}