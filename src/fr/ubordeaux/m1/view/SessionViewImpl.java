package fr.ubordeaux.m1.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class SessionViewImpl {

    private final StackPane root;

    public SessionViewImpl() {
        root = new StackPane();

        Label label = new Label("Vue de test : Sessions");
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        root.setPadding(new Insets(40));
        root.getChildren().add(label);
    }

    public StackPane getRoot() {
        return root;
    }
}
