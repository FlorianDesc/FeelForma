package fr.ubordeaux.m1.model.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.model.listeners.SessionListener;
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
    private List<Apprenant> inscrits;
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
        this.inscrits = new ArrayList<>();
        this.etat = new EtatOuverte(this);

    }

    // --- Gestion des listeners ---
    public void addListener(SessionListener listener) {
        listeners.add(listener);
    }
    public void removeListener(SessionListener listener) {
        listeners.remove(listener);
    }
    private void notifyListeners() {
        for (SessionListener listener : listeners) {
            listener.sessionUpdated(this);
        }
    }

    public void notifyInscriptionConfirmed(Apprenant apprenant) {
        for (SessionListener l : listeners) l.inscriptionSessionConfirmed(this, apprenant);
    }

    public void notifyInscriptionWaitlisted(Apprenant apprenant) {
        for (SessionListener l : listeners) l.inscriptionSessionWaitlisted(this, apprenant);
    }

    public void notifyInscriptionCancelled(Apprenant apprenant) {
        for (SessionListener l : listeners) l.inscriptionSessionCancelled(this, apprenant);
    }

    public void notifySessionFull() {
        for (SessionListener l : listeners) l.sessionFull(this);
    }

    public void notifySessionReopened() {
        for (SessionListener l : listeners) l.sessionReopened(this);
    }


    public void changeState(SessionState etat) {
        this.etat = etat;
        notifyListeners();
    }

    public void inscrire(Apprenant apprenant) {
        etat.inscrire(apprenant);
    }

    public void ajouterInscrit(Apprenant apprenant) {
        inscrits.add(apprenant);
    }

    public boolean estPleine() {
        return inscrits.size() >= nbPlacesMax;
    }
    
    // Getters et Setters
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

    public List<Apprenant> getInscrits() {
        return inscrits;
    }

    public SessionState getEtat() {
        return etat;
    }

    @Override
    public String toString() {
        return "Session{" +
                "formation=" + formation.getTitre() +
                ", formateur=" + formateur.getNom() +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", places=" + inscrits.size() + "/" + nbPlacesMax +
                ", etat=" + etat.getLabel() +
                '}';
    }
}
