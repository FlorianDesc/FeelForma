package fr.ubordeaux.m1.model.entities;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fr.ubordeaux.m1.model.listeners.InscriptionListener;

/**
 * Classe représentant une inscription à une session de formation
 */
public class Inscription {
    private Session session;
    private Apprenant apprenant;
    private LocalDateTime dateInscription;
    private EtatInscription etat;
    private static Queue<Inscription> listeAttente = new LinkedList<>();

    private final List<InscriptionListener> listeners = new LinkedList<>();

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

    // --- Gestion des listeners ---
    public void addListener(InscriptionListener listener) {
        listeners.add(listener);
    }
    public void removeListener(InscriptionListener listener) {
        listeners.remove(listener);
    }
    private void notifyListeners() {
        for (InscriptionListener listener : listeners) {
            listener.inscriptionConfirmed(this);
        }
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
