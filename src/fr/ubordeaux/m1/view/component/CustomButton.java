package fr.ubordeaux.m1.view.component;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CustomButton extends Button {

    public enum Variant { PRIMARY, SECONDARY, TERTIARY, DESTRUCTIVE }
    public enum Size { SM, MD, LG }

    private Variant variant = Variant.SECONDARY;
    private Size size = Size.MD;

    public CustomButton(String text, Variant variant, Size size) {
        super(text);
        getStyleClass().add("btn");
        setVariant(variant);
        setSize(size);

        // Observe changes to graphic
        ChangeListener<Node> listener = (obs, oldVal, newVal) -> {
            if (newVal != null) addTextClassToLabels(newVal);
        };
        graphicProperty().addListener(listener);
    }

    public CustomButton(String text, Variant variant) { this(text, variant, Size.MD); }
    public CustomButton(String text) { this(text, Variant.SECONDARY, Size.MD); }


    /** Ajoute automatiquement .btn-text à tous les labels du graphic */
    private void addTextClassToLabels(Node node) {
        if (node instanceof Label label) {
            label.getStyleClass().add("btn-text");
        } else if (node instanceof Parent parent) {
            for (Node child : parent.getChildrenUnmodifiable()) {
                addTextClassToLabels(child);
            }
        }
    }


    public void setVariant(Variant v) {
        // On supprime les anciens variants
        getStyleClass().removeIf(s ->
            s.equals("btn-primary") ||
            s.equals("btn-secondary") ||
            s.equals("btn-tertiary") ||
            s.equals("btn-destructive")
        );

        this.variant = v;

        // On ajoute la bonne classe CSS
        switch (v) {
            case PRIMARY -> getStyleClass().add("btn-primary");
            case SECONDARY -> getStyleClass().add("btn-secondary");
            case TERTIARY -> getStyleClass().add("btn-tertiary");
            case DESTRUCTIVE -> getStyleClass().add("btn-destructive");
        }
    }

    public Variant getVariant() { return variant; }


    public void setSize(Size s) {
        getStyleClass().removeIf(c ->
            c.equals("btn-sm") || c.equals("btn-md") || c.equals("btn-lg")
        );
        this.size = s;

        switch (s) {
            case SM -> getStyleClass().add("btn-sm");
            case LG -> getStyleClass().add("btn-lg");
            default -> getStyleClass().add("btn-md");
        }
    }

    public Size getSize() { return size; }
}
