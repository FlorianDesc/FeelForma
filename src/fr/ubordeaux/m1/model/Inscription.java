package fr.ubordeaux.m1.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Classe représentant une inscription à une session de formation
 */
public class Inscription {
    private Session session;
    private Apprenant apprenant;
    private LocalDateTime dateInscription;
    private EtatInscription etat;
    private static Queue<Inscription> listeAttente = new LinkedList<>();

    /**
     * Énumération des différents états possibles d'une inscription
     */
    public enum EtatInscription {
        CONFIRMEE,
        EN_ATTENTE,
        ANNULEE
    }

    /**
     * Constructeur de la classe Inscription
     * @param session la session concernée
     * @param apprenant l'apprenant qui s'inscrit
     */
    public Inscription(Session session, Apprenant apprenant) {
        this.session = session;
        this.apprenant = apprenant;
        this.dateInscription = LocalDateTime.now();
    }

    // Getters et Setters
    public Session getSession() {
        return session;
    }

    public Apprenant getApprenant() {
        return apprenant;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public EtatInscription getEtat() {
        return etat;
    }
}
