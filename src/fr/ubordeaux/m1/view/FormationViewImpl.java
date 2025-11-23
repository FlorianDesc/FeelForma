package fr.ubordeaux.m1.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Simple implementation of the Formation page used for navigation demo.
 */
public class FormationViewImpl implements FormationView {
    private final VBox root;

    public FormationViewImpl() {
        root = new VBox(12);
        root.setAlignment(Pos.CENTER);

        Label titre = new Label("Page Formations");
        Label message = new Label("Liste des formations - contenu de démonstration");

        root.getChildren().addAll(titre, message);
    }

    @Override
    public Pane getRoot() { return root; }
}
