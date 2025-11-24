package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Session;
import fr.ubordeaux.m1.model.listeners.SessionListener;

public class SessionController implements SessionListener {
    @Override
    public void sessionUpdated(Session session) {
        System.out.println("État mis à jour : " + session.getEtat().getLabel());
    }

    @Override
    public void inscriptionSessionConfirmed(Session session, Apprenant apprenant) {
        System.out.println("Inscription validée : " + apprenant.getNom());
    }

    @Override
    public void inscriptionSessionWaitlisted(Session session, Apprenant apprenant) {
        System.out.println("Liste d'attente : " + apprenant.getNom());
    }

    @Override
    public void inscriptionSessionCancelled(Session session, Apprenant apprenant) {
        System.out.println("Inscription refusée : " + apprenant.getNom());
    }

    @Override
    public void sessionFull(Session session) {
        System.out.println("Session complète !");
    }

    @Override
    public void sessionReopened(Session session) {
        System.out.println("La session est réouverte.");
    }


}
