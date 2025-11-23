package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.Apprenant;
import fr.ubordeaux.m1.view.ApprenantView;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur de la vue des apprenants.
 * Relie le modèle (liste d'apprenants) et la vue ApprenantView.
 */
public class ApprenantController {
    private final List<Apprenant> model;
    private ApprenantView view;

    /**
     * Constructeur du contrôleur des apprenants.
     * @param view la vue des apprenants
     */
    public ApprenantController(ApprenantView view) {
        this.model = new ArrayList<>();
        this.view = view;
    }

    /**
     * Permet de redéfinir la vue dynamiquement.
     */
    public void setView(ApprenantView view) {
        this.view = view;
    }

    /**
     * Ajoute un nouvel apprenant au modèle.
     * @param apprenant l'apprenant à ajouter
     */
    public void ajouterApprenant(Apprenant apprenant) {
        model.add(apprenant);
    }

    /**
     * Crée et ajoute un nouvel apprenant avec des valeurs par défaut.
     */
    public void ajouterApprenant() {
        Apprenant nouvelApprenant = new Apprenant(
            "Nouveau", 
            "Apprenant", 
            "", 
            "", 
            "nouveau.apprenant@example.com"
        );
        ajouterApprenant(nouvelApprenant);
    }

    /**
     * Retourne la liste des apprenants du modèle.
     * @return la liste des apprenants
     */
    public List<Apprenant> getApprenants() {
        return new ArrayList<>(model);
    }
}

