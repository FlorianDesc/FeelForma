package fr.ubordeaux.m1.controller;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.model.entities.Formation;
import fr.ubordeaux.m1.model.entities.Session;
import fr.ubordeaux.m1.view.SessionView;

public class SessionController {

    private final Formation formation;
    private final List<Session> model;
    private SessionView view;

    public SessionController(Formation formation, SessionView view) {
        this.formation = formation;
        this.model = new ArrayList<>(formation.getSessions());
        this.view = view;
        // Initialiser la vue avec les sessions existantes
        if (view != null) {
            view.updateTable(model);
        }
    }

    public void setView(SessionView view) {
        this.view = view;
    }

    public Formation getFormation() {
        return formation;
    }

    // === AJOUT ===
    public void ajouterSession(Session session) {
        model.add(session);
        formation.addSession(session);
        view.updateTable(model);
    }

    // === SUPPRESSION ===
    public void supprimerSession(Session session) {
        model.remove(session);
        formation.removeSession(session);
        view.updateTable(model);
    }

    // === MODIFICATION ===
    public void modifierSession(Session ancienne, Session nouvelle, String etatLabel) {
        int index = model.indexOf(ancienne);
        if (index >= 0) {
            // Conserver les inscriptions et la liste d'attente de l'ancienne session
            for (fr.ubordeaux.m1.model.entities.Inscription inscription : ancienne.getInscriptions()) {
                nouvelle.confirmerInscription(inscription);
            }
            for (fr.ubordeaux.m1.model.entities.Inscription inscription : ancienne.getListeAttente()) {
                nouvelle.mettreEnListeAttente(inscription);
            }
            
            // Valider la cohérence de l'état avec le nombre d'inscrits
            int nbInscrits = nouvelle.getNbInscrits();
            int nbPlacesMax = nouvelle.getNbPlacesMax();
            
            // Si la session est pleine, on ne peut pas la mettre en "Ouverte"
            if (nbInscrits >= nbPlacesMax && etatLabel.equals("Ouverte")) {
                // Forcer l'état "Complète" car la session est pleine
                etatLabel = "Complète";
            }
            // Si la session n'est pas pleine, on ne peut pas la mettre en "Complète" manuellement
            else if (nbInscrits < nbPlacesMax && etatLabel.equals("Complète")) {
                // Forcer l'état "Ouverte" car il reste des places
                etatLabel = "Ouverte";
            }
            
            // Appliquer l'état validé APRÈS le transfert des inscriptions
            fr.ubordeaux.m1.model.states.SessionState etat = creerEtatDepuisLabel(etatLabel, nouvelle);
            nouvelle.changeState(etat);
            
            model.set(index, nouvelle);
            view.updateTable(model);
        }
    }
    
    private fr.ubordeaux.m1.model.states.SessionState creerEtatDepuisLabel(String label, Session session) {
        return switch (label) {
            case "Ouverte" -> new fr.ubordeaux.m1.model.states.EtatOuverte(session);
            case "Complète" -> new fr.ubordeaux.m1.model.states.EtatComplete(session);
            case "Terminée" -> new fr.ubordeaux.m1.model.states.EtatTerminee(session);
            case "Annulée" -> new fr.ubordeaux.m1.model.states.EtatAnnulee(session);
            default -> new fr.ubordeaux.m1.model.states.EtatOuverte(session);
        };
    }

    public List<Session> getSessions() {
        return new ArrayList<>(model);
    }
}
