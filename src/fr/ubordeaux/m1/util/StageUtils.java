package fr.ubordeaux.m1.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Utility methods to manage Stage sizing and scene swapping in a consistent way.
 */
public final class StageUtils {

    private StageUtils() {}

    /**
     * Set the given scene on the stage and resize the stage to occupy the maximum available area.
     * Uses full bounds on macOS to allow covering the Dock if desired, otherwise visual bounds.
     */
    public static void setSceneAndMaximize(Stage stage, Scene scene) {
        // attach global stylesheet if available so all scenes use the same base styles
        try {
            // attach base stylesheet located under view/css/base.css
            String[] sheets = new String[] {
                "/fr/ubordeaux/m1/view/css/base.css",
                "/fr/ubordeaux/m1/view/css/sidebar.css",
                "/fr/ubordeaux/m1/view/css/components/btn.css",
                "/fr/ubordeaux/m1/view/css/components/card.css",
                "/fr/ubordeaux/m1/view/css/components/input.css"
            };
            for (String path : sheets) {
                java.net.URL css = StageUtils.class.getResource(path);
                if (css != null) {
                    String sheet = css.toExternalForm();
                    if (!scene.getStylesheets().contains(sheet)) scene.getStylesheets().add(sheet);
                }
            }
        } catch (Exception ignored) {}

        stage.setScene(scene);
        applyMaxSize(stage);
    }

    /**
     * Resize the stage to the available screen area (not exclusive fullscreen) and try to maximize.
     */
    public static void applyMaxSize(Stage stage) {
        Rectangle2D bounds;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac") || os.contains("darwin")) {
            bounds = Screen.getPrimary().getBounds();
        } else {
            bounds = Screen.getPrimary().getVisualBounds();
        }
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        try { stage.setMaximized(true); } catch (Exception ignored) {}
    }
}
