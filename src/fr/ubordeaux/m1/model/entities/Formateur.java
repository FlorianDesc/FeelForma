package fr.ubordeaux.m1.model.entities;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.model.listeners.FormateurListener;
import fr.ubordeaux.m1.model.listeners.NotificationListener;

public class Formateur {
    private String nom;
    private String prenom;
    private List<String> specialites;
    private List<Notification> notifications = new ArrayList<>();

    private final List<FormateurListener> listeners = new ArrayList<>();
    private final List<NotificationListener> notificationListeners = new ArrayList<>();

    public Formateur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.specialites = new ArrayList<>();
    }

    public void addListener(FormateurListener listener) {
        listeners.add(listener);
    }

    public void removeListener(FormateurListener listener) {
        listeners.remove(listener);
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public List<String> getSpecialites() {
        return new ArrayList<>(specialites);
    }

    public void ajouterSpecialite(String specialite) {
        if (!specialites.contains(specialite)) {
            specialites.add(specialite);
        }
    }

    public void retirerSpecialite(String specialite) {
        specialites.remove(specialite);
    }

    public void ajouterNotification(Notification notification) {
        notifications.add(notification);
        for (NotificationListener listener : notificationListeners) {
            listener.notificationAjoutee(null, notification);
        }
    }

    public List<Notification> getNotifications() {
        return new ArrayList<>(notifications);
    }

    public void addNotificationListener(NotificationListener listener) {
        notificationListeners.add(listener);
    }

    public void removeNotificationListener(NotificationListener listener) {
        notificationListeners.remove(listener);
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}
