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
    public void modifierSession(Session ancienne, Session nouvelle) {
        int index = model.indexOf(ancienne);
        if (index >= 0) {
            model.set(index, nouvelle);
            view.updateTable(model);
        }
    }

    public List<Session> getSessions() {
        return new ArrayList<>(model);
    }
}
