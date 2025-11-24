package fr.ubordeaux.m1.model.states;

import fr.ubordeaux.m1.model.entities.Apprenant;

public interface SessionState {
    void inscrire(Apprenant apprenant);
    String getLabel();
}
