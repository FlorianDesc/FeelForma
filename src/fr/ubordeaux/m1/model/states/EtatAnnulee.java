package fr.ubordeaux.m1.model.states;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Session;

public class EtatAnnulee implements SessionState {
    private final Session session;

    public EtatAnnulee(Session session) {
        this.session = session;
    }

    @Override
    public void inscrire(Apprenant apprenant) {
        session.notifyInscriptionCancelled(apprenant);
    }

    @Override
    public String getLabel() {
        return "Annulée";
    }
}

