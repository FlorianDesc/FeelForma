package fr.ubordeaux.m1.view.component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Labeled text input used across forms.
 */
public class TextInput extends HBox {

    private final Label label;
    private final TextField field;

    public TextInput(String labelText, String prompt) {
        super(8);
        this.label = new Label(labelText);
        this.label.getStyleClass().add("input-label");
        this.field = new TextField();
        this.field.setPromptText(prompt);
        this.field.getStyleClass().add("input-field");
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(this.label, this.field);
    }

    public TextField getField() { return field; }
    public String getText() { return field.getText(); }
}
