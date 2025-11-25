package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Inscription;
import fr.ubordeaux.m1.model.entities.Session;

public class InscriptionController {
    public void inscrire(Session session, Apprenant apprenant) {
        session.inscrire(apprenant);
    }

    public void annulerInscription(Inscription inscription) {
        inscription.annuler();
        inscription.getSession().libererPlace(inscription);
    }

}
