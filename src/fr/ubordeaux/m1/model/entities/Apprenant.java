package fr.ubordeaux.m1.model.entities;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.model.listeners.ApprenantListener;

/**
 * Classe représentant un apprenant (modèle).
 * Notifie ses listeners lors de toute mise à jour.
 */
public class Apprenant {
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;

    private final List<ApprenantListener> listeners = new ArrayList<>();

    /**
     * Constructeur d'un apprenant.
     * @param nom le nom
     * @param prenom le prénom
     * @param adresse l'adresse
     * @param telephone le téléphone
     * @param email l'adresse e-mail
     */
    public Apprenant(String nom, String prenom, String adresse, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    // --- Gestion des listeners ---
    public void addListener(ApprenantListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ApprenantListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ApprenantListener listener : listeners) {
            listener.apprenantUpdated(this);
        }
    }

    // --- Getters / Setters avec notification ---
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + email + ")";
    }
}
