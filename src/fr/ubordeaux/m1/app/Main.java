package fr.ubordeaux.m1.app;

import fr.ubordeaux.m1.controller.NavigationController;
import fr.ubordeaux.m1.util.StageUtils;
import fr.ubordeaux.m1.view.AppLayout;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        AppLayout layout = new AppLayout();

        // Initialise le controller de navigation qui affichera la page par défaut
        new NavigationController(layout);

        Scene scene = new Scene((Parent) layout.getRoot(), 1920, 1080);
        primaryStage.setTitle("Application de gestion d’un centre de formation");
        StageUtils.setSceneAndMaximize(primaryStage, scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
