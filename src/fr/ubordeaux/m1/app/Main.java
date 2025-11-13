package fr.ubordeaux.m1.app;

import fr.ubordeaux.m1.view.AccueilViewImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Point d'entrée de l'application JavaFX.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    AccueilViewImpl accueilView = new AccueilViewImpl();

    // create counter model + controller and bind to the view
    fr.ubordeaux.m1.model.Counter counter = new fr.ubordeaux.m1.model.Counter(0);
    new fr.ubordeaux.m1.controller.CounterController(counter, accueilView);

    Scene scene = new Scene(accueilView.getRoot(), 600, 400);
        primaryStage.setTitle("Application de gestion d’un centre de formation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
