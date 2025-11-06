package fr.ubordeaux.m1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private EtatSession etat;

    /**
     * Énumération des différents états possibles d'une session
     */
    public enum EtatSession {
        OUVERTE,
        COMPLETE,
        TERMINEE,
        ANNULEE
    }

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
        this.etat = EtatSession.OUVERTE;
    }

    // Getters et Setters
    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbPlacesMax() {
        return nbPlacesMax;
    }

    public void setNbPlacesMax(int nbPlacesMax) {
        this.nbPlacesMax = nbPlacesMax;
    }

    public List<Apprenant> getInscrits() {
        return inscrits;
    }

    public EtatSession getEtat() {
        return etat;
    }

    public void setEtat(EtatSession etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Session{" +
                "formation=" + formation.getTitre() +
                ", formateur=" + formateur.getNom() +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", places=" + inscrits.size() + "/" + nbPlacesMax +
                ", etat=" + etat +
                '}';
    }
}
