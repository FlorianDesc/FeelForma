package fr.ubordeaux.m1.model.states;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Session;

public class EtatComplete implements SessionState {
    private final Session session;

    public EtatComplete(Session session) {
        this.session = session;
    }

    @Override
    public void inscrire(Apprenant apprenant) {
        session.notifyInscriptionWaitlisted(apprenant);
    }

    @Override
    public String getLabel() {
        return "Complète";
    }
}

