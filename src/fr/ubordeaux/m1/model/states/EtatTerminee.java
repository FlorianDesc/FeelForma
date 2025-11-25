package fr.ubordeaux.m1.model.states;

import fr.ubordeaux.m1.model.entities.Inscription;
import fr.ubordeaux.m1.model.entities.Session;

public class EtatTerminee implements SessionState {
    private final Session session;

    public EtatTerminee(Session session) {
        this.session = session;
    }

    @Override
    public void inscrire(Inscription inscription) {
        System.out.println("Impossible : session terminée");
    }

    @Override
    public String getLabel() {
        return "Terminée";
    }
}

