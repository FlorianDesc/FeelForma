package fr.ubordeaux.m1.model;

public interface InscriptionListener {
    void inscriptionCreated(Inscription source);
    void inscriptionStateChanged(Inscription source, Inscription.EtatInscription oldState, Inscription.EtatInscription newState);
    void inscriptionConfirmed(Inscription source);
    void inscriptionWaitlisted(Inscription source);
    void inscriptionCancelled(Inscription source);
}
