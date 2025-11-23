package fr.ubordeaux.m1.view;

import fr.ubordeaux.m1.controller.ApprenantController;
import fr.ubordeaux.m1.model.Apprenant;
import fr.ubordeaux.m1.view.component.CustomButton;
import fr.ubordeaux.m1.view.component.CustomButton.Size;
import fr.ubordeaux.m1.view.component.CustomButton.Variant;
import fr.ubordeaux.m1.view.component.Sheet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ApprenantViewImpl implements ApprenantView {

    private final StackPane root;
    private final VBox contentPane;
    private final TableView<Apprenant> tableView;
    private final ObservableList<Apprenant> apprenants;
    private final ApprenantController controller;

    // ⚠️ Sheet fournie depuis AppLayout (globale)
    private final Sheet sheet;

    private final TextField fieldNom;
    private final TextField fieldPrenom;
    private final TextField fieldEmail;
    private final TextField fieldTelephone;

    // ⚠️ NOUVEAU : on récupère la Sheet depuis AppLayout
    public ApprenantViewImpl(Sheet sheetGlobal) {

        this.sheet = sheetGlobal; // utiliser la sheet globale

        root = new StackPane();

        // Contenu principal
        contentPane = new VBox(20);
        contentPane.setPadding(new Insets(20));

        // Tableau
        tableView = new TableView<>();
        apprenants = FXCollections.observableArrayList();
        tableView.setItems(apprenants);

        // Contrôleur
        controller = new ApprenantController(this);

        // Champs du formulaire
        fieldNom = new TextField();
        fieldPrenom = new TextField();
        fieldEmail = new TextField();
        fieldTelephone = new TextField();

        // On installe le formulaire dans la sheet globale
        sheet.setContent(creerFormulaireSheet());

        // Bouton Ajouter
        CustomButton btnAjouter = new CustomButton("Ajouter", Variant.PRIMARY, Size.MD);
        btnAjouter.setOnAction(e -> {
            reinitialiserFormulaire();
            sheet.open();
        });

        // Colonnes tableau
        TableColumn<Apprenant, String> colonneNom = new TableColumn<>("Nom");
        colonneNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colonneNom.setPrefWidth(150);

        TableColumn<Apprenant, String> colonnePrenom = new TableColumn<>("Prénom");
        colonnePrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colonnePrenom.setPrefWidth(150);

        TableColumn<Apprenant, String> colonneEmail = new TableColumn<>("Email");
        colonneEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colonneEmail.setPrefWidth(250);

        TableColumn<Apprenant, String> colonneTelephone = new TableColumn<>("Téléphone");
        colonneTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colonneTelephone.setPrefWidth(150);

        tableView.getColumns().addAll(colonneNom, colonnePrenom, colonneEmail, colonneTelephone);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        contentPane.getChildren().addAll(btnAjouter, tableView);

        root.getChildren().add(contentPane);
        // ❌ On n'ajoute plus la sheet ici — elle est dans AppLayout
    }

    private VBox creerFormulaireSheet() {
        VBox formulaire = new VBox(20);

        Label titre = new Label("Ajouter un apprenant");
        titre.getStyleClass().add("sheet-title");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox champs = new VBox(16);
        champs.getChildren().addAll(
                creerChampFormulaire("Nom", fieldNom),
                creerChampFormulaire("Prénom", fieldPrenom),
                creerChampFormulaire("Email", fieldEmail),
                creerChampFormulaire("Téléphone", fieldTelephone)
        );

        HBox boutons = new HBox(12);
        boutons.setPadding(new Insets(16, 0, 0, 0));

        CustomButton btnAnnuler = new CustomButton("Annuler", Variant.SECONDARY, Size.MD);
        btnAnnuler.setOnAction(e -> sheet.close());

        CustomButton btnValider = new CustomButton("Ajouter", Variant.PRIMARY, Size.MD);
        btnValider.setOnAction(e -> {
            if (validerFormulaire()) {
                Apprenant nouvelApprenant = new Apprenant(
                    fieldNom.getText().trim(),
                    fieldPrenom.getText().trim(),
                    "",
                    fieldTelephone.getText().trim(),
                    fieldEmail.getText().trim()
                );
                controller.ajouterApprenant(nouvelApprenant);
                synchroniserAvecModele();
                sheet.close();
            }
        });

        boutons.getChildren().addAll(btnAnnuler, btnValider);

        formulaire.getChildren().addAll(titre, champs, boutons);
        return formulaire;
    }

    private VBox creerChampFormulaire(String label, TextField field) {
        VBox container = new VBox(8);
        Label labelControl = new Label(label);
        field.setPrefHeight(36);
        field.setPrefWidth(350);
        container.getChildren().addAll(labelControl, field);
        return container;
    }

    private void reinitialiserFormulaire() {
        fieldNom.clear();
        fieldPrenom.clear();
        fieldEmail.clear();
        fieldTelephone.clear();
    }

    private boolean validerFormulaire() {
        return !fieldNom.getText().trim().isEmpty()
            && !fieldPrenom.getText().trim().isEmpty()
            && !fieldEmail.getText().trim().isEmpty()
            && !fieldTelephone.getText().trim().isEmpty();
    }

    private void synchroniserAvecModele() {
        apprenants.clear();
        apprenants.addAll(controller.getApprenants());
    }

    @Override
    public Pane getRoot() {
        return root;
    }
}
