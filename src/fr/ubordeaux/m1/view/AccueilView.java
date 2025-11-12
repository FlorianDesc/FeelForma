package fr.ubordeaux.m1.view;

import fr.ubordeaux.m1.controller.FormationController;
import fr.ubordeaux.m1.util.StageUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Accueil view (page d'accueil) responsible for the main landing page UI.
 * Keeps MainApp minimal and provides navigation to Formation view.
 */
public class AccueilView {

    private Scene scene;
    private SidebarView sidebar;

    public AccueilView(Stage stage) {
        Label title = new Label("Centre de Formation - MIAGE");
        title.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

    fr.ubordeaux.m1.view.component.CustomButton createFormation = new fr.ubordeaux.m1.view.component.CustomButton("Créer une formation", fr.ubordeaux.m1.view.component.CustomButton.Variant.PRIMARY);

        VBox content = new VBox(16, title, createFormation);
        content.setAlignment(Pos.CENTER);

        // Sidebar navigation callbacks (use StageUtils to centralize maximizing behavior)
        Runnable goHome = () -> {
            // ensure the Accueil sidebar is marked active before showing the scene
            this.setActiveSidebar("home");
            StageUtils.setSceneAndMaximize(stage, this.scene);
        };
        Runnable goFormation = () -> {
            FormationController controller = new FormationController();
            Runnable onBack = () -> {
                // mark Accueil active first so the visual state is ready when the scene is shown
                this.setActiveSidebar("home");
                StageUtils.setSceneAndMaximize(stage, this.scene);
            };
            var formationView = controller.createFormationView(stage, onBack);
            StageUtils.setSceneAndMaximize(stage, formationView.getScene());
        };

    this.sidebar = new SidebarView(stage, goHome, goFormation);

        BorderPane root = new BorderPane();
        root.setLeft(sidebar.getNode());
        root.setCenter(content);

        this.scene = new Scene(root);

        // load page-specific stylesheet
        try {
            java.net.URL css = this.getClass().getResource("css/accueil.css");
            if (css != null) this.scene.getStylesheets().add(css.toExternalForm());
        } catch (Exception ignored) {}

        // mark Accueil as active in the sidebar
        sidebar.setActive("home");

        // Also wire the button inside the content area
    createFormation.setOnAction(e -> goFormation.run());
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Allow external callers to set the active item in the sidebar.
     */
    public void setActiveSidebar(String key) {
        if (this.sidebar != null) this.sidebar.setActive(key);
    }
}
