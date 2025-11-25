package fr.ubordeaux.m1.model.entities;

import java.time.LocalDateTime;

/**
 * Classe représentant une inscription à une session de formation
 */
public class Inscription {
    private final Session session;
    private final Apprenant apprenant;
    private final LocalDateTime dateInscription;
    private boolean estConfirmee;

    public Inscription(Session session, Apprenant apprenant) {
        this.session = session;
        this.apprenant = apprenant;
        this.dateInscription = LocalDateTime.now();
        this.estConfirmee = false;
    }

    public void confirmer() {
        estConfirmee = true;
        session.notifyInscriptionConfirmed(apprenant);
        apprenant.ajouterSessionSuivie(session);
    }

    public void mettreEnAttente() {
        session.notifyInscriptionWaitlisted(apprenant);
    }
    public void annuler() {
        session.notifyInscriptionCancelled(apprenant);
        apprenant.retirerSessionSuivie(session);
    }

    public Session getSession() {
        return session;
    }

    public Apprenant getApprenant() {
        return apprenant;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public boolean estConfirmee() {
        return estConfirmee;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "apprenant=" + apprenant.getNom() +
                ", date=" + dateInscription +
                ", estConfirmee=" + estConfirmee +'}';
    }
}
