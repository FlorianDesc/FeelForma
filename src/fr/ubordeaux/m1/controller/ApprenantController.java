package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.Apprenant;
import fr.ubordeaux.m1.model.ApprenantListener;
import fr.ubordeaux.m1.model.Session;

public class ApprenantController implements ApprenantListener {
    @Override
    public void apprenantUpdated(Apprenant source) {
        // Implémentation de la gestion de la mise à jour de l'apprenant
    }

    @Override
    public void apprenantionAdded(Apprenant source) {
        // Implémentation de la gestion de l'ajout d'une inscription
    }

    @Override
    public void apprenantionRemoved(Apprenant source) {
        // Implémentation de la gestion de la suppression d'une inscription
    }

    @Override
    public void sessionFormaAdded(Apprenant source, Session session) {
        // Implémentation de la gestion de l'ajout d'une formation à un apprenant
    }

    @Override
    public void sessionFormaRemoved(Apprenant source, Session session) {
        // Implémentation de la gestion de la suppression d'une formation à un apprenant
    }

}
