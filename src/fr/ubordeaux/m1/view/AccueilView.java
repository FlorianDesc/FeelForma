package fr.ubordeaux.m1.view;

import javafx.scene.layout.Pane;

/**
 * Interface représentant la vue d'accueil.
 * Elle définit les comportements que toute implémentation doit fournir.
 */
public interface AccueilView {
    Pane getRoot();

    /**
     * Set the numeric counter value displayed in the view.
     */
    void setCounterValue(int value);

    /**
     * Register a handler that will be invoked when the view's increment button is pressed.
     */
    void setOnIncrement(Runnable handler);
}
