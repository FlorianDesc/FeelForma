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

    // Getters et Setters
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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
