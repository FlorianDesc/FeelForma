package fr.ubordeaux.m1.model.listeners;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Session;

public interface SessionListener {
    void sessionUpdated(Session source);
    void inscriptionSessionConfirmed(Session session, Apprenant apprenant);
    void inscriptionSessionWaitlisted(Session session, Apprenant apprenant);
    void inscriptionSessionCancelled(Session session, Apprenant apprenant);
    void sessionFull(Session session);
    void sessionReopened(Session session);
}
