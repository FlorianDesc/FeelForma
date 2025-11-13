package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.Formateur;
import fr.ubordeaux.m1.model.FormateurListener;

public class FormateurController implements FormateurListener {
    @Override
    public void formateurUpdated(Formateur source) {
        // Implémentation de la gestion de la mise à jour du formateur
    }

    @Override
    public void formateurAdded(Formateur source) {
        // Implémentation de la gestion de l'ajout d'un formateur
    }

    @Override
    public void formateurRemoved(Formateur source) {
        // Implémentation de la gestion de la suppression d'un formateur
    }

}
