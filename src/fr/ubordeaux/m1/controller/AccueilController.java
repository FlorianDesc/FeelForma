package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.Apprenant;
import fr.ubordeaux.m1.view.AccueilView;

/**
 * Contrôleur de la vue d'accueil.
 * Relie le modèle Apprenant et la vue AccueilView.
 */
public class AccueilController {

    private final Apprenant model;
    private AccueilView view;

    /**
     * Constructeur du contrôleur d'accueil.
     * @param model le modèle Apprenant
     * @param view la vue d'accueil
     */
    public AccueilController(Apprenant model, AccueilView view) {
        this.model = model;
        this.view = view;

        if (view != null) {
            bindView();
        }
    }

    /**
     * Permet de redéfinir la vue dynamiquement.
     */
    public void setView(AccueilView view) {
        this.view = view;
        bindView();
    }

    /**
     * Lie les actions de la vue avec le modèle et
     * s’abonne aux changements du modèle.
     */
    private void bindView() {

        // Initialisation de la vue à partir du modèle
        System.out.println("test");
    }
}
