package fr.ubordeaux.m1.model.entities;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.model.listeners.FormationListener;

/**
 * Classe représentant une formation
 */
public class Formation {
    private String titre;
    private int duree; // durée en heures
    private String categorie;
    private List<Session> sessions;

    private final List<FormationListener> listeners = new ArrayList<>();

    // --- Constructeur privé utilisé par le Builder ---
    private Formation(Builder builder) {
        this.titre = builder.titre;
        this.duree = builder.duree;
        this.categorie = builder.categorie;
        this.sessions = new ArrayList<>(builder.sessions);
    }

    // --- Builder ---
    public static class Builder {
        private String titre;
        private int duree;
        private String categorie;
        private List<Session> sessions = new ArrayList<>();

        public Builder titre(String titre) {
            this.titre = titre;
            return this;
        }

        public Builder duree(int duree) {
            this.duree = duree;
            return this;
        }

        public Builder categorie(String categorie) {
            this.categorie = categorie;
            return this;
        }

        public Formation build() {
            return new Formation(this);
        }
    }

    public Formation(String titre, int duree, String categorie) {
        this.titre = titre;
        this.duree = duree;
        this.categorie = categorie;
        this.sessions = new ArrayList<>();
    }

    // --- Modification groupée des propriétés ---
    public void modifier(String nouveauTitre, int nouvelleDuree, String nouvelleCategorie) {
        this.titre = nouveauTitre;
        this.duree = nouvelleDuree;
        this.categorie = nouvelleCategorie;
        notifyFormationUpdated();
    }

    // --- Gestion des listeners ---
    public void addListener(FormationListener listener) {
        listeners.add(listener);
    }

    public void removeListener(FormationListener listener) {
        listeners.remove(listener);
    }

    private void notifyFormationUpdated() {
        for (FormationListener listener : listeners) {
            listener.formationUpdated(this);
        }
    }

    public void addSession(Session session) {
        sessions.add(session);
        for (FormationListener listener : listeners) {
            listener.sessionAdded(this, session);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
        for (FormationListener listener : listeners) {
            listener.sessionRemoved(this, session);
        }
    }

    // Getters et Setters
    public String getTitre() {
        return titre;
    }

    public int getDuree() {
        return duree;
    }

    public String getCategorie() {
        return categorie;
    }

    public List<Session> getSessions() {
        return new ArrayList<>(sessions);
    }

    @Override
    public String toString() {
        return "Formation{" +
                "titre='" + titre + '\'' +
                ", duree=" + duree +
                ", categorie='" + categorie + '\'' +
                ", nombre de sessions=" + sessions.size() +
                '}';
    }
}
