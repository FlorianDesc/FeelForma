package fr.ubordeaux.m1.app;

import fr.ubordeaux.m1.view.AccueilView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application entry. Keeps startup logic minimal and delegates UI to views.
 */
public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        AccueilView accueilView = new AccueilView(stage);
        stage.setTitle("FeelForma");
        // Use StageUtils to set the initial scene and maximize the window consistently
        fr.ubordeaux.m1.util.StageUtils.setSceneAndMaximize(stage, accueilView.getScene());
        stage.show();
    }
}
