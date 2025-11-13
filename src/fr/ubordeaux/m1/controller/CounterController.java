package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.Counter;
import fr.ubordeaux.m1.model.CounterListener;
import fr.ubordeaux.m1.view.AccueilView;

/**
 * Controller that binds a Counter model with the Accueil view.
 */
public class CounterController implements CounterListener {

    private final Counter model;
    private AccueilView view;

    public CounterController(Counter model, AccueilView view) {
        this.model = model;
        this.view = view;
        this.model.addListener(this);

        if (this.view != null) bindView();
    }

    /**
     * Replace the bound view at runtime and re-bind handlers/state.
     */
    public void setView(AccueilView view) {
        this.view = view;
        bindView();
    }

    /**
     * Bind the view controls to the model and initialize view state.
     */
    private void bindView() {
        if (this.view == null) return;
        // initialize view from model
        this.view.setCounterValue(this.model.getValue());
        // when the view signals an increment, update the model
        this.view.setOnIncrement(() -> this.model.increment());
    }

    @Override
    public void counterUpdated(Counter source) {
        view.setCounterValue(source.getValue());
    }
}
