package fr.ubordeaux.m1.model;

import java.util.ArrayList;
import java.util.List;

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

    public void setNom(String nom) {
        this.nom = nom;
        notifyListeners();
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
        notifyListeners();
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
        notifyListeners();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
        notifyListeners();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyListeners();
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + email + ")";
    }
}
