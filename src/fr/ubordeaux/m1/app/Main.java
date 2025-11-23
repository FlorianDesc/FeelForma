package fr.ubordeaux.m1.app;

import java.time.LocalDate;

import fr.ubordeaux.m1.controller.FormationController;
import fr.ubordeaux.m1.controller.SessionController;
import fr.ubordeaux.m1.model.Formateur;
import fr.ubordeaux.m1.model.Formation;
import fr.ubordeaux.m1.model.Session;
import fr.ubordeaux.m1.view.AccueilViewImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Point d'entrée de l'application JavaFX.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        AccueilViewImpl accueilView = new AccueilViewImpl();

        // --- TEST FormationController ---
        FormationController formationController = new FormationController();

        // Créer une formation et l'ajouter via le contrôleur
        Formation formation = new Formation("Java Avancé", 40, "Informatique");
        formationController.ajouterFormation(formation);

        // Modifier la formation
        formationController.modifierFormation(formation, "Java Débutant", 30, "Informatique");
        System.out.println("Après modification : " + formation);

        // Créer une session et l'ajouter via le contrôleur
        Formateur formateur = new Formateur("Dupont", "Jean");
        Session session = new Session(formation, formateur,
                                      LocalDate.now(),
                                      LocalDate.now().plusDays(5),
                                      20);
        formationController.ajouterSession(formation, session);
        System.out.println("Après ajout session : " + formation.getSessions().size());

        // Supprimer la session via le contrôleur
        formationController.supprimerSession(formation, session);
        System.out.println("Après suppression session : " + formation.getSessions().size());

        // Supprimer la formation via le contrôleur
        formationController.supprimerFormation(formation);
        System.out.println("Formations restantes : " + formationController.getFormations().size());

        // create counter model + controller and bind to the view
        fr.ubordeaux.m1.model.Counter counter = new fr.ubordeaux.m1.model.Counter(0);
        new fr.ubordeaux.m1.controller.CounterController(counter, accueilView);

        Scene scene = new Scene(accueilView.getRoot(), 600, 400);
            primaryStage.setTitle("Application de gestion d’un centre de formation");
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
