package fr.ubordeaux.m1.view.component;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * Simple card container used to group content with subtle elevation.
 */
public class CardView extends VBox {

    public CardView() {
        super();
        getStyleClass().add("card");
        setPadding(new Insets(12));
        setSpacing(8);
    }
}
