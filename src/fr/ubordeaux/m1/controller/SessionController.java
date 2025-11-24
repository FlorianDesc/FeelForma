package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Session;
import fr.ubordeaux.m1.model.listeners.SessionListener;

public class SessionController implements SessionListener {
    public void sessionUpdated(Session source){

    }

    public void inscriptionSessionConfirmed(Session session, Apprenant apprenant){

    }

    public void inscriptionSessionWaitlisted(Session session, Apprenant apprenant){

    }

    public void inscriptionSessionCancelled(Session session, Apprenant apprenant){

    }

    public void sessionFull(Session session){

    }

    public void sessionReopened(Session session){

    }

}
