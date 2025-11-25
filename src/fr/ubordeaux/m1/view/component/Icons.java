package fr.ubordeaux.m1.view.component;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;

public class Icons {

    public static Node user(double size) {
        SVGPath p = new SVGPath();
        p.setContent(
            "M12 2a5 5 0 1 0 5 5a5 5 0 0 0-5-5m0 8a3 3 0 1 1 3-3a3 3 0 0 1-3 3m9 11v-1a7 7 0 0 0-7-7h-4a7 7 0 0 0-7 7v1h2v-1a5 5 0 0 1 5-5h4a5 5 0 0 1 5 5v1z"
        );

        p.getStyleClass().add("icon");

        p.setScaleX(size / 24);
        p.setScaleY(size / 24);

        return new StackPane(p);
    }

    public static Node book(double size) {
        SVGPath p = new SVGPath();
        p.setContent(
            "M21 3h-7a2.98 2.98 0 0 0-2 .78A2.98 2.98 0 0 0 10 3H3a1 1 0 0 0-1 1v15a1 1 0 0 0 1 1h5.758c.526 0 1.042.214 1.414.586l1.121 1.121c.009.009.021.012.03.021c.086.079.182.149.294.196h.002a1 1 0 0 0 .762 0h.002c.112-.047.208-.117.294-.196c.009-.009.021-.012.03-.021l1.121-1.121A2.02 2.02 0 0 1 15.242 20H21a1 1 0 0 0 1-1V4a1 1 0 0 0-1-1M8.758 18H4V5h6c.552 0 1 .449 1 1v12.689A4.03 4.03 0 0 0 8.758 18M20 18h-4.758c-.799 0-1.584.246-2.242.689V6c0-.551.448-1 1-1h6z"
        );

        p.getStyleClass().add("icon");

        p.setScaleX(size / 24);
        p.setScaleY(size / 24);

        return new StackPane(p);
    }
}
