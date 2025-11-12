package fr.ubordeaux.m1.view.component;

import javafx.scene.control.Button;

/**
 * CustomButton centralises button variants used across the app.
 * It supports two variants: PRIMARY and SECONDARY.
 */
public class CustomButton extends Button {

    public enum Variant { PRIMARY, SECONDARY }

    public CustomButton(String text, Variant variant) {
        super(text);
        getStyleClass().add("btn");
        if (variant == Variant.PRIMARY) getStyleClass().add("btn-primary");
        else getStyleClass().add("btn-secondary");
    }

    public CustomButton(String text) { this(text, Variant.SECONDARY); }
}
