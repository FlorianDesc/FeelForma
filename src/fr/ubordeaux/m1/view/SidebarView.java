package fr.ubordeaux.m1.view;

import fr.ubordeaux.m1.view.component.CustomButton;
import fr.ubordeaux.m1.view.component.CustomButton.Size;
import fr.ubordeaux.m1.view.component.CustomButton.Variant;
import fr.ubordeaux.m1.view.component.Icons;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SidebarView {

    private final VBox root;
    private final CustomButton btnFormation;
    private final CustomButton btnSession;
    private final CustomButton btnApprenants;

    public SidebarView() {
        root = new VBox(12);
        root.setPadding(new Insets(12));

        // Largeur fixe ajustée
        root.setPrefWidth(200);
        root.setMinWidth(200);
        root.setMaxWidth(200);

        Label title = new Label("FeelForma");
        title.getStyleClass().add("sidebar-title");

        // === Bouton Formations ===
        btnFormation = new CustomButton("", Variant.TERTIARY, Size.MD);
        btnFormation.setMaxWidth(Double.MAX_VALUE);  // FULL WIDTH
        btnFormation.setGraphic(createButtonContent(Icons.book(20), "Formations"));

        // === Bouton Sessions (supprimé de la sidebar) ===
        btnSession = new CustomButton("", Variant.TERTIARY, Size.MD);
        btnSession.setMaxWidth(Double.MAX_VALUE);   // FULL WIDTH
        btnSession.setGraphic(createButtonContent(Icons.user(20), "Sessions"));

        // === Bouton Apprenants ===
        btnApprenants = new CustomButton("", Variant.TERTIARY, Size.MD);
        btnApprenants.setMaxWidth(Double.MAX_VALUE);  // FULL WIDTH
        btnApprenants.setGraphic(createButtonContent(Icons.user(20), "Apprenants"));

        setActive("formation");

        root.getChildren().addAll(title, btnFormation, btnApprenants);
    }

    public Node getNode() {
        return root;
    }

    public void setOnFormation(Runnable r) {
        btnFormation.setOnAction(e -> r.run());
    }

    public void setOnSession(Runnable r) {
        btnSession.setOnAction(e -> r.run());
    }

    public void setOnApprenants(Runnable r) {
        btnApprenants.setOnAction(e -> r.run());
    }

    public void setActive(String key) {
        btnFormation.getStyleClass().remove("active");
        btnSession.getStyleClass().remove("active");
        btnApprenants.getStyleClass().remove("active");

        switch (key) {
            case "session" -> btnSession.getStyleClass().add("active");
            case "apprenants" -> btnApprenants.getStyleClass().add("active");
            default -> btnFormation.getStyleClass().add("active");
        }
    }

    private Node createButtonContent(Node icon, String text) {
        HBox box = new HBox(10);
        box.setFillHeight(true);

        Label label = new Label(text);
        label.getStyleClass().add("sidebar-btn-text");

        // Permet à la zone textuelle de s'étirer
        HBox.setHgrow(label, Priority.ALWAYS);

        box.getChildren().addAll(icon, label);
        return box;
    }
}
