package wgu.joshwill.managementsystemproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for main form application
 *
 *
 * @author Joshua Williams
 */

public class MainScreenController implements Initializable {

    /**
     * Selected object in Part Tableview
     */

    private static Part partModify;

    /**
     * Selected object in Product Tableview
     */

    private static Product productModify;

    /**
     * Main Form Parts Table
     * <p>
     * TableColumn: ID, Name, Inventory Level, Price/Cost Per Usual
     */
    @javafx.fxml.FXML
    private TableView<Part> Table;
    @javafx.fxml.FXML
    private TableColumn<Part, Integer> PartsIDColumn;
    @javafx.fxml.FXML
    private TableColumn<Part, String> PartsNameColumn;
    @javafx.fxml.FXML
    private TableColumn<Part, Integer> PartsInStockColumn;
    @javafx.fxml.FXML
    private TableColumn<Part, Double> PartsPriceColumn;
    @javafx.fxml.FXML
    private Label MainScreenParts;
    @javafx.fxml.FXML
    private Label MainScreenProducts;

    /**
     * Main Form Products Table
     * <p>
     * Table Column: ID, Name, Inventory Level, Price/Cost Per Usual
     */
    @javafx.fxml.FXML
    private TableView<Product> Table1;
    @javafx.fxml.FXML
    private TableColumn<Product, Integer> ProductsIDColumn;
    @javafx.fxml.FXML
    private TableColumn<Product, String> ProductsNameColumn;
    @javafx.fxml.FXML
    private TableColumn<Product, Integer> ProductsInStockColumn;
    @javafx.fxml.FXML
    private TableColumn<Product, Double> ProductsPriceColumn;

    /**
     * Getter for selected Parts object
     *
     * @return Parts object
     */

    public static Part getPartModify() {
        return partModify;
    }

    /**
     * Getter for selected Products object
     *
     * @return Parts object
     */

    public static Product getProductModify() {
        return productModify;
    }

    /**
     * Parts & Products Search Field
     */
    @FXML
    private TextField PartsSearch;
    @FXML
    private TextField ProductsSearch;

    /**
     * Loads Add Part Controller
     *
     * @param event Add button
     * @throws java.io.IOException from FXML Loader
     */

    @FXML
    void AddHandler(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("AddPartForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads Modify Part Controller
     * Display error message if Part isn't selected
     *
     * @param event Part (Modify Button)
     * @throws IOException from FXML Loader
     */
    @FXML
    void ModifyHandler(ActionEvent event) throws IOException {
        partModify = Table.getSelectionModel().getSelectedItem();

        if (partModify == null) {
            displayAlert(3);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("ModifyPartForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Remove selected Parts table
     * Method will display error message if no Part selected
     * Method will also have confirmation dialog to confirm selected Part
     *
     * @param event Delete Button
     */

    @FXML
    void DeleteHandler(ActionEvent event) {
        Part selectedPart = Table.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("You sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }

        }
    }

    /**
     * Execute search based on input in search text box
     * User type ID or Name to search Part
     *
     * @param event Parts (Search Button)
     */

    @FXML
    void PartSearchButton(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = PartsSearch.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        Table.setItems(partsFound);

        if (partsFound.size() == 0) {
            displayAlert(1);
        }
    }

    /**
     * Shows Part table after value entered into search text field
     *
     * @param event Part (Search Text Field)
     */

    @FXML
    void ProductSearchKeyPressed(KeyEvent event) {
        if (ProductsSearch.getText().isEmpty()) {
            Table1.setItems(Inventory.getAllProducts());
        }
    }


    /**
     * Load Add Product Controller
     *
     * @param event Add Button
     * @throws IOException from FXML Loader
     */

    @FXML
    void AddHandler1(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads Modify Product Controller
     * Display error message if Product isn't selected
     *
     * RUNTIME ERROR: Receive a RUNTIME ERROR when trying to modify selected item.
     *
     * @param event Product (Modify Button)
     * @throws IOException from FXML Loader
     */

    @FXML
    void ModifyHandler1(ActionEvent event) throws IOException {
        productModify = Table1.getSelectionModel().getSelectedItem();

        if (productModify == null) {
            displayAlert(4);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("ModifyProductForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Remove selected Products table
     * Method will display error message if no Product selected
     * Method will also have confirmation dialog to confirm selected Product
     *
     * @param event Delete Button
     */

    @FXML
    void DeleteHandler1(ActionEvent event) {
        Product selectedProduct = Table1.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            displayAlert(4);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("You sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();

                if (associatedParts.size() >= 1) {
                    displayAlert(5);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    /**
     * Execute search based on input in search text box
     * User type ID or Name to search Product
     *
     * @param event Products (Search Button)
     */

    @FXML
    void ProductSearchButton(ActionEvent event) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = ProductsSearch.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }
        if (productsFound.size() == 0) {
            displayAlert(2);
        }
    }

    /**
     * Shows Product table after value entered into search text field
     *
     * @param event Product (Search Text Field)
     */

    @FXML
    void PartSearchKeyPressed(KeyEvent event) {
        if (PartsSearch.getText().isEmpty()) {
            Table.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Exit Application
     *
     * @param event Exit button
     */
    @FXML
    void ExitHandler(ActionEvent event) {
        System.exit(0);
    }


    /**
     * Alert Message Board
     *
     * @param alertType Activate Alert Message
     */

    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Product not found");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Warning!");
                alertError.setHeaderText("Part not selected");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Warning!");
                alertError.setHeaderText("Product not selected");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Warning!");
                alertError.setHeaderText("Parts Associated");
                alertError.setContentText("All parts must be removed from product before deletion.");
                alertError.showAndWait();
                break;
        }
    }

    /**
     * Populates Parts and Products Table
     *
     * @param url            Location Path
     * @param resourceBundle Resource for root
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Table.setItems(Inventory.getAllParts());
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsInStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        Table1.setItems(Inventory.getAllProducts());
        ProductsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductsInStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        System.out.println("Initialized");

    }
}