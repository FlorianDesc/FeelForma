package fr.ubordeaux.m1.view;

import fr.ubordeaux.m1.view.component.Sheet;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AppLayout {
    private final StackPane root; // IMPORTANT
    private final BorderPane main;
    private final SidebarView sidebar;
    private final Sheet sheet;

    public AppLayout() {
        root = new StackPane();
        main = new BorderPane();
        sidebar = new SidebarView();
        sheet = new Sheet(); // la sheet globale
        root.getStyleClass().add("app-root");

        // sidebar à gauche
        VBox left = new VBox(sidebar.getNode());
        left.getStyleClass().add("sidebar-wrapper");
        main.setLeft(left);

        // On empile BorderPane (contenu) + Sheet
        root.getChildren().addAll(main, sheet);
    }

    public Node getRoot() {
        return root;
    }

    public void setContent(Node content) {
        main.setCenter(content);
    }

    public Sheet getSheet() {
        return sheet; // accès depuis les vues
    }
}
