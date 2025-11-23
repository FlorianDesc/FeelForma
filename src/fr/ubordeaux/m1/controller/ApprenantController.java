package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.Apprenant;
import fr.ubordeaux.m1.model.ApprenantListener;
import fr.ubordeaux.m1.model.Session;

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
