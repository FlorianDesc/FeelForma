package fr.ubordeaux.m1.controller;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.model.Formation;
import fr.ubordeaux.m1.view.FormationView;

public class FormationController {

    private final List<Formation> model;
    private FormationView view;

    public FormationController(FormationView view) {
        this.model = new ArrayList<>();
        this.view = view;
    }

    public void setView(FormationView view) {
        this.view = view;
    }

    // === AJOUT ===
    public void ajouterFormation(Formation formation) {
        model.add(formation);
        view.updateTable(model);
    }

    // === SUPPRESSION ===
    public void supprimerFormation(Formation formation) {
        model.remove(formation);
        view.updateTable(model);
    }

    // === MODIFICATION ===
    public void modifierFormation(Formation ancienne, Formation nouvelle) {
        int index = model.indexOf(ancienne);
        if (index >= 0) {
            model.set(index, nouvelle);
            view.updateTable(model);
        }
    }

    public List<Formation> getFormations() {
        return new ArrayList<>(model);
    }
}
