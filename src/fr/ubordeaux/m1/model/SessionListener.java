package fr.ubordeaux.m1.model;

public interface SessionListener {
    void sessionUpdated(Session source);
    void inscriptionSessionConfirmed(Session session, Apprenant apprenant);
    void inscriptionSessionWaitlisted(Session session, Apprenant apprenant);
    void inscriptionSessionCancelled(Session session, Apprenant apprenant);
    void sessionFull(Session session);
    void sessionReopened(Session session);
}
