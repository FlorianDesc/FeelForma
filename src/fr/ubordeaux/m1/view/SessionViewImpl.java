package fr.ubordeaux.m1.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.m1.controller.NavigationController;
import fr.ubordeaux.m1.controller.SessionController;
import fr.ubordeaux.m1.model.entities.Apprenant;
import fr.ubordeaux.m1.model.entities.Formateur;
import fr.ubordeaux.m1.model.entities.Formation;
import fr.ubordeaux.m1.model.entities.Inscription;
import fr.ubordeaux.m1.model.entities.Session;
import fr.ubordeaux.m1.model.services.DataService;
import fr.ubordeaux.m1.model.states.EtatAnnulee;
import fr.ubordeaux.m1.model.states.EtatComplete;
import fr.ubordeaux.m1.model.states.EtatOuverte;
import fr.ubordeaux.m1.model.states.EtatTerminee;
import fr.ubordeaux.m1.model.states.SessionState;
import fr.ubordeaux.m1.view.component.CustomButton;
import fr.ubordeaux.m1.view.component.CustomButton.Size;
import fr.ubordeaux.m1.view.component.CustomButton.Variant;
import fr.ubordeaux.m1.view.component.Sheet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

public class SessionViewImpl implements SessionView {

    private final StackPane root;
    private final VBox contentPane;
    private final TableView<Session> tableView;
    private final Label titreFormation;

    private final SessionController controller;
    private final Sheet sheet;
    private final Sheet inscriptionSheet;
    private final NavigationController navigationController;

    private final DatePicker fieldDateDebut;
    private final DatePicker fieldDateFin;
    private final ComboBox<Formateur> fieldFormateur;
    private final TextField fieldPlacesMax;
    private final ComboBox<String> fieldEtat;
    
    private final ComboBox<Apprenant> fieldApprenant;
    
    private final Label errorDateDebut = new Label();
    private final Label errorDateFin = new Label();
    private final Label errorFormateur = new Label();
    private final Label errorPlacesMax = new Label();
    private final Label errorEtat = new Label();
    private final Label infoEtat = new Label();

    private Session sessionEnEdition = null;
    private Session sessionPourInscription = null;
    
    private final DataService dataService;
    
