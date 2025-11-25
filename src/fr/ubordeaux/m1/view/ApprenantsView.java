package fr.ubordeaux.m1.view;

import javafx.scene.layout.Pane;

/**
 * Interface pour la vue des apprenants
 */
public interface ApprenantsView {
    /**
     * Retourne le panneau principal de la vue
     */
    Pane getRoot();
    
    /**
     * Rafraîchit l'affichage de la vue
     */
    void rafraichir();
}
