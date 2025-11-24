package fr.ubordeaux.m1.model.listeners;

import fr.ubordeaux.m1.model.entities.Inscription;

public interface InscriptionListener {
    void inscriptionCreated(Inscription source);
    void inscriptionStateChanged(Inscription source, Inscription.EtatInscription oldState, Inscription.EtatInscription newState);
    void inscriptionConfirmed(Inscription source);
    void inscriptionWaitlisted(Inscription source);
    void inscriptionCancelled(Inscription source);
}
