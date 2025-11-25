package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.entities.Inscription;
import fr.ubordeaux.m1.model.listeners.InscriptionListener;

public class InscriptionController implements InscriptionListener {
    public void inscriptionCreated(Inscription source){

    }

    public void inscriptionStateChanged(Inscription source, Inscription.EtatInscription oldState, Inscription.EtatInscription newState){

    }

    public void inscriptionConfirmed(Inscription source){

    }

    public void inscriptionWaitlisted(Inscription source){

    }

    public void inscriptionCancelled(Inscription source){

    }

}
