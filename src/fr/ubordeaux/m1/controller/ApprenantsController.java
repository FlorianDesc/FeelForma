package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Notification;
import fr.ubordeaux.m1.model.listeners.NotificationListener;
import fr.ubordeaux.m1.view.ApprenantsViewImpl;

/**
 * Contrôleur pour la vue des apprenants et de leurs notifications
 */
public class ApprenantsController implements NotificationListener {

    private final ApprenantsViewImpl view;

    public ApprenantsController(ApprenantsViewImpl view) {
        this.view = view;
    }

    @Override
    public void notificationAjoutee(Apprenant apprenant, Notification notification) {
        // Rafraîchir la vue si c'est l'apprenant actuellement sélectionné
        view.rafraichir();
    }

    public void setView(ApprenantsViewImpl view) {
        // Méthode pour la cohérence avec les autres contrôleurs
    }
}
