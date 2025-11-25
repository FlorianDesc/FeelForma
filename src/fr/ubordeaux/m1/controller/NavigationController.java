package fr.ubordeaux.m1.controller;

import java.util.HashMap;
import java.util.Map;

import fr.ubordeaux.m1.model.entities.Formation;
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
    private final Sheet inscriptionSheet;

    // Cache des vues pour éviter de les recréer
    private final FormationViewImpl formationView;
    private final Map<Formation, SessionViewImpl> sessionViewsCache;

    public NavigationController(AppLayout layout) {
        this.sidebar = layout.getSidebar();
        this.container = layout.getContainer();
        this.sheet = layout.getSheet();
        this.inscriptionSheet = layout.getInscriptionSheet();
        this.sessionViewsCache = new HashMap<>();

        // Création unique de la vue formations avec référence à ce controller
        this.formationView = new FormationViewImpl(sheet, this);

        sidebar.setOnFormation(this::showFormationPage);

        showFormationPage(); // page par défaut
    }

    public void showFormationPage() {
        container.getChildren().setAll(formationView.getRoot());
        sidebar.setActive("formation");
    }

    public void showSessionPage(Formation formation) {
        // Récupération ou création de la vue session pour cette formation
        SessionViewImpl sessionView = sessionViewsCache.get(formation);
        if (sessionView == null) {
            sessionView = new SessionViewImpl(sheet, inscriptionSheet, formation, this);
            sessionViewsCache.put(formation, sessionView);
        }
        container.getChildren().setAll(sessionView.getRoot());
        sidebar.setActive("");
    }
}
