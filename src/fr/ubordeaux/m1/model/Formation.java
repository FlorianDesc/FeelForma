package fr.ubordeaux.m1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une formation
 */
public class Formation {
    private String titre;
    private int duree; // durée en heures
    private String categorie;
    private List<Session> sessions;

    private final List<FormationListener> listeners = new ArrayList<>();

    /**
     * Constructeur de la classe Formation
     * @param titre le titre de la formation
     * @param duree la durée de la formation en heures
     * @param categorie la catégorie de la formation (informatique, management, langues, etc.)
     */
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
