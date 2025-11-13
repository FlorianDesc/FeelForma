package fr.ubordeaux.m1.model;

public interface FormateurListener {

    void formateurUpdated(Formateur source);
    void formateurAdded(Formateur source);
    void formateurRemoved(Formateur source);
}
