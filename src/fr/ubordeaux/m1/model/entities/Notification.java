package fr.ubordeaux.m1.model.entities;

import java.time.LocalDateTime;

public class Notification {
    private final String message;
    private final LocalDateTime dateNotification;
    private final TypeNotification type;
    private boolean lue;

    public enum TypeNotification {
        INSCRIPTION_CONFIRMEE("Inscription confirmée"),
        INSCRIPTION_EN_ATTENTE("Mise en liste d'attente"),
        INSCRIPTION_ANNULEE("Inscription annulée"),
        SESSION_COMPLETE("Session complète"),
        PLACE_LIBEREE("Place libérée");

        private final String label;

        TypeNotification(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public Notification(String message, TypeNotification type) {
        this.message = message;
        this.type = type;
        this.dateNotification = LocalDateTime.now();
        this.lue = false;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateNotification() {
        return dateNotification;
    }

    public TypeNotification getType() {
        return type;
    }

    public boolean isLue() {
        return lue;
    }

    public void marquerCommeLue() {
        this.lue = true;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s", 
            type.getLabel(), 
            dateNotification.toLocalDate(), 
            message);
    }
}
