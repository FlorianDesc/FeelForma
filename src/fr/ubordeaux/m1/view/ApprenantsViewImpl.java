package fr.ubordeaux.m1.view;

import java.time.format.DateTimeFormatter;
import java.util.List;

import fr.ubordeaux.m1.controller.ApprenantsController;
import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Formateur;
import fr.ubordeaux.m1.model.entities.Notification;
import fr.ubordeaux.m1.model.entities.Session;
import fr.ubordeaux.m1.model.services.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ApprenantsViewImpl implements ApprenantsView {

    private static class PersonneWrapper {
        private final Object personne;
        private final String type;
        
        public PersonneWrapper(Object personne, String type) {
            this.personne = personne;
            this.type = type;
        }
        
        public Object getPersonne() { return personne; }
        public String getType() { return type; }
        
        @Override
        public String toString() {
            if (personne instanceof Apprenant) {
                Apprenant a = (Apprenant) personne;
                return a.getPrenom() + " " + a.getNom() + " (Apprenant)";
            } else if (personne instanceof Formateur) {
                Formateur f = (Formateur) personne;
                return f.getPrenom() + " " + f.getNom() + " (Formateur)";
            }
            return "";
        }
    }

    private final StackPane root;
    private final VBox contentPane;
    private final ComboBox<PersonneWrapper> comboPersonnes;
    private final VBox notificationsContainer;
    private final VBox historiqueContainer;
    private final Label labelAucuneNotification;
    private final Label labelAucuneFormation;
    
    private final ApprenantsController controller;
    private Object personneSelectionnee;

    public ApprenantsViewImpl() {
        this.controller = new ApprenantsController(this);
        
        root = new StackPane();
        contentPane = new VBox(20);
        contentPane.setPadding(new Insets(20));
        contentPane.setAlignment(Pos.TOP_LEFT);

        Label titre = new Label("Notifications / Formations");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox selecteurBox = new HBox(15);
        selecteurBox.setAlignment(Pos.CENTER_LEFT);
        
        Label labelPersonne = new Label("Sélectionner une personne :");
        labelPersonne.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        comboPersonnes = new ComboBox<>();
        comboPersonnes.setPrefWidth(350);
        comboPersonnes.setPromptText("Choisir une personne...");
        comboPersonnes.setOnAction(e -> {
            PersonneWrapper wrapper = comboPersonnes.getValue();
            if (wrapper != null) {
                personneSelectionnee = wrapper.getPersonne();
                afficherNotifications();
                afficherHistorique();
            }
        });
        
        selecteurBox.getChildren().addAll(labelPersonne, comboPersonnes);

        HBox contenuPrincipal = new HBox(20);
        HBox.setHgrow(contenuPrincipal, Priority.ALWAYS);
        
        VBox notificationsSection = new VBox(15);
        HBox.setHgrow(notificationsSection, Priority.ALWAYS);
        notificationsSection.setPrefWidth(400);
        
        HBox headerNotifications = new HBox(10);
        headerNotifications.setAlignment(Pos.CENTER_LEFT);
        headerNotifications.setPadding(new Insets(10, 0, 10, 0));
        
        Label labelTitreNotifs = new Label("Notifications");
        labelTitreNotifs.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        headerNotifications.getChildren().add(labelTitreNotifs);

        notificationsContainer = new VBox(10);
        notificationsContainer.setPadding(new Insets(10));
        
        ScrollPane scrollPane = new ScrollPane(notificationsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefHeight(500);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        labelAucuneNotification = new Label("Aucune notification");
        labelAucuneNotification.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6; -fx-padding: 20px;");
        labelAucuneNotification.setVisible(false);

        notificationsSection.getChildren().addAll(headerNotifications, scrollPane, labelAucuneNotification);
        
        VBox historiqueSection = new VBox(15);
        HBox.setHgrow(historiqueSection, Priority.ALWAYS);
        historiqueSection.setPrefWidth(400);
        
        HBox headerHistorique = new HBox(10);
        headerHistorique.setAlignment(Pos.CENTER_LEFT);
        headerHistorique.setPadding(new Insets(10, 0, 10, 0));
        
        Label labelTitreHistorique = new Label("Historique des formations");
        labelTitreHistorique.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        headerHistorique.getChildren().add(labelTitreHistorique);
        
        historiqueContainer = new VBox(10);
        historiqueContainer.setPadding(new Insets(10));
        
        ScrollPane scrollPaneHistorique = new ScrollPane(historiqueContainer);
        scrollPaneHistorique.setFitToWidth(true);
        scrollPaneHistorique.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPaneHistorique.setPrefHeight(500);
        VBox.setVgrow(scrollPaneHistorique, Priority.ALWAYS);
        
        labelAucuneFormation = new Label("Aucune formation suivie");
        labelAucuneFormation.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6; -fx-padding: 20px;");
        labelAucuneFormation.setVisible(false);
        
        historiqueSection.getChildren().addAll(headerHistorique, scrollPaneHistorique, labelAucuneFormation);
        
        contenuPrincipal.getChildren().addAll(notificationsSection, historiqueSection);

        contentPane.getChildren().addAll(titre, selecteurBox, contenuPrincipal);
        root.getChildren().add(contentPane);

        chargerPersonnes();
    }

    private void chargerPersonnes() {
        DataService dataService = DataService.getInstance();
        List<PersonneWrapper> personnes = new java.util.ArrayList<>();
        
        for (Apprenant a : dataService.getApprenants()) {
            personnes.add(new PersonneWrapper(a, "Apprenant"));
        }
        
        for (Formateur f : dataService.getFormateurs()) {
            personnes.add(new PersonneWrapper(f, "Formateur"));
        }
        
        comboPersonnes.getItems().setAll(personnes);
    }

    private void afficherNotifications() {
        notificationsContainer.getChildren().clear();
        
        if (personneSelectionnee == null) {
            labelAucuneNotification.setVisible(true);
            return;
        }

        List<Notification> notifications;
        if (personneSelectionnee instanceof Apprenant) {
            notifications = ((Apprenant) personneSelectionnee).getNotifications();
        } else if (personneSelectionnee instanceof Formateur) {
            notifications = ((Formateur) personneSelectionnee).getNotifications();
        } else {
            labelAucuneNotification.setVisible(true);
            return;
        }
        
        if (notifications.isEmpty()) {
            labelAucuneNotification.setVisible(true);
            return;
        }

        labelAucuneNotification.setVisible(false);

        // Afficher les notifications dans l'ordre inverse (plus récentes en premier)
        for (int i = notifications.size() - 1; i >= 0; i--) {
            Notification notif = notifications.get(i);
            VBox notifBox = creerCarteNotification(notif);
            notificationsContainer.getChildren().add(notifBox);
        }
    }

    private void afficherHistorique() {
        historiqueContainer.getChildren().clear();
        
        if (personneSelectionnee == null || !(personneSelectionnee instanceof Apprenant)) {
            labelAucuneFormation.setVisible(true);
            return;
        }

        List<Session> sessions = ((Apprenant) personneSelectionnee).getSessionsSuivies();
        
        if (sessions.isEmpty()) {
            labelAucuneFormation.setVisible(true);
            return;
        }

        labelAucuneFormation.setVisible(false);

        // Afficher les sessions dans l'ordre inverse (plus récentes en premier)
        for (int i = sessions.size() - 1; i >= 0; i--) {
            Session session = sessions.get(i);
            VBox sessionBox = creerCarteSession(session);
            historiqueContainer.getChildren().add(sessionBox);
        }
    }
    
    private VBox creerCarteSession(Session session) {
        VBox carte = new VBox(8);
        carte.setPadding(new Insets(15));
        carte.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-border-color: #3498db;" +
            "-fx-border-width: 0 0 0 4;" +
            "-fx-background-radius: 5;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);"
        );

        // Titre de la formation
        Label labelFormation = new Label(session.getFormation().getTitre());
        labelFormation.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        // Dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Label labelDates = new Label("Du " + session.getDateDebut().format(formatter) + 
                                     " au " + session.getDateFin().format(formatter));
        labelDates.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        
        // Formateur
        Formateur formateur = session.getFormateur();
        Label labelFormateur = new Label("Fait par : " + formateur.getPrenom() + " " + formateur.getNom());
        labelFormateur.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        carte.getChildren().addAll(labelFormation, labelDates, labelFormateur);

        return carte;
    }

    private VBox creerCarteNotification(Notification notification) {
        VBox carte = new VBox(8);
        carte.setPadding(new Insets(15));
        carte.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-border-color: " + getCouleurType(notification.getType()) + ";" +
            "-fx-border-width: 0 0 0 4;" +
            "-fx-background-radius: 5;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);"
        );

        // En-tête avec type et date
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label labelType = new Label(getIconeType(notification.getType()) + " " + notification.getType().getLabel());
        labelType.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: " + getCouleurType(notification.getType()) + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Label labelDate = new Label(notification.getDateNotification().format(formatter));
        labelDate.setStyle("-fx-font-size: 11px; -fx-text-fill: #7f8c8d;");

        header.getChildren().addAll(labelType, spacer, labelDate);

        // Message
        Label labelMessage = new Label(notification.getMessage());
        labelMessage.setWrapText(true);
        labelMessage.setStyle("-fx-font-size: 13px; -fx-text-fill: #2c3e50;");

        carte.getChildren().addAll(header, labelMessage);

        return carte;
    }

    private String getCouleurType(Notification.TypeNotification type) {
        return switch (type) {
            case INSCRIPTION_CONFIRMEE -> "#27ae60";
            case INSCRIPTION_EN_ATTENTE -> "#f39c12";
            case INSCRIPTION_ANNULEE -> "#e74c3c";
            case SESSION_COMPLETE -> "#e67e22";
            case PLACE_LIBEREE -> "#3498db";
        };
    }

    private String getIconeType(Notification.TypeNotification type) {
        return switch (type) {
            case INSCRIPTION_CONFIRMEE -> "✓";
            case INSCRIPTION_EN_ATTENTE -> "⏳";
            case INSCRIPTION_ANNULEE -> "✗";
            case SESSION_COMPLETE -> "🚫";
            case PLACE_LIBEREE -> "🎉";
        };
    }

    @Override
    public Pane getRoot() {
        return root;
    }

    @Override
    public void rafraichir() {
        afficherNotifications();
    }

    public void setApprenantSelectionne(Apprenant apprenant) {
        this.personneSelectionnee = apprenant;
        for (PersonneWrapper wrapper : comboPersonnes.getItems()) {
            if (wrapper.getPersonne().equals(apprenant)) {
                comboPersonnes.setValue(wrapper);
                break;
            }
        }
        afficherNotifications();
        afficherHistorique();
    }
}
