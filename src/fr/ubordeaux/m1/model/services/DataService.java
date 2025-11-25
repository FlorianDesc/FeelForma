package fr.ubordeaux.m1.model.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Formateur;
import fr.ubordeaux.m1.model.entities.Formation;
import fr.ubordeaux.m1.model.entities.Session;

/**
 * Service singleton pour gérer les données partagées de l'application
 */
public class DataService {
    
    private static DataService instance;
    
    private final List<Apprenant> apprenants;
    private final List<Formateur> formateurs;
    private final List<Formation> formations;
    
    private DataService() {
        this.apprenants = new ArrayList<>();
        this.formateurs = new ArrayList<>();
        this.formations = new ArrayList<>();
        initialiserDonneesParDefaut();
    }
    
    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }
    
    private void initialiserDonneesParDefaut() {
        // Création d'apprenants par défaut
        apprenants.add(new Apprenant("Dupont", "Jean", "123 Rue de la Paix, Paris", "0601020304", "jean.dupont@email.fr"));
        apprenants.add(new Apprenant("Martin", "Sophie", "456 Avenue des Champs, Lyon", "0612345678", "sophie.martin@email.fr"));
        apprenants.add(new Apprenant("Bernard", "Pierre", "789 Boulevard Voltaire, Marseille", "0623456789", "pierre.bernard@email.fr"));
        apprenants.add(new Apprenant("Dubois", "Marie", "321 Rue Victor Hugo, Toulouse", "0634567890", "marie.dubois@email.fr"));
        apprenants.add(new Apprenant("Thomas", "Luc", "654 Avenue de la République, Nice", "0645678901", "luc.thomas@email.fr"));
        apprenants.add(new Apprenant("Robert", "Emma", "987 Rue Jean Jaurès, Nantes", "0656789012", "emma.robert@email.fr"));
        apprenants.add(new Apprenant("Petit", "Paul", "147 Boulevard Saint-Germain, Strasbourg", "0667890123", "paul.petit@email.fr"));
        apprenants.add(new Apprenant("Durand", "Julie", "258 Rue de Rivoli, Bordeaux", "0678901234", "julie.durand@email.fr"));
        
        // Création de formateurs par défaut avec leurs spécialités
        Formateur f1 = new Formateur("Leroy", "Michel");
        f1.ajouterSpecialite("Java");
        f1.ajouterSpecialite("Développement");
        formateurs.add(f1);
        
        Formateur f2 = new Formateur("Moreau", "Catherine");
        f2.ajouterSpecialite("Java");
        f2.ajouterSpecialite("Spring");
        formateurs.add(f2);
        
        Formateur f3 = new Formateur("Simon", "François");
        f3.ajouterSpecialite("Java");
        f3.ajouterSpecialite("Architecture");
        formateurs.add(f3);
        
        Formateur f4 = new Formateur("Laurent", "Isabelle");
        f4.ajouterSpecialite("Python");
        f4.ajouterSpecialite("Data Science");
        formateurs.add(f4);
        
        Formateur f5 = new Formateur("Lefebvre", "Antoine");
        f5.ajouterSpecialite("Python");
        f5.ajouterSpecialite("Machine Learning");
        formateurs.add(f5);
        
        Formateur f6 = new Formateur("Garcia", "Nathalie");
        f6.ajouterSpecialite("Management");
        f6.ajouterSpecialite("Leadership");
        formateurs.add(f6);
        
        Formateur f7 = new Formateur("Roux", "Alexandre");
        f7.ajouterSpecialite("Langues");
        f7.ajouterSpecialite("Anglais");
        formateurs.add(f7);
        
        Formateur f8 = new Formateur("Fournier", "Valérie");
        f8.ajouterSpecialite("DevOps");
        f8.ajouterSpecialite("Cloud");
        formateurs.add(f8);
        
        // Création de formations par défaut
        Formation formationJava = new Formation("Java Avancé", 40, "Développement");
        Formation formationPython = new Formation("Python pour Data Science", 35, "Data Science");
        
        formations.add(formationJava);
        formations.add(formationPython);
        
        // Création de sessions pour la formation Java
        Session sessionJava1 = new Session(
            formationJava,
            formateurs.get(0), // Michel Leroy
            LocalDate.of(2025, 12, 1),
            LocalDate.of(2025, 12, 15),
            3
        );
        
        Session sessionJava2 = new Session(
            formationJava,
            formateurs.get(1), // Catherine Moreau
            LocalDate.of(2026, 1, 10),
            LocalDate.of(2026, 1, 24),
            4
        );
        
        Session sessionJava3 = new Session(
            formationJava,
            formateurs.get(2), // François Simon
            LocalDate.of(2026, 2, 5),
            LocalDate.of(2026, 2, 19),
            3
        );
        
        formationJava.addSession(sessionJava1);
        formationJava.addSession(sessionJava2);
        formationJava.addSession(sessionJava3);
        
        // Inscription de quelques apprenants à la première session Java
        sessionJava1.inscrire(apprenants.get(0)); // Jean Dupont
        apprenants.get(0).ajouterSessionSuivie(sessionJava1);
        sessionJava1.inscrire(apprenants.get(1)); // Sophie Martin
        apprenants.get(1).ajouterSessionSuivie(sessionJava1);
        sessionJava1.inscrire(apprenants.get(2)); // Pierre Bernard
        apprenants.get(2).ajouterSessionSuivie(sessionJava1);
        
        // Création de sessions pour la formation Python
        Session sessionPython1 = new Session(
            formationPython,
            formateurs.get(3), // Isabelle Laurent
            LocalDate.of(2025, 12, 5),
            LocalDate.of(2025, 12, 20),
            4
        );
        
        Session sessionPython2 = new Session(
            formationPython,
            formateurs.get(4), // Antoine Lefebvre
            LocalDate.of(2026, 1, 15),
            LocalDate.of(2026, 1, 30),
            3
        );
        
        formationPython.addSession(sessionPython1);
        formationPython.addSession(sessionPython2);
        
        // Inscription de quelques apprenants à la première session Python
        sessionPython1.inscrire(apprenants.get(3)); // Marie Dubois
        apprenants.get(3).ajouterSessionSuivie(sessionPython1);
        sessionPython1.inscrire(apprenants.get(4)); // Luc Thomas
        apprenants.get(4).ajouterSessionSuivie(sessionPython1);
    }
    
    public List<Apprenant> getApprenants() {
        return new ArrayList<>(apprenants);
    }
    
    public List<Formateur> getFormateurs() {
        return new ArrayList<>(formateurs);
    }
    
    public List<Formation> getFormations() {
        return new ArrayList<>(formations);
    }
    
    public void ajouterApprenant(Apprenant apprenant) {
        apprenants.add(apprenant);
    }
    
    public void ajouterFormateur(Formateur formateur) {
        formateurs.add(formateur);
    }
    
    public void ajouterFormation(Formation formation) {
        formations.add(formation);
    }
}
