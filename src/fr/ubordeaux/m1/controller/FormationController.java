package fr.ubordeaux.m1.controller;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.model.entities.Formation;
import fr.ubordeaux.m1.model.entities.Session;
import fr.ubordeaux.m1.model.listeners.FormationListener;
import fr.ubordeaux.m1.view.FormationViewImpl;

public class FormationController implements FormationListener {
    private final List<Formation> formations = new ArrayList<>();
    private FormationViewImpl view;
    
    public void setView(FormationViewImpl view) {
        this.view = view;
    }
    
    // --- Méthodes pour manipuler les formations ---
    public void ajouterFormation(Formation formation) {
        formations.add(formation);
        formation.addListener(this);
        if (view != null) {
            view.ajouterFormationAuTableau(formation);
        }
    }

    public void supprimerFormation(Formation formation) {
        formations.remove(formation);
        if (view != null) {
            view.supprimerFormationDuTableau(formation);
        }
    }

    public void modifierFormation(Formation formation, String titre, int duree, String categorie) {
        formation.modifier(titre, duree, categorie);
        if (view != null) {
            view.updateTable(formations);
        }
    }

    public List<Formation> getFormations() {
        return new ArrayList<>(formations);
    }

    // --- Méthodes pour manipuler les sessions ---
    public void ajouterSession(Formation formation, Session session) {
        formation.addSession(session);
    }

    public void supprimerSession(Formation formation, Session session) {
        formation.removeSession(session);
    }

    //Listeners methods
    @Override
    public void formationUpdated(Formation source) {
        System.out.println("Formation mise à jour : " + source.getTitre());
    }

    @Override
    public void formationAdded(Formation source) {
        System.out.println("Formation ajoutée : " + source.getTitre());
    }

    @Override
    public void formationRemoved(Formation source) {
        System.out.println("Formation supprimée : " + source.getTitre());
    }

    @Override
    public void sessionAdded(Formation formation, Session session) {
        System.out.println("Session ajoutée à " + formation.getTitre());
    }

    @Override
    public void sessionRemoved(Formation formation, Session session) {
        System.out.println("Session supprimée de " + formation.getTitre());
    }
}
