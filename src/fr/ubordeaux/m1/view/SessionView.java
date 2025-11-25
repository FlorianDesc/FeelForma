package fr.ubordeaux.m1.view;

import java.util.List;

import fr.ubordeaux.m1.model.entities.Session;
import javafx.scene.layout.Pane;

/**
 * Interface représentant la vue des sessions.
 * Elle définit les comportements que toute implémentation doit fournir.
 */
public interface SessionView {
    void updateTable(List<Session> sessions);
    Pane getRoot();
}

