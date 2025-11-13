package fr.ubordeaux.m1.model;

/**
 * Interface d'écoute pour les changements d'état d'un Apprenant.
 * Implémente le pattern Observer.
 */
public interface ApprenantListener {
    void apprenantUpdated(Apprenant source);
    void apprenantionAdded(Apprenant source);
    void apprenantionRemoved(Apprenant source);

    void sessionFormaAdded(Apprenant source, Session session);
    void sessionFormaRemoved(Apprenant source, Session session);
}