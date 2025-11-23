package fr.ubordeaux.m1.model;

/**
 * Interface d'écoute pour les changements d'état d'un Apprenant.
 * Implémente le pattern Observer.
 */
public interface ApprenantListener {
    void sessionFormaAdded(Apprenant source, Session session);
    void sessionFormaRemoved(Apprenant source, Session session);
}