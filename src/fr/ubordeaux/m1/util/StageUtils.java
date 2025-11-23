package fr.ubordeaux.m1.util;

import java.net.URL;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Small helper that attaches common stylesheets and applies a maximized/fullscreen state.
 */
public final class StageUtils {
    private StageUtils() {}

    public static void setSceneAndMaximize(Stage stage, Scene scene) {
        // try to attach common css files if present on the classpath
        addIfFound(scene, "/fr/ubordeaux/m1/view/css/base.css");
        addIfFound(scene, "/fr/ubordeaux/m1/view/css/sidebar.css");
        addIfFound(scene, "/fr/ubordeaux/m1/view/css/accueil.css");
        addIfFound(scene, "/fr/ubordeaux/m1/view/css/formation.css");
        addIfFound(scene, "/fr/ubordeaux/m1/view/css/components/btn.css");
        addIfFound(scene, "/fr/ubordeaux/m1/view/css/components/sheet.css");

        stage.setScene(scene);

        // Use a fixed window size instead of fullscreen/maximized so the app appears
        // at a consistent 1920x1080 on startup.
        stage.setWidth(1920);
        stage.setHeight(1080);
        stage.centerOnScreen();
    }

    private static void addIfFound(Scene scene, String resourcePath) {
        URL url = StageUtils.class.getResource(resourcePath);
        if (url != null) scene.getStylesheets().add(url.toExternalForm());
    }
}
