module wgu.joshwill.managementsystemproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens wgu.joshwill.managementsystemproject to javafx.fxml;
    exports wgu.joshwill.managementsystemproject;
}