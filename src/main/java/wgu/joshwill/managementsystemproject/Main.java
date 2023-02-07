package wgu.joshwill.managementsystemproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * C482 - Software I
 * Inventory Management System
 * Contains data such as parts and products
 * This project creates a database for inventory
 *
 * FUTURE ENHANCEMENTS: I would consider moving Inventory Management System to a cloud-base solution to capture real time
 * data. This system with future upgrades, can be used by various retail businesses. Also, adding this Inventory Management System to a database.
 * I would try to be more creative on the UX/UI of the application making it more user-friendly and easy to understand and use.
 * Making navigating application simple as possible for future clients. The feel and look of this application would be my primary enhancement.
 *
 * @author Joshua Williams
 */
public class Main extends Application {

    /**
     *Starts the Application.
     *
     * @param stage Main Screen
     * @throws Exception Activate Main
     */

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("MainScreenForm.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Launches the Application
     *
     * @param args Launch Application
     */
    public static void main(String[] args) {
        launch(args);
    }
}

