package fr.ubordeaux.m1.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un formateur
 */
public class Formateur {
    private String nom;
    private String prenom;
    private List<String> specialites;

    /**
     * Constructeur de la classe Formateur
     * @param nom le nom du formateur
     * @param prenom le prénom du formateur
     */
    public Formateur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.specialites = new ArrayList<>();
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<String> getSpecialites() {
        return specialites;
    }

    @Override
    public String toString() {
        return "Formateur{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", specialites=" + specialites +
                '}';
    }
}
