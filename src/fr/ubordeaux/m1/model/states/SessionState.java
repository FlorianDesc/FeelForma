package fr.ubordeaux.m1.model.states;

import fr.ubordeaux.m1.model.entities.Inscription;

public interface SessionState {
    void inscrire(Inscription inscription);
    String getLabel();
}
