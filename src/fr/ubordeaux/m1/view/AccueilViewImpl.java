package fr.ubordeaux.m1.view;

import fr.ubordeaux.m1.view.component.CustomButton;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Implémentation concrète de la page d'accueil.
 */
public class AccueilViewImpl implements AccueilView {
    private final VBox root;
    private final Label counterLabel;
    private final CustomButton incrementButton;
    private Runnable onIncrement;

    public AccueilViewImpl() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Label titre = new Label("Bienvenue dans l’application de gestion du centre de formation");
        Label message = new Label("Hello World");

        // counter area
        counterLabel = new Label("0");
        counterLabel.setStyle("-fx-font-size:16px; -fx-font-weight:600;");
        incrementButton = new CustomButton("+1", CustomButton.Variant.PRIMARY);
        incrementButton.setOnAction(e -> { if (onIncrement != null) onIncrement.run(); });

        HBox counterBox = new HBox(8, incrementButton, counterLabel);
        counterBox.setAlignment(Pos.CENTER_LEFT);
        counterBox.setPadding(new Insets(8));

        root.getChildren().addAll(titre, message, counterBox);
    }

    @Override
    public VBox getRoot() {
        return root;
    }

    @Override
    public void setCounterValue(int value) {
        // ensure UI update runs on FX thread
        Platform.runLater(() -> counterLabel.setText(Integer.toString(value)));
    }

    @Override
    public void setOnIncrement(Runnable handler) {
        this.onIncrement = handler;
    }
}
