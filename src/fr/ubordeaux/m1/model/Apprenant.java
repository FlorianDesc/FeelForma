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

    // Getters
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
