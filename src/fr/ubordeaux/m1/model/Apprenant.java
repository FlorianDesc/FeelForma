package fr.ubordeaux.m1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un apprenant
 */
public class Apprenant {
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private List<Session> historiqueFormations;

    /**
     * Constructeur de la classe Apprenant
     * @param nom le nom de l'apprenant
     * @param prenom le prénom de l'apprenant
     * @param adresse l'adresse de l'apprenant
     * @param telephone le numéro de téléphone de l'apprenant
     * @param email l'email de l'apprenant
     */
    public Apprenant(String nom, String prenom, String adresse, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.historiqueFormations = new ArrayList<>();
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Session> getHistoriqueFormations() {
        return historiqueFormations;
    }

    @Override
    public String toString() {
        return "Apprenant{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
