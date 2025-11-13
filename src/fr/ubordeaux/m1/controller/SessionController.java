package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.Apprenant;
import fr.ubordeaux.m1.model.Session;
import fr.ubordeaux.m1.model.SessionListener;

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
