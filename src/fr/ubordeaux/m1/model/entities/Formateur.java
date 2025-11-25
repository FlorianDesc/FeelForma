package fr.ubordeaux.m1.model.entities;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.model.listeners.FormateurListener;

/**
 * Classe représentant un formateur
 */
public class Formateur {
    private String nom;
    private String prenom;
    private List<String> specialites;

    private final List<FormateurListener> listeners = new ArrayList<>();

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

    // --- Gestion des listeners ---
    public void addListener(FormateurListener listener) {
        listeners.add(listener);
    }

    public void removeListener(FormateurListener listener) {
        listeners.remove(listener);
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public List<String> getSpecialites() {
        return new ArrayList<>(specialites);
    }

    public void ajouterSpecialite(String specialite) {
        if (!specialites.contains(specialite)) {
            specialites.add(specialite);
        }
    }

    public void retirerSpecialite(String specialite) {
        specialites.remove(specialite);
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}
