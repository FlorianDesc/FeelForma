package fr.ubordeaux.m1.model.listeners;

import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Notification;

/**
 * Interface pour écouter les événements de notifications
 */
public interface NotificationListener {
    /**
     * Appelée lorsqu'une nouvelle notification est ajoutée pour un apprenant
     */
    void notificationAjoutee(Apprenant apprenant, Notification notification);
}
