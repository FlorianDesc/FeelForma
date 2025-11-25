package fr.ubordeaux.m1.model.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fr.ubordeaux.m1.model.listeners.SessionListener;
import fr.ubordeaux.m1.model.states.EtatComplete;
import fr.ubordeaux.m1.model.states.EtatOuverte;
import fr.ubordeaux.m1.model.states.SessionState;

/**
 * Classe représentant une session de formation
 */
public class Session {
    private Formation formation;
    private Formateur formateur;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nbPlacesMax;
    private List<Inscription> inscriptions = new ArrayList<>();
    private Queue<Inscription> listeAttente = new LinkedList<>();
    private SessionState etat;

    private final List<SessionListener> listeners = new ArrayList<>();

    /**
     * Constructeur de la classe Session
     * @param formation la formation associée
     * @param formateur le formateur qui anime la session
     * @param dateDebut la date de début de la session
     * @param dateFin la date de fin de la session
     * @param nbPlacesMax le nombre maximum de places disponibles
     */
    public Session(Formation formation, Formateur formateur, LocalDate dateDebut, 
                  LocalDate dateFin, int nbPlacesMax) {
        this.formation = formation;
        this.formateur = formateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbPlacesMax = nbPlacesMax;
        this.etat = new EtatOuverte(this);

    }

    public void inscrire(Apprenant apprenant) {
        Inscription inscription = new Inscription(this, apprenant);
        etat.inscrire(inscription);
    }
    
    public void confirmerInscription(Inscription inscription) {
        inscriptions.add(inscription);
        inscription.confirmer();
        
        // Créer notification pour l'apprenant inscrit
        Apprenant apprenant = inscription.getApprenant();
        String message = String.format("Vous êtes inscrit à la session '%s' du %s au %s", 
            formation.getTitre(), dateDebut, dateFin);
        apprenant.ajouterNotification(new Notification(message, Notification.TypeNotification.INSCRIPTION_CONFIRMEE));
        
        // Notifier tous les autres inscrits qu'une nouvelle personne a rejoint
        for (Inscription i : inscriptions) {
            if (!i.equals(inscription)) {
                String messageAutres = String.format("%s %s a rejoint la session '%s'", 
                    apprenant.getPrenom(), apprenant.getNom(), formation.getTitre());
                i.getApprenant().ajouterNotification(
                    new Notification(messageAutres, Notification.TypeNotification.INSCRIPTION_CONFIRMEE));
            }
        }
        
        if (inscriptions.size() >= nbPlacesMax) {
            changeState(new EtatComplete(this));
            notifySessionFull(this);
        }
    }

    public void mettreEnListeAttente(Inscription inscription) {
        listeAttente.offer(inscription);
        inscription.mettreEnAttente();
        
        // Notification pour l'apprenant mis en attente
        Apprenant apprenant = inscription.getApprenant();
        String message = String.format("Vous êtes en liste d'attente pour la session '%s' (session complète)", 
            formation.getTitre());
        apprenant.ajouterNotification(new Notification(message, Notification.TypeNotification.INSCRIPTION_EN_ATTENTE));
    }
    
    public void libererPlace(Inscription inscription) {
        inscriptions.remove(inscription);
        
        // Notification d'annulation
        Apprenant apprenantSortant = inscription.getApprenant();
        String messageAnnulation = String.format("Votre inscription à la session '%s' a été annulée", 
            formation.getTitre());
        apprenantSortant.ajouterNotification(
            new Notification(messageAnnulation, Notification.TypeNotification.INSCRIPTION_ANNULEE));
        
        Inscription prochaine = listeAttente.poll();
        if (prochaine != null) {
            // Notification pour celui qui passe de la liste d'attente aux inscrits
            String messagePlaceLibere = String.format("Une place s'est libérée ! Vous êtes maintenant inscrit à la session '%s'", 
                formation.getTitre());
            prochaine.getApprenant().ajouterNotification(
                new Notification(messagePlaceLibere, Notification.TypeNotification.PLACE_LIBEREE));
            
            confirmerInscription(prochaine);
            notifySessionReopened(this);
        }
    }

    // --- State pattern ---
    
    public void changeState(SessionState newState) {
        this.etat = newState;
        notifyUpdate();
    }

    // --- Listeners ---
    
    public void addListener(SessionListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SessionListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyUpdate() {
        for (SessionListener listener : listeners) {
            listener.sessionUpdated(this);
        }
    }
    
    public void notifyInscriptionConfirmed(Apprenant apprenant) {
        for (SessionListener listener : listeners) {
            listener.inscriptionSessionConfirmed(this, apprenant);
        }
    }
    
    public void notifyInscriptionWaitlisted(Apprenant apprenant) {
        for (SessionListener listener : listeners) {
            listener.inscriptionSessionWaitlisted(this, apprenant);
        }
    }
    
    public void notifyInscriptionCancelled(Apprenant apprenant) {
        for (SessionListener listener : listeners) {
            listener.inscriptionSessionCancelled(this, apprenant);
        }
    }

    private void notifySessionFull(Session session) {
        for (SessionListener listener : listeners) {
            listener.sessionFull(session);
        }
    }
    
    private void notifySessionReopened(Session session) {
        for (SessionListener listener : listeners) {
            listener.sessionReopened(session);
        }
    }

    public Formation getFormation() {
        return formation;
    }
    
    public Formateur getFormateur() {
        return formateur;
    }
    
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    
    public LocalDate getDateFin() {
        return dateFin;
    }
    
    public int getNbPlacesMax() {
        return nbPlacesMax;
    }
    
    public List<Inscription> getInscriptions() {
        return new ArrayList<>(inscriptions);
    }
    
    public Queue<Inscription> getListeAttente() {
        return new LinkedList<>(listeAttente);
    }

    public SessionState getEtat() {
        return etat;
    }
    
    public int getNbInscrits() {
        return inscriptions.size();
    }

    @Override
    public String toString() {
        return "Session{" +
                "formation=" + formation.getTitre() +
                ", formateur=" + formateur.getNom() +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbPlacesMax=" + nbPlacesMax +
                ", nbInscriptions=" + inscriptions.size() +
                ", etat=" + etat.getLabel() +
                '}';
    }
}
