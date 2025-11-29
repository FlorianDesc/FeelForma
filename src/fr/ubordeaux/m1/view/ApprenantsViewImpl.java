package fr.ubordeaux.m1.view;

import java.time.format.DateTimeFormatter;
import java.util.List;

import fr.ubordeaux.m1.controller.ApprenantsController;
import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Notification;
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

/**
 * Vue permettant de sélectionner un apprenant et voir ses notifications
 */
public class ApprenantsViewImpl implements ApprenantsView {

    private final StackPane root;
    private final VBox contentPane;
    private final ComboBox<Apprenant> comboApprenants;
    private final VBox notificationsContainer;
    private final Label labelAucuneNotification;
    
    private final ApprenantsController controller;
    private Apprenant apprenantSelectionne;

    public ApprenantsViewImpl() {
        this.controller = new ApprenantsController(this);
        
        root = new StackPane();
        contentPane = new VBox(20);
        contentPane.setPadding(new Insets(20));
        contentPane.setAlignment(Pos.TOP_LEFT);

        // En-tête
        Label titre = new Label("Notifications des Apprenants");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Sélecteur d'apprenant
        HBox selecteurBox = new HBox(15);
        selecteurBox.setAlignment(Pos.CENTER_LEFT);
        
        Label labelApprenant = new Label("Sélectionner un apprenant :");
        labelApprenant.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        comboApprenants = new ComboBox<>();
        comboApprenants.setPrefWidth(300);
        comboApprenants.setPromptText("Choisir un apprenant...");
        comboApprenants.setOnAction(e -> {
            apprenantSelectionne = comboApprenants.getValue();
            afficherNotifications();
        });
        
        selecteurBox.getChildren().addAll(labelApprenant, comboApprenants);

        // Zone de notifications avec titre et séparateur
        VBox notificationsSection = new VBox(15);
        
        HBox headerNotifications = new HBox(10);
        headerNotifications.setAlignment(Pos.CENTER_LEFT);
        headerNotifications.setPadding(new Insets(10, 0, 10, 0));
        
        Label labelTitreNotifs = new Label("Notifications");
        labelTitreNotifs.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        headerNotifications.getChildren().add(labelTitreNotifs);

        // Conteneur scrollable pour les notifications
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

        // Assemblage
        contentPane.getChildren().addAll(titre, selecteurBox, notificationsSection);
        root.getChildren().add(contentPane);

        chargerApprenants();
    }

    private void chargerApprenants() {
        DataService dataService = DataService.getInstance();
        List<Apprenant> apprenants = dataService.getApprenants();
        comboApprenants.getItems().setAll(apprenants);
    }

    private void afficherNotifications() {
        notificationsContainer.getChildren().clear();
        
        if (apprenantSelectionne == null) {
            labelAucuneNotification.setVisible(true);
            return;
        }

        List<Notification> notifications = apprenantSelectionne.getNotifications();
        
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
        this.apprenantSelectionne = apprenant;
        comboApprenants.setValue(apprenant);
        afficherNotifications();
    }
}
