package fr.ubordeaux.m1.model.listeners;

import fr.ubordeaux.m1.model.entities.Formateur;

public interface FormateurListener {

    void formateurUpdated(Formateur source);
    void formateurAdded(Formateur source);
    void formateurRemoved(Formateur source);
}
