package fr.ubordeaux.m1.model.states;

import fr.ubordeaux.m1.model.entities.Inscription;
import fr.ubordeaux.m1.model.entities.Session;

public class EtatOuverte implements SessionState {
    private final Session session;

    public EtatOuverte(Session session) {
        this.session = session;
    }

    @Override
    public void inscrire(Inscription inscription) {
        // Vérifier si la session est pleine avant d'inscrire
        if (session.getNbInscrits() >= session.getNbPlacesMax()) {
            // La session est pleine, mettre en liste d'attente
            session.mettreEnListeAttente(inscription);
        } else {
            // La session a encore de la place, confirmer l'inscription
            session.confirmerInscription(inscription);
        }
    }

    @Override
    public String getLabel() {
        return "Ouverte";
    }
}

