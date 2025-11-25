package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Session;
import fr.ubordeaux.m1.model.listeners.ApprenantListener;

public class ApprenantController implements ApprenantListener {
    @Override
    public void sessionFormaAdded(Apprenant source, Session session) {
        System.out.println("Session ajoutée à l'historique de " + source + " : " + session);
    }

    @Override
    public void sessionFormaRemoved(Apprenant source, Session session) {
        System.out.println("Session retirée de l'historique de " + source + " : " + session);
    }

}
