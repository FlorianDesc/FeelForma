package fr.ubordeaux.m1.view;

import fr.ubordeaux.m1.view.component.Sheet;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AppLayout {
    private final StackPane root; 
    private final BorderPane main;
    private final SidebarView sidebar;
    private final Sheet formationSheet;
    private final Sheet sessionSheet;
    private final Sheet inscriptionSheet;
    private final StackPane container; // ← zone où on affiche les pages

    public AppLayout() {
        root = new StackPane();
        main = new BorderPane();
        sidebar = new SidebarView();
        formationSheet = new Sheet();
        sessionSheet = new Sheet();
        inscriptionSheet = new Sheet();
        container = new StackPane(); // ← ajouter ça
        root.getStyleClass().add("app-root");

        // Place la sidebar à gauche
        VBox left = new VBox(sidebar.getNode());
        left.getStyleClass().add("sidebar-wrapper");
        main.setLeft(left);

        // Le contenu principal (qui sera remplacé par le controller)
        main.setCenter(container);

        // On empile BorderPane + Sheets (pour les popups)
        root.getChildren().addAll(main, formationSheet, sessionSheet, inscriptionSheet);
    }

    public Node getRoot() {
        return root;
    }

    public StackPane getContainer() {
        return container;
    }

    public SidebarView getSidebar() {
        return sidebar;
    }

    public Sheet getFormationSheet() {
        return formationSheet;
    }

    public Sheet getSessionSheet() {
        return sessionSheet;
    }

    public Sheet getInscriptionSheet() {
        return inscriptionSheet;
    }
}
