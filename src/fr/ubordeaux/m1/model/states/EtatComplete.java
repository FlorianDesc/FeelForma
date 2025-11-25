package fr.ubordeaux.m1.model.states;

import fr.ubordeaux.m1.model.entities.Inscription;
import fr.ubordeaux.m1.model.entities.Session;

public class EtatComplete implements SessionState {
    private final Session session;

    public EtatComplete(Session session) {
        this.session = session;
    }

    @Override
    public void inscrire(Inscription inscription) {
        session.mettreEnListeAttente(inscription);
    }

    @Override
    public String getLabel() {
        return "Complète";
    }
}

