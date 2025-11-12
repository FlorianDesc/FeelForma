package fr.ubordeaux.m1.view;

// Button import no longer used directly; CustomButton provides button variants
import fr.ubordeaux.m1.view.component.CustomButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Reusable application sidebar displayed on the left of main screens.
 * Accepts Runnables for navigation actions so it can be reused by each view.
 */
public class SidebarView {

    private final VBox root;
    private final CustomButton btnHome;
    private final CustomButton btnFormation;

    /**
     * Create a sidebar with navigation buttons.
     * @param stage primary stage (kept for possible future use)
     * @param onHome called when Home is requested
     * @param onFormation called when user navigates to Formation
     */
    public SidebarView(Stage stage, Runnable onHome, Runnable onFormation) {
        Label appLabel = new Label("FeelForma");
        // use css classes and keep minimal inline styling
        appLabel.getStyleClass().add("label");

    btnHome = new CustomButton("Accueil", CustomButton.Variant.SECONDARY);
    btnFormation = new CustomButton("Formations", CustomButton.Variant.SECONDARY);

        btnHome.setMaxWidth(Double.MAX_VALUE);
        btnFormation.setMaxWidth(Double.MAX_VALUE);

        // keep focus traversal disabled for now (can be enabled for accessibility later)
        btnHome.setFocusTraversable(false);
        btnFormation.setFocusTraversable(false);

    // attach CSS classes so styles come from sidebar.css
    btnHome.getStyleClass().add("sidebar-button");
    btnFormation.getStyleClass().add("sidebar-button");

        btnHome.setOnAction(e -> {
            if (onHome != null) onHome.run();
            setActive("home");
        });
        btnFormation.setOnAction(e -> {
            if (onFormation != null) onFormation.run();
            setActive("formation");
        });

        root = new VBox(12, appLabel, btnHome, btnFormation);
        root.getStyleClass().add("sidebar");
        root.setPadding(new Insets(12));
        root.setAlignment(Pos.TOP_LEFT);
        root.setPrefWidth(160);
    }

    /**
     * Mark a sidebar item as active. Valid keys: "home", "formation". Other values clear selection.
     */
    public void setActive(String key) {
        // remove active class from both then add to the selected one
        btnHome.getStyleClass().remove("active");
        btnFormation.getStyleClass().remove("active");
        if ("home".equalsIgnoreCase(key)) {
            if (!btnHome.getStyleClass().contains("active")) btnHome.getStyleClass().add("active");
        } else if ("formation".equalsIgnoreCase(key)) {
            if (!btnFormation.getStyleClass().contains("active")) btnFormation.getStyleClass().add("active");
        }
    }

    public Node getNode() {
        return root;
    }
}
