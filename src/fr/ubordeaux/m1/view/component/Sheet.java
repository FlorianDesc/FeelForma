package fr.ubordeaux.m1.view.component;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Composant Sheet inspiré de shadcnUI qui slide depuis le côté (généralement la droite).
 */
public class Sheet extends StackPane {
    private final VBox content;
    private final Pane overlay;
    private final VBox sheetPanel;
    private boolean isOpen = false;

    public Sheet() {
        // Overlay sombre en arrière-plan
        overlay = new Pane();
        overlay.setBackground(new Background(new BackgroundFill(
            Color.rgb(0, 0, 0, 0.5), null, null)));
        overlay.setVisible(false);
        overlay.setOnMouseClicked(e -> close());

        // Panel du sheet qui slide depuis la droite
        sheetPanel = new VBox();
        sheetPanel.setPrefWidth(400);
        sheetPanel.setMaxWidth(400);
        sheetPanel.setPadding(new Insets(24));
        sheetPanel.getStyleClass().add("sheet-panel");
        sheetPanel.setTranslateX(400); // Initialement hors écran à droite
        sheetPanel.setBackground(new Background(new BackgroundFill(
            Color.WHITE, null, null)));

        // Contenu du sheet
        content = new VBox(16);
        sheetPanel.getChildren().add(content);

        // Structure principale
        getChildren().addAll(overlay, sheetPanel);
        StackPane.setAlignment(sheetPanel, Pos.CENTER_RIGHT);
        setVisible(false);
    }

    /**
     * Définit le contenu du sheet.
     */
    public void setContent(Node node) {
        content.getChildren().setAll(node);
    }

    /**
     * Ajoute un élément au contenu du sheet.
     */
    public void addContent(Node node) {
        content.getChildren().add(node);
    }

    /**
     * Ouvre le sheet avec une animation.
     */
    public void open() {
        if (isOpen) return;
        isOpen = true;
        
        setVisible(true);
        overlay.setVisible(true);
        overlay.setOpacity(0);
        sheetPanel.setTranslateX(400);

        // Animation de l'overlay (fade in)
        javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(Duration.millis(200), overlay);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Animation du sheet (slide depuis la droite)
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), sheetPanel);
        slideIn.setFromX(400);
        slideIn.setToX(0);
        slideIn.play();
    }

    /**
     * Ferme le sheet avec une animation.
     */
    public void close() {
        if (!isOpen) return;
        isOpen = false;

        // Animation de l'overlay (fade out)
        javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(Duration.millis(200), overlay);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> overlay.setVisible(false));
        fadeOut.play();

        // Animation du sheet (slide vers la droite)
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), sheetPanel);
        slideOut.setFromX(0);
        slideOut.setToX(400);
        slideOut.setOnFinished(e -> setVisible(false));
        slideOut.play();
    }

    /**
     * Retourne true si le sheet est ouvert.
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Retourne le conteneur du contenu pour permettre l'ajout d'éléments.
     */
    public VBox getContentContainer() {
        return content;
    }
}

