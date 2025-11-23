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
    private final List<Session> sessionsSuivies = new ArrayList<>();

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

    public void ajouterSessionSuivie(Session session) {
        if (!sessionsSuivies.contains(session)) {
            sessionsSuivies.add(session);
            for (ApprenantListener listener : listeners) {
                listener.sessionFormaAdded(this, session);
            }
        }
    }

    public void retirerSessionSuivie(Session session) {
        if (sessionsSuivies.remove(session)) {
            for (ApprenantListener listener : listeners) {
                listener.sessionFormaRemoved(this, session);
            }
        }
    }

    public List<Session> getSessionsSuivies() {
        return new ArrayList<>(sessionsSuivies);
    }

    public void addListener(ApprenantListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ApprenantListener listener) {
        listeners.remove(listener);
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
