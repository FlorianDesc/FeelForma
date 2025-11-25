package fr.ubordeaux.m1.model.entities;

import java.time.LocalDateTime;

/**
 * Classe représentant une inscription à une session de formation
 */
public class Inscription {
    private Session session;
    private Apprenant apprenant;
    private LocalDateTime dateInscription;
    private EtatInscription etat;

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
        this.etat = EtatInscription.EN_ATTENTE;
    }

    // --- Méthodes appelées par SessionState ---
    public void confirmer() {
        etat = EtatInscription.CONFIRMEE;
        session.notifyInscriptionConfirmed(apprenant);
    }

    public void mettreEnAttente() {
        etat = EtatInscription.EN_ATTENTE;
        session.notifyInscriptionWaitlisted(apprenant);
    }

    public void annuler() {
        etat = EtatInscription.ANNULEE;
        session.notifyInscriptionCancelled(apprenant);
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

    @Override
    public String toString() {
        return "Inscription{" +
                "apprenant=" + apprenant.getNom() +
                ", date=" + dateInscription +
                ", etat=" + etat +
                '}';
    }
}
