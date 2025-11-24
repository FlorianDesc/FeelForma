package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.view.AppLayout;
import fr.ubordeaux.m1.view.FormationViewImpl;
import fr.ubordeaux.m1.view.SessionViewImpl;
import fr.ubordeaux.m1.view.SidebarView;
import fr.ubordeaux.m1.view.component.Sheet;
import javafx.scene.layout.StackPane;

public class NavigationController {

    private final SidebarView sidebar;
    private final StackPane container;
    private final Sheet sheet;

    public NavigationController(AppLayout layout) {
        this.sidebar = layout.getSidebar();
        this.container = layout.getContainer();
        this.sheet = layout.getSheet();

        sidebar.setOnFormation(this::showFormationPage);
        sidebar.setOnSession(this::showSessionPage);

        showFormationPage(); // page par défaut
    }

    private void showFormationPage() {
        FormationViewImpl view = new FormationViewImpl(sheet);
        container.getChildren().setAll(view.getRoot());
        sidebar.setActive("formation");
    }

    private void showSessionPage() {
        SessionViewImpl view = new SessionViewImpl();
        container.getChildren().setAll(view.getRoot());
        sidebar.setActive("session");
    }
}
