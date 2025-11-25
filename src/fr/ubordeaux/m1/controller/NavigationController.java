package fr.ubordeaux.m1.controller;

import java.util.HashMap;
import java.util.Map;

import fr.ubordeaux.m1.model.entities.Formation;
import fr.ubordeaux.m1.view.AppLayout;
import fr.ubordeaux.m1.view.ApprenantsViewImpl;
import fr.ubordeaux.m1.view.FormationViewImpl;
import fr.ubordeaux.m1.view.SessionViewImpl;
import fr.ubordeaux.m1.view.SidebarView;
import fr.ubordeaux.m1.view.component.Sheet;
import javafx.scene.layout.StackPane;

public class NavigationController {

    private final SidebarView sidebar;
    private final StackPane container;
    private final Sheet formationSheet;
    private final Sheet sessionSheet;
    private final Sheet inscriptionSheet;

    // Cache des vues pour éviter de les recréer
    private final FormationViewImpl formationView;
    private final ApprenantsViewImpl apprenantsView;
    private final Map<Formation, SessionViewImpl> sessionViewsCache;

    public NavigationController(AppLayout layout) {
        this.sidebar = layout.getSidebar();
        this.container = layout.getContainer();
        this.formationSheet = layout.getFormationSheet();
        this.sessionSheet = layout.getSessionSheet();
        this.inscriptionSheet = layout.getInscriptionSheet();
        this.sessionViewsCache = new HashMap<>();

        // Création unique des vues avec référence à ce controller
        this.formationView = new FormationViewImpl(formationSheet, this);
        this.apprenantsView = new ApprenantsViewImpl();

        sidebar.setOnFormation(this::showFormationPage);
        sidebar.setOnApprenants(this::showApprenantsPage);

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
            sessionView = new SessionViewImpl(sessionSheet, inscriptionSheet, formation, this);
            sessionViewsCache.put(formation, sessionView);
        }
        container.getChildren().setAll(sessionView.getRoot());
        sidebar.setActive("");
    }

    public void showApprenantsPage() {
        container.getChildren().setAll(apprenantsView.getRoot());
        sidebar.setActive("apprenants");
    }
}