    private Label labelPlacesInscription;
    private TableView<Apprenant> tableInscritsInscription;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public SessionViewImpl(Sheet sheetGlobal, Sheet inscriptionSheetGlobal, Formation formation, NavigationController navigationController) {
        this.sheet = sheetGlobal;
        this.inscriptionSheet = inscriptionSheetGlobal;
        this.navigationController = navigationController;
        this.dataService = DataService.getInstance();

        root = new StackPane();
        contentPane = new VBox(20);
        contentPane.setPadding(new Insets(20));

        HBox headerBox = new HBox(20);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        CustomButton btnRetour = new CustomButton("← Retour", Variant.SECONDARY, Size.MD);
        btnRetour.setOnAction(e -> navigationController.showFormationPage());

        titreFormation = new Label("Sessions de : " + formation.getTitre());
        titreFormation.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        headerBox.getChildren().addAll(btnRetour, titreFormation);

        tableView = new TableView<>();

        controller = new SessionController(formation, this);

        fieldDateDebut = new DatePicker();
        fieldDateFin = new DatePicker();
        fieldFormateur = new ComboBox<>();
        fieldFormateur.getItems().setAll(dataService.getFormateurs());
        fieldFormateur.setCellFactory(param -> new javafx.scene.control.ListCell<Formateur>() {
            @Override
            protected void updateItem(Formateur formateur, boolean empty) {
                super.updateItem(formateur, empty);
                if (empty || formateur == null) {
                    setText(null);
                } else {
                    String texte = formateur.getPrenom() + " " + formateur.getNom();
                    List<String> specialites = formateur.getSpecialites();
                    if (!specialites.isEmpty()) {
                        texte += " (" + String.join(", ", specialites) + ")";
                    }
                    setText(texte);
                }
            }
        });
        fieldFormateur.setButtonCell(new javafx.scene.control.ListCell<Formateur>() {
            @Override
            protected void updateItem(Formateur formateur, boolean empty) {
                super.updateItem(formateur, empty);
                if (empty || formateur == null) {
                    setText(null);
                } else {
                    String texte = formateur.getPrenom() + " " + formateur.getNom();
                    List<String> specialites = formateur.getSpecialites();
                    if (!specialites.isEmpty()) {
                        texte += " (" + String.join(", ", specialites) + ")";
                    }
                    setText(texte);
                }
            }
        });
        fieldPlacesMax = new TextField();
        fieldEtat = new ComboBox<>();
        fieldEtat.getItems().setAll("Ouverte", "Complète", "Terminée", "Annulée");
        
        fieldApprenant = new ComboBox<>();
        fieldApprenant.getItems().setAll(dataService.getApprenants());

        sheet.setContent(creerFormulaireSheet());
        inscriptionSheet.setContent(creerFormulaireInscription());

        CustomButton btnAjouter = new CustomButton("Créer une session", Variant.PRIMARY, Size.MD);
        btnAjouter.setOnAction(e -> {
            sessionEnEdition = null;
            reinitialiserFormulaire();
            sheet.open();
        });

        TableColumn<Session, Void> colNum = new TableColumn<>("N°");
        colNum.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
                setAlignment(Pos.CENTER);
            }
        });
        colNum.setPrefWidth(50);

        TableColumn<Session, LocalDate> colDateDebut = new TableColumn<>("Date début");
        colDateDebut.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDateDebut()));
        colDateDebut.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(date.format(DATE_FORMATTER));
                }
                setAlignment(Pos.CENTER);
            }
        });

        // Date de fin
        TableColumn<Session, LocalDate> colDateFin = new TableColumn<>("Date fin");
        colDateFin.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDateFin()));
        colDateFin.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(date.format(DATE_FORMATTER));
                }
                setAlignment(Pos.CENTER);
            }
        });

        TableColumn<Session, String> colFormateur = new TableColumn<>("Formateur");
        colFormateur.setCellValueFactory(data -> {
            Formateur f = data.getValue().getFormateur();
            String nom = f.getPrenom() + " " + f.getNom();
            List<String> specialites = f.getSpecialites();
            if (!specialites.isEmpty()) {
                nom += " (" + String.join(", ", specialites) + ")";
            }
            return new javafx.beans.property.SimpleStringProperty(nom);
        });
        centrerCellules(colFormateur);
        colFormateur.setPrefWidth(250);

        TableColumn<Session, Integer> colPlacesMax = new TableColumn<>("Places max");
        colPlacesMax.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getNbPlacesMax()));
        centrerCellules(colPlacesMax);

        TableColumn<Session, String> colEtat = new TableColumn<>("État");
        colEtat.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEtat().getLabel()));
        centrerCellules(colEtat);

        TableColumn<Session, Void> colInscription = new TableColumn<>("Inscription");
        colInscription.setCellFactory(column -> new TableCell<>() {
            private final CustomButton btn = new CustomButton("Inscrire", Variant.PRIMARY, Size.SM);
            private final Label label = new Label();

            {
                btn.setOnAction(e -> {
                    Session s = getTableView().getItems().get(getIndex());
                    sessionPourInscription = s;
                    rafraichirFormulaireInscription();
                    inscriptionSheet.open();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Session s = getTableView().getItems().get(getIndex());
                    label.setText(s.getNbInscrits() + " / " + s.getNbPlacesMax());
                    
                    VBox box = new VBox(5);
                    box.setAlignment(Pos.CENTER);
                    box.getChildren().addAll(label, btn);
                    setGraphic(box);
                }
                setAlignment(Pos.CENTER);
            }
        });

        TableColumn<Session, Void> colModifier = new TableColumn<>("Modifier");
        colModifier.setCellFactory(column -> new TableCell<>() {
            private final CustomButton btn = new CustomButton("Modifier", Variant.SECONDARY, Size.SM);

            {
                btn.setOnAction(e -> {
                    Session s = getTableView().getItems().get(getIndex());
                    sessionEnEdition = s;

                    fieldDateDebut.setValue(s.getDateDebut());
                    fieldDateFin.setValue(s.getDateFin());
                    fieldFormateur.setValue(s.getFormateur());
                    fieldPlacesMax.setText(String.valueOf(s.getNbPlacesMax()));
                    fieldEtat.setValue(s.getEtat().getLabel());

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

        TableColumn<Session, Void> colSupprimer = new TableColumn<>("Supprimer");
        colSupprimer.setCellFactory(column -> new TableCell<>() {
            private final CustomButton btn = new CustomButton("Supprimer", Variant.DESTRUCTIVE, Size.SM);

            {
                btn.setOnAction(e -> {
                    Session s = getTableView().getItems().get(getIndex());
                    controller.supprimerSession(s);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
                setAlignment(Pos.CENTER);
            }
        });

        tableView.getColumns().addAll(colNum, colDateDebut, colDateFin, colFormateur, 
                                       colPlacesMax, colEtat, colInscription, colModifier, colSupprimer);

        tableView.setFixedCellSize(50);
        tableView.prefHeightProperty().bind(
                tableView.fixedCellSizeProperty().multiply(10).add(30)
        );

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        contentPane.getChildren().addAll(headerBox, btnAjouter, tableView);
        root.getChildren().add(contentPane);
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
                setAlignment(Pos.CENTER);
            }
        });
    }

    private VBox creerFormulaireSheet() {
        VBox formulaire = new VBox(20);

        Label titre = new Label("Session");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        fieldEtat.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (sessionEnEdition != null && newVal != null) {
                try {
                    int nbInscrits = sessionEnEdition.getNbInscrits();
                    int nbPlacesMax = Integer.parseInt(fieldPlacesMax.getText());
                    
                    if (nbInscrits >= nbPlacesMax && newVal.equals("Ouverte")) {
                        infoEtat.setText("⚠️ La session est complète, impossible de la passer en 'Ouverte'");
                        infoEtat.setVisible(true);
                        infoEtat.setManaged(true);
                    } else {
                        infoEtat.setVisible(false);
                        infoEtat.setManaged(false);
                    }
                } catch (NumberFormatException e) {}
            }
        });
        
        VBox champs = new VBox(16);
        champs.getChildren().addAll(
            creerChampFormulaireDatePicker("Date de début", fieldDateDebut, errorDateDebut),
            creerChampFormulaireDatePicker("Date de fin", fieldDateFin, errorDateFin),
            creerChampFormulaireComboBox("Formateur", fieldFormateur, errorFormateur, null),
            creerChampFormulaire("Nombre de places max", fieldPlacesMax, errorPlacesMax),
            creerChampFormulaireComboBox("État", fieldEtat, errorEtat, infoEtat)
        );

        HBox boutons = new HBox(12);

        CustomButton btnAnnuler = new CustomButton("Annuler", Variant.SECONDARY, Size.MD);
        btnAnnuler.setOnAction(e -> sheet.close());

        CustomButton btnValider = new CustomButton("Valider", Variant.PRIMARY, Size.MD);
        btnValider.setOnAction(e -> {
            if (validerFormulaire()) {
                Formateur formateur = fieldFormateur.getValue();
                String etatSelectionne = fieldEtat.getValue();
                
                Session nouvelle = new Session(
                        controller.getFormation(),
                        formateur,
                        fieldDateDebut.getValue(),
                        fieldDateFin.getValue(),
                        Integer.parseInt(fieldPlacesMax.getText())
                );

                if (sessionEnEdition == null) {
                    if (etatSelectionne.equals("Complète")) {
                        etatSelectionne = "Ouverte";
                    }
                    SessionState etat = creerEtatDepuisLabel(etatSelectionne, nouvelle);
                    nouvelle.changeState(etat);
                    controller.ajouterSession(nouvelle);
                } else {
                    controller.modifierSession(sessionEnEdition, nouvelle, etatSelectionne);
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

    private VBox creerChampFormulaireDatePicker(String label, DatePicker field, Label errorLabel) {
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

    private <T> VBox creerChampFormulaireComboBox(String label, ComboBox<T> field, Label errorLabel, Label infoLabel) {
        VBox container = new VBox(4);
        Label labelControl = new Label(label);
        field.setPrefHeight(36);
        field.setPrefWidth(350);
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px;");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        if (infoLabel != null) {
            infoLabel.setStyle("-fx-text-fill: #f39c12; -fx-font-size: 11px; -fx-font-style: italic;");
            infoLabel.setVisible(false);
            infoLabel.setManaged(false);
            container.getChildren().addAll(labelControl, field, errorLabel, infoLabel);
        } else {
            container.getChildren().addAll(labelControl, field, errorLabel);
        }
        return container;
    }

    private void reinitialiserFormulaire() {
        fieldDateDebut.setValue(null);
        fieldDateFin.setValue(null);
        fieldFormateur.setValue(null);
        fieldPlacesMax.clear();
        fieldEtat.setValue("Ouverte");
        infoEtat.setVisible(false);
        infoEtat.setManaged(false);
    }

    private boolean validerFormulaire() {
        fieldDateDebut.setStyle("");
        fieldDateFin.setStyle("");
        fieldFormateur.setStyle("");
        fieldPlacesMax.setStyle("");
        fieldEtat.setStyle("");
        
        errorDateDebut.setVisible(false);
        errorDateDebut.setManaged(false);
        errorDateFin.setVisible(false);
        errorDateFin.setManaged(false);
        errorFormateur.setVisible(false);
        errorFormateur.setManaged(false);
        errorPlacesMax.setVisible(false);
        errorPlacesMax.setManaged(false);
        errorEtat.setVisible(false);
        errorEtat.setManaged(false);
        
        boolean valide = true;
        
        if (fieldDateDebut.getValue() == null) {
            fieldDateDebut.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorDateDebut.setText("La date de début est obligatoire");
            errorDateDebut.setVisible(true);
            errorDateDebut.setManaged(true);
            valide = false;
        }
        
        if (fieldDateFin.getValue() == null) {
            fieldDateFin.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorDateFin.setText("La date de fin est obligatoire");
            errorDateFin.setVisible(true);
            errorDateFin.setManaged(true);
            valide = false;
        }
        
        if (fieldDateDebut.getValue() != null && fieldDateFin.getValue() != null) {
            if (fieldDateDebut.getValue().isAfter(fieldDateFin.getValue())) {
                fieldDateDebut.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                fieldDateFin.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                errorDateDebut.setText("La date de début doit être avant la date de fin");
                errorDateDebut.setVisible(true);
                errorDateDebut.setManaged(true);
                valide = false;
            }
        }
        
        if (fieldFormateur.getValue() == null) {
            fieldFormateur.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorFormateur.setText("Le formateur est obligatoire");
            errorFormateur.setVisible(true);
            errorFormateur.setManaged(true);
            valide = false;
        }
        
        if (fieldPlacesMax.getText().trim().isEmpty()) {
            fieldPlacesMax.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorPlacesMax.setText("Le nombre de places ne peut pas être vide");
            errorPlacesMax.setVisible(true);
            errorPlacesMax.setManaged(true);
            valide = false;
        } else {
            try {
                int places = Integer.parseInt(fieldPlacesMax.getText().trim());
                if (places <= 0) {
                    fieldPlacesMax.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    errorPlacesMax.setText("Le nombre de places doit être un nombre positif");
                    errorPlacesMax.setVisible(true);
                    errorPlacesMax.setManaged(true);
                    valide = false;
                }
            } catch (NumberFormatException e) {
                fieldPlacesMax.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                errorPlacesMax.setText("Le nombre de places doit être un nombre entier");
                errorPlacesMax.setVisible(true);
                errorPlacesMax.setManaged(true);
                valide = false;
            }
        }
        
        if (fieldEtat.getValue() == null) {
            fieldEtat.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorEtat.setText("L'état est obligatoire");
            errorEtat.setVisible(true);
            errorEtat.setManaged(true);
            valide = false;
        }
        
        if (infoEtat.isVisible()) {
            valide = false;
        }
        
        return valide;
    }

    private SessionState creerEtatDepuisLabel(String label, Session session) {
        return switch (label) {
            case "Ouverte" -> new EtatOuverte(session);
            case "Complète" -> new EtatComplete(session);
            case "Terminée" -> new EtatTerminee(session);
            case "Annulée" -> new EtatAnnulee(session);
            default -> new EtatOuverte(session);
        };
    }

    private VBox creerFormulaireInscription() {
        VBox formulaire = new VBox(20);
        formulaire.setPrefWidth(600);

        Label titre = new Label("Gérer les inscriptions");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Informations sur la session
        VBox infoSession = new VBox(8);
        labelPlacesInscription = new Label();
        labelPlacesInscription.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        infoSession.getChildren().add(labelPlacesInscription);

        // Section pour ajouter un nouvel apprenant
        VBox sectionAjout = new VBox(12);
        sectionAjout.setStyle("-fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #f9f9f9;");
        
        Label titreAjout = new Label("Ajouter un apprenant");
        titreAjout.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        HBox ligneAjout = new HBox(12);
        ligneAjout.setAlignment(Pos.CENTER_LEFT);
        fieldApprenant.setPrefWidth(300);
        fieldApprenant.setPrefHeight(36);
        
        CustomButton btnInscrire = new CustomButton("Inscrire", Variant.PRIMARY, Size.SM);
        btnInscrire.setOnAction(e -> {
            if (fieldApprenant.getValue() != null && sessionPourInscription != null) {
                Apprenant apprenant = fieldApprenant.getValue();
                sessionPourInscription.inscrire(apprenant);
                apprenant.ajouterSessionSuivie(sessionPourInscription);
                
                // Rafraîchir l'affichage de la feuille d'inscription
                rafraichirFormulaireInscription();
                
                // Rafraîchir le tableau principal
                updateTable(controller.getSessions());
            }
        });
        
        ligneAjout.getChildren().addAll(new Label("Sélectionner un apprenant :"), fieldApprenant, btnInscrire);
        sectionAjout.getChildren().addAll(titreAjout, ligneAjout);

        // Tableau des inscrits confirmés
        VBox sectionInscrits = new VBox(8);
        Label titreInscrits = new Label("Apprenants inscrits");
        titreInscrits.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        tableInscritsInscription = new TableView<>();
        tableInscritsInscription.setPrefHeight(200);
        
        TableColumn<Apprenant, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(150);
        
        TableColumn<Apprenant, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colPrenom.setPrefWidth(150);
        
        TableColumn<Apprenant, String> colEtatInscription = new TableColumn<>("État");
        colEtatInscription.setPrefWidth(120);
        colEtatInscription.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setText(null);
                    setStyle("");
                } else {
                    Apprenant apprenant = getTableView().getItems().get(getIndex());
                    
                    // Vérifier si l'apprenant est dans les inscrits confirmés ou en liste d'attente
                    boolean estInscrit = sessionPourInscription.getInscriptions().stream()
                        .anyMatch(i -> i.getApprenant().equals(apprenant));
                    
                    if (estInscrit) {
                        setText("Inscrit");
                        setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    } else {
                        setText("En attente");
                        setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
                    }
                }
                setAlignment(Pos.CENTER);
            }
        });
        
        tableInscritsInscription.getColumns().addAll(colNom, colPrenom, colEtatInscription);
        sectionInscrits.getChildren().addAll(titreInscrits, tableInscritsInscription);

        // Bouton fermer
        HBox boutons = new HBox(12);
        boutons.setAlignment(Pos.CENTER_RIGHT);
        CustomButton btnFermer = new CustomButton("Fermer", Variant.SECONDARY, Size.MD);
        btnFermer.setOnAction(e -> inscriptionSheet.close());
        boutons.getChildren().add(btnFermer);

        formulaire.getChildren().addAll(titre, infoSession, sectionAjout, sectionInscrits, boutons);
        
        return formulaire;
    }
    
    private void rafraichirFormulaireInscription() {
        if (sessionPourInscription == null) return;
        
        int nbInscrits = sessionPourInscription.getNbInscrits();
        int nbPlacesMax = sessionPourInscription.getNbPlacesMax();
        int nbEnAttente = sessionPourInscription.getListeAttente().size();
        int placesRestantes = nbPlacesMax - nbInscrits;
        
        // Mise à jour du label avec couleur
        String couleur = placesRestantes > 0 ? "#27ae60" : "#e74c3c";
        String statut = placesRestantes > 0 ? "Places disponibles" : "SESSION COMPLÈTE";
        String texte = String.format("%s : %d/%d places occupées", statut, nbInscrits, nbPlacesMax);
        if (nbEnAttente > 0) {
            texte += String.format(" (%d en attente)", nbEnAttente);
        } else if (placesRestantes > 0) {
            texte += String.format(" (%d place%s restante%s)", placesRestantes, 
                placesRestantes > 1 ? "s" : "", placesRestantes > 1 ? "s" : "");
        }
        labelPlacesInscription.setText(texte);
        labelPlacesInscription.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + couleur + ";");
        
        // Mise à jour du tableau des inscrits (inscrits + liste d'attente)
        List<Apprenant> tousLesInscrits = new ArrayList<>();
        
        // Ajouter d'abord les inscrits confirmés
        tousLesInscrits.addAll(sessionPourInscription.getInscriptions().stream()
            .map(Inscription::getApprenant)
            .toList());
        
        // Puis ajouter ceux en liste d'attente
        tousLesInscrits.addAll(sessionPourInscription.getListeAttente().stream()
            .map(Inscription::getApprenant)
            .toList());
        
        tableInscritsInscription.getItems().setAll(tousLesInscrits);
        
        // Mise à jour du ComboBox : retirer les apprenants déjà inscrits ou en attente
        List<Apprenant> apprenantsDisponibles = dataService.getApprenants().stream()
            .filter(a -> tousLesInscrits.stream().noneMatch(i -> i.equals(a)))
            .toList();
        fieldApprenant.getItems().setAll(apprenantsDisponibles);
        fieldApprenant.setValue(null);
    }

    @Override
    public void updateTable(List<Session> sessions) {
        tableView.getItems().setAll(sessions);
        tableView.refresh();
    }

    @Override
    public Pane getRoot() {
        return root;
    }
}
