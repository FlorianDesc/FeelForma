package fr.ubordeaux.m1.view;

import java.util.List;

import fr.ubordeaux.m1.model.entities.Formation;
import javafx.scene.layout.Pane;

/**
 * Interface représentant la vue des formations.
 * La vue ne gère pas le modèle, elle reçoit uniquement les données
 * du contrôleur et les affiche.
 */
public interface FormationView {
    
    /** Retourne la racine graphique de la vue. */
    Pane getRoot();

    /** Permet au contrôleur de mettre à jour l'affichage du tableau. */
    void updateTable(List<Formation> formations);
}
