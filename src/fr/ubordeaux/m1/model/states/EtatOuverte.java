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
        session.getInscrits().add(apprenant);
        
        if (session.getInscrits().size() <= session.getNbPlacesMax()) {
            session.notifyInscriptionConfirmed(apprenant);

            if (session.getInscrits().size() == session.getNbPlacesMax()) {
                session.changeState(new EtatComplete(session));
                session.notifySessionFull();
            }
        } else {
            // Si on dépasse le nombre de places, on met en attente
            session.notifyInscriptionWaitlisted(apprenant);
        }
    }

    @Override
    public String getLabel() {
        return "Ouverte";
    }
}

