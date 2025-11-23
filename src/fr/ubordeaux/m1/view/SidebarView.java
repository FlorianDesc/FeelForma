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
    private final CustomButton btnApprenant;
    private final CustomButton btnFormation;

    public SidebarView() {

        root = new VBox(12);
        root.setPadding(new Insets(12));
        root.getStyleClass().add("sidebar");
        root.setFillWidth(true);

        // Title
        Label title = new Label("FeelForma");
        title.getStyleClass().add("sidebar-title");

        // --- Apprenants ---
        btnApprenant = new CustomButton("", Variant.TERTIARY, Size.MD);
        btnApprenant.setMaxWidth(Double.MAX_VALUE);
        btnApprenant.setGraphic(createButtonContent(
            Icons.user(20),
            "Apprenants"
        ));

        // --- Formations ---
        btnFormation = new CustomButton("", Variant.TERTIARY, Size.MD);
        btnFormation.setMaxWidth(Double.MAX_VALUE);
        btnFormation.setGraphic(createButtonContent(
            Icons.book(20),
            "Formations"
        ));


        setActive("apprenant");

        root.getChildren().addAll(title, btnApprenant, btnFormation);
    }

    public Node getNode() {
        return root;
    }

    public void setOnApprenant(Runnable r) {
        btnApprenant.setOnAction(e -> r.run());
    }

    public void setOnFormation(Runnable r) {
        btnFormation.setOnAction(e -> r.run());
    }

    private Node createButtonContent(Node icon, String text) {
        HBox box = new HBox(10);

        Label label = new Label(text);
        label.getStyleClass().add("sidebar-btn-text"); // <-- AJOUT ESSENTIEL

        HBox.setHgrow(label, Priority.ALWAYS);
        box.getChildren().addAll(icon, label);

        return box;
    }

    public void setActive(String key) {

        btnApprenant.getStyleClass().remove("active");
        btnFormation.getStyleClass().remove("active");

        switch (key) {
            case "formation" -> btnFormation.getStyleClass().add("active");
            default -> btnApprenant.getStyleClass().add("active");
        }
    }

}
