package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.view.FormationView;
import javafx.stage.Stage;

/**
 * Controller for Formation-related views. Non-static: creates view instances.
 */
public class FormationController {

    public FormationController() {
    }

    /**
     * Create and return a FormationView instance wired with the provided callback.
     */
    public FormationView createFormationView(Stage stage, Runnable onBack) {
        return new FormationView(stage, onBack);
    }
}
