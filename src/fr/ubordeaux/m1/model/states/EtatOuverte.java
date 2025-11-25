package fr.ubordeaux.m1.model.states;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Session;

public class EtatOuverte implements SessionState {
    private final Session session;

    public EtatOuverte(Session session) {
        this.session = session;
    }

    @Override
    public void inscrire(Apprenant apprenant) {
        if (session.getInscrits().size() < session.getNbPlacesMax()) {
            session.getInscrits().add(apprenant);
            session.notifyInscriptionConfirmed(apprenant);

            if (session.getInscrits().size() == session.getNbPlacesMax()) {
                session.changeState(new EtatComplete(session));
                session.notifySessionFull();
            }
        }
    }

    @Override
    public String getLabel() {
        return "Ouverte";
    }
}

