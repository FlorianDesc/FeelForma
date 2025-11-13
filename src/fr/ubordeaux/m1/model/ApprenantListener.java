package fr.ubordeaux.m1.model;

/**
 * Interface d'écoute pour les changements d'état d'un Apprenant.
 * Implémente le pattern Observer.
 */
public interface ApprenantListener {
    /**
     * Méthode appelée lorsque les données d'un apprenant sont modifiées.
     * @param source l'apprenant ayant déclenché la notification
     */
    void apprenantUpdated(Apprenant source);
}
