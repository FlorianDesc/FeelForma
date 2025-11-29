package fr.ubordeaux.m1.view;

import java.util.List;

import fr.ubordeaux.m1.controller.FormationController;
import fr.ubordeaux.m1.model.entities.Formation;
import fr.ubordeaux.m1.model.services.DataService;
import fr.ubordeaux.m1.view.component.CustomButton;
import fr.ubordeaux.m1.view.component.CustomButton.Size;
import fr.ubordeaux.m1.view.component.CustomButton.Variant;
import fr.ubordeaux.m1.view.component.Sheet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FormationViewImpl implements FormationView {

    private final StackPane root;
    private final VBox contentPane;
    private final TableView<Formation> tableView;

    private final FormationController controller;
    private final Sheet sheet;
    private final fr.ubordeaux.m1.controller.NavigationController navigationController;

    private final TextField fieldTitre;
    private final TextField fieldDuree;
    private final TextField fieldCategorie;
    
    // Labels d'erreur
    private final Label errorTitre = new Label();
    private final Label errorDuree = new Label();
    private final Label errorCategorie = new Label();

    private Formation formationEnEdition = null;

    public FormationViewImpl(Sheet sheetGlobal, fr.ubordeaux.m1.controller.NavigationController navigationController) {

        this.sheet = sheetGlobal;
        this.navigationController = navigationController;

        root = new StackPane();
        contentPane = new VBox(20);
        contentPane.setPadding(new Insets(20));

        tableView = new TableView<>();

        controller = new FormationController();
        controller.setView(this);

        fieldTitre = new TextField();
        fieldDuree = new TextField();
        fieldCategorie = new TextField();

        sheet.setContent(creerFormulaireSheet());

        CustomButton btnAjouter = new CustomButton("Ajouter", Variant.PRIMARY, Size.MD);
        btnAjouter.setOnAction(e -> {
            formationEnEdition = null;
            reinitialiserFormulaire();
            sheet.open();
        });

        // === COLONNES ===
        TableColumn<Formation, String> colTitre = new TableColumn<>("Titre");
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));

        TableColumn<Formation, Integer> colDuree = new TableColumn<>("Durée");
        colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));

        TableColumn<Formation, String> colCategorie = new TableColumn<>("Catégorie");
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        // Centrage horizontal + vertical
        centrerCellules(colTitre);
        centrerCellules(colDuree);
        centrerCellules(colCategorie);

        // Bouton Modifier
        TableColumn<Formation, Void> colModifier = new TableColumn<>("Modifier");
        colModifier.setCellFactory(column -> new TableCell<>() {
            private final CustomButton btn = new CustomButton("Modifier", Variant.SECONDARY, Size.SM);

            {
                btn.setOnAction(e -> {
                    Formation f = getTableView().getItems().get(getIndex());
                    formationEnEdition = f;

                    fieldTitre.setText(f.getTitre());
                    fieldDuree.setText(String.valueOf(f.getDuree()));
                    fieldCategorie.setText(f.getCategorie());

                    sheet.open();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
                setAlignment(Pos.CENTER);
            }
        });

        // Bouton Supprimer
        TableColumn<Formation, Void> colSupprimer = new TableColumn<>("Supprimer");
        colSupprimer.setCellFactory(column -> new TableCell<>() {
            private final CustomButton btn = new CustomButton("Supprimer", Variant.DESTRUCTIVE, Size.SM);

            {
                btn.setOnAction(e -> {
                    Formation f = getTableView().getItems().get(getIndex());
                    controller.supprimerFormation(f);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
                setAlignment(Pos.CENTER);
            }
        });

        // Bouton Voir Sessions
        TableColumn<Formation, Void> colVoirSessions = new TableColumn<>("Voir sessions");
        colVoirSessions.setCellFactory(column -> new TableCell<>() {
            private final CustomButton btn = new CustomButton("Voir sessions", Variant.PRIMARY, Size.SM);

            {
                btn.setOnAction(e -> {
                    Formation f = getTableView().getItems().get(getIndex());
                    navigationController.showSessionPage(f);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
                setAlignment(Pos.CENTER);
            }
        });

        tableView.getColumns().addAll(colTitre, colDuree, colCategorie, colModifier, colSupprimer, colVoirSessions);

        // === HAUTEUR FIXE DES LIGNES ===
        tableView.setFixedCellSize(50); // 👉 hauteur de ligne demandée
        tableView.prefHeightProperty().bind(
                tableView.fixedCellSizeProperty().multiply(10).add(30)
        );

        // === AUTO-LARGEUR COLONNES ===
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        contentPane.getChildren().addAll(btnAjouter, tableView);
        root.getChildren().add(contentPane);
        
        // Charger les formations par défaut depuis DataService
        chargerFormationsParDefaut();
    }
    
    private void chargerFormationsParDefaut() {
        DataService dataService = DataService.getInstance();
        for (Formation formation : dataService.getFormations()) {
            controller.ajouterFormation(formation);
        }
    }

    private <S, T> void centrerCellules(TableColumn<S, T> col) {
    col.setCellFactory(column -> new TableCell<S, T>() {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.toString());
            }
            setAlignment(Pos.CENTER); // centrage horizontal + vertical
        }
    });
}

    private VBox creerFormulaireSheet() {
        VBox formulaire = new VBox(20);

        Label titre = new Label("Formation");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox champs = new VBox(16);
        champs.getChildren().addAll(
            creerChampFormulaire("Titre", fieldTitre, errorTitre),
            creerChampFormulaire("Durée (heures)", fieldDuree, errorDuree),
            creerChampFormulaire("Catégorie", fieldCategorie, errorCategorie)
        );

        HBox boutons = new HBox(12);

        CustomButton btnAnnuler = new CustomButton("Annuler", Variant.SECONDARY, Size.MD);
        btnAnnuler.setOnAction(e -> sheet.close());

        CustomButton btnValider = new CustomButton("Valider", Variant.PRIMARY, Size.MD);
        btnValider.setOnAction(e -> {
            if (validerFormulaire()) {

                Formation nouvelle = new Formation(
                        fieldTitre.getText(),
                        Integer.parseInt(fieldDuree.getText()),
                        fieldCategorie.getText()
                );

                if (formationEnEdition == null) {
                    controller.ajouterFormation(nouvelle);
                } else {
                    controller.modifierFormation(formationEnEdition, nouvelle.getTitre(), nouvelle.getDuree(), nouvelle.getCategorie());
                }

                sheet.close();
            }
        });

        boutons.getChildren().addAll(btnAnnuler, btnValider);

        formulaire.getChildren().addAll(titre, champs, boutons);
        return formulaire;
    }

    private VBox creerChampFormulaire(String label, TextField field, Label errorLabel) {
        VBox container = new VBox(4);
        Label labelControl = new Label(label);
        field.setPrefHeight(36);
        field.setPrefWidth(350);
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px;");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        container.getChildren().addAll(labelControl, field, errorLabel);
        return container;
    }

    private void reinitialiserFormulaire() {
        fieldTitre.clear();
        fieldDuree.clear();
        fieldCategorie.clear();
    }

    private boolean validerFormulaire() {
        // Réinitialiser les styles et erreurs
        fieldTitre.setStyle("");
        fieldDuree.setStyle("");
        fieldCategorie.setStyle("");
        
        errorTitre.setVisible(false);
        errorTitre.setManaged(false);
        errorDuree.setVisible(false);
        errorDuree.setManaged(false);
        errorCategorie.setVisible(false);
        errorCategorie.setManaged(false);
        
        boolean valide = true;
        
        // Validation du titre
        if (fieldTitre.getText().trim().isEmpty()) {
            fieldTitre.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorTitre.setText("Le titre ne peut pas être vide");
            errorTitre.setVisible(true);
            errorTitre.setManaged(true);
            valide = false;
        }
        
        // Validation de la durée (doit être un nombre positif)
        if (fieldDuree.getText().trim().isEmpty()) {
            fieldDuree.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorDuree.setText("La durée ne peut pas être vide");
            errorDuree.setVisible(true);
            errorDuree.setManaged(true);
            valide = false;
        } else {
            try {
                int duree = Integer.parseInt(fieldDuree.getText().trim());
                if (duree <= 0) {
                    fieldDuree.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    errorDuree.setText("La durée doit être un nombre positif");
                    errorDuree.setVisible(true);
                    errorDuree.setManaged(true);
                    valide = false;
                }
            } catch (NumberFormatException e) {
                fieldDuree.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                errorDuree.setText("La durée doit être un nombre entier");
                errorDuree.setVisible(true);
                errorDuree.setManaged(true);
                valide = false;
            }
        }
        
        // Validation de la catégorie
        if (fieldCategorie.getText().trim().isEmpty()) {
            fieldCategorie.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorCategorie.setText("La catégorie ne peut pas être vide");
            errorCategorie.setVisible(true);
            errorCategorie.setManaged(true);
            valide = false;
        }
        
        return valide;
    }

    @Override
    public void updateTable(List<Formation> formations) {
        tableView.getItems().setAll(formations);
        tableView.refresh();
    }

    public void ajouterFormationAuTableau(Formation formation) {
        tableView.getItems().add(formation);
    }

    public void supprimerFormationDuTableau(Formation formation) {
        tableView.getItems().remove(formation);
    }

    @Override
    public Pane getRoot() {
        return root;
    }
}
