package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Inscription;
import fr.ubordeaux.m1.model.entities.Session;

public class InscriptionController {
    /**
     * Tente d'inscrire un apprenant dans une session.
     * Délègue à SessionState.
     */
    public void inscrire(Session session, Apprenant apprenant) {
        session.inscrire(apprenant);
    }

    /**
     * Annule une inscription confirmée ou en attente.
     */
    public void annulerInscription(Inscription inscription) {
        inscription.annuler();
    }

}
