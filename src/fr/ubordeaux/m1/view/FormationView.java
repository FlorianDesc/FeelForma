package fr.ubordeaux.m1.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simple view for creating a formation. Kept deliberately minimal for testing/navigation.
 */
public class FormationView {

    private Scene scene;

    /**
     * Construct a FormationView instance. The view manages its own Scene and behavior.
     * @param stage the primary stage (provided for potential future use)
     * @param onBack callback invoked when the user requests to go back
     */
    public FormationView(Stage stage, Runnable onBack) {
        Label title = new Label("Créer une nouvelle formation");
        title.setStyle("-fx-font-size:18px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Intitulé :");
        TextField nameField = new TextField();
        nameField.setPromptText("Saisir le nom de la formation");

        HBox nameBox = new HBox(8, nameLabel, nameField);
        nameBox.setAlignment(Pos.CENTER_LEFT);

        // simple action buttons
        fr.ubordeaux.m1.view.component.CustomButton save = new fr.ubordeaux.m1.view.component.CustomButton("Enregistrer", fr.ubordeaux.m1.view.component.CustomButton.Variant.PRIMARY);
        fr.ubordeaux.m1.view.component.CustomButton back = new fr.ubordeaux.m1.view.component.CustomButton("Retour", fr.ubordeaux.m1.view.component.CustomButton.Variant.SECONDARY);

        save.setOnAction(e -> {
            // For now, just print to console. Later controller would handle persistence.
            System.out.println("Enregistré formation: " + nameField.getText());
        });

        back.setOnAction(e -> {
            if (onBack != null) onBack.run();
        });

        HBox buttons = new HBox(10, save, back);
        buttons.setAlignment(Pos.CENTER);

        VBox content = new VBox(16, title, nameBox, buttons);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        // Sidebar: provide a Home callback that invokes onBack (use StageUtils from caller to set scene)
        Runnable onHome = () -> { if (onBack != null) onBack.run(); };
        SidebarView sidebar = new SidebarView(stage, onHome, null);

        javafx.scene.layout.BorderPane root = new javafx.scene.layout.BorderPane();
        root.setLeft(sidebar.getNode());
        root.setCenter(content);

        this.scene = new Scene(root);
        // load page-specific stylesheet
        try {
            java.net.URL css = this.getClass().getResource("css/formation.css");
            if (css != null) this.scene.getStylesheets().add(css.toExternalForm());
        } catch (Exception ignored) {}

        // mark formation as active in the sidebar
        sidebar.setActive("formation");
    }

    public Scene getScene() {
        return scene;
    }
}
