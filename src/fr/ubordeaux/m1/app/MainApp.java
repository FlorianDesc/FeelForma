package fr.ubordeaux.m1.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application{

    public static void main(String[] args) {
        // Démarre l'application JavaFX
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Simple scene for testing app startup
        Label label = new Label("Centre de Formation - MIAGE — JavaFX démarré !");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 640, 480);

        stage.setTitle("Centre de Formation - MIAGE");
        stage.setScene(scene);
        stage.show();
    }
}
