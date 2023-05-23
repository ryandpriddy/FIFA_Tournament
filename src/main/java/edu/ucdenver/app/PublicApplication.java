package edu.ucdenver.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PublicApplication extends Application {

    /**
     * Start Public application
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PublicApplication.class.getResource("tournament_public.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        String css = this.getClass().getResource("material-fx-v0_3.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Tournament Viewer");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Main for PublicApplication
     */
    public static void main(String[] args) {
        launch();
    }
}