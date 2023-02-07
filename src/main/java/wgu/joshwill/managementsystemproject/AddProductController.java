package wgu.joshwill.managementsystemproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    /**
     * The FXML includes
     * TextField for ID, Name, Inventory, Price, Min, Max
     *
     */
    @javafx.fxml.FXML
    private TextField txtAddProductName;
    @javafx.fxml.FXML
    private TextField txtAddProductInv;
    @javafx.fxml.FXML
    private TextField txtAddProductPrice;
    @javafx.fxml.FXML
    private TextField txtAddProductMax;
    @javafx.fxml.FXML
    private TextField txtAddProductMin;
    /**
     * The FXML includes
     * Add Product Table
     * TableColumn: ID, Name, Inventory, Price/Cost
     */
    @javafx.fxml.FXML
    private TableView<Part> AddProductTable;
    @javafx.fxml.FXML
    private TableColumn<Part , Integer> AddProductIDColumn;
    @javafx.fxml.FXML
    private TableColumn<Part , String> AddProductNameColumn;
    @javafx.fxml.FXML
    private TableColumn<Part , Integer> AddProductInventoryColumn;
    @javafx.fxml.FXML
    private TableColumn<Part , Double> AddProductPriceColumn;

    /**
     * The FXML includes
     * Associate Part Table
     * TableColumn: ID, Name, Inventory, Price/Cost
     *
     */

    @javafx.fxml.FXML
    private TableView<Part> AssocPartTable;
    @javafx.fxml.FXML
    private TableColumn<Part , Integer> AssocAddProductID;
    @javafx.fxml.FXML
    private TableColumn<Part , String> AssocAddProductName;
    @javafx.fxml.FXML
    private TableColumn<Part , Integer> AssocAddProductInventoryColumn;
    @javafx.fxml.FXML
    private TableColumn<Part , Double> AssocAddProductPriceColumn;
    @javafx.fxml.FXML
    private TextField txtAddProductSearch;
    @FXML
    private Label txtAddProductID;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();



    /**
     * Adds selected objects to Associated Table
     *
     * Error Message if nothing is selected
     *
     * @param event Add Button
     */
    @FXML
    void AddBtnAddProduct(ActionEvent event) {
        Part selectedPart = AddProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            displayAlert(5);
        }
        else {
            associatedParts.add(selectedPart);
            AssocPartTable.setItems(associatedParts);
        }
    }

    /**
     * Confirmation Message to remove item from Associated Parts Table
     *
     * Error Message if nothing is selected
     *
     * @param event Remove Associated Button
     */
    @FXML
    void BtnRemoveAssocPart(ActionEvent event) {
        Part selectedPart = AssocPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            displayAlert(5);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("You sure you want to remove?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                associatedParts.remove(selectedPart);
                AssocPartTable.setItems(associatedParts);
            }
        }
    }

    /**
     * Save new product item to Inventory and return to Home Page
     *
     * Text Field is required
     * Error Message will show if requirements not met.
     *
     * @param event Save Button Functionality
     * @throws IOException from FXML Loader
     */
    @FXML
    void SaveBtnAddProduct(ActionEvent event) throws IOException {
        try {
            int id = 0;
            String name = txtAddProductName.getText();
            double price = Double.parseDouble(txtAddProductPrice.getText());
            int stock = Integer.parseInt(txtAddProductInv.getText());
            int min = Integer.parseInt(txtAddProductMin.getText());
            int max = Integer.parseInt(txtAddProductMax.getText());

            if (name.isEmpty()){
                displayAlert(6);
            }
            else {
                if(validMin(min, max) && validInv (min, max, stock)){
                    Product newProduct = new Product(id, name, price, stock, min, max);
                    for(Part part : associatedParts){
                        newProduct.addAssociatedPart(part);
                    }
                    newProduct.setId(Inventory.getNewProductId());
                    Inventory.addProduct(newProduct);
                    returnHomeScreen(event);
                }
            }
        } catch (Exception e){
            displayAlert(1);
        }
    }

    /**
     * Checks validation of Inventory Level
     *
     * @param min of Add Product
     * @param max of Add Product
     * @param stock of Add Product
     * @return True or False
     */
    private boolean validInv(int min, int max, int stock) {
        boolean isValid = true;

        if(stock < min || stock > max){
            isValid = false;
            displayAlert(4);
        }
        return isValid;
    }

    /**
     * Checks validation of min
     * @param min of Add Product
     * @param max of Add Product
     * @return True or False of min
     */
    private boolean validMin(int min, int max) {
        boolean isValid = true;

        if (min <= 0 || min >= max){
            isValid = false;
            displayAlert(3);
        }
        return isValid;
    }

    /**
     * A functionality that returns to Home Screen
     * @param event from Parent
     * @throws IOException from Loader
     */
    private void returnHomeScreen(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainScreenForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Confirmation Message and return to Home Screen
     *
     * @param event Cancel Button Functionality
     * @throws IOException from FXML Loader
     */
    @FXML
    void CancelBtnAddProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("You sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            returnHomeScreen(event);
        }
    }

    /**
     * Execute search based on input in search text box
     *
     * User can search by ID or Name
     *
     * @param event Add Product (Search Button)
     */
    @FXML
    public void AssocSearchButton(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String stringSearch = txtAddProductSearch.getText();

        for (Part part : allParts){
            if (String.valueOf(part.getId()).contains(stringSearch)|| part.getName().contains(stringSearch)){
                foundParts.add(part);
            }
        }
        AddProductTable.setItems(foundParts);
        if (foundParts.size() == 0){
            displayAlert(1);
        }
    }

    /**
     * Shows Part table after value entered into search text field
     *
     * @param event Add Product Search Text Field
     */
    @FXML
    public void AddProductKeyPress(KeyEvent event) {
        if (txtAddProductSearch.getText().isEmpty()){
            AddProductTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Alert Message
     * @param alertMessage Message Board
     */
    private void displayAlert(int alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertMessage) {
            case 1: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Error Add Product");
                alert.setContentText("Must fill blank fields.");
                alert.showAndWait();
            }
            case 2: {
                alertInfo.setTitle("Information");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
            }
            case 3: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
            }
            case 4: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must match valid value.");
                alert.showAndWait();
            }
            case 5: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Part not selected");
                alert.showAndWait();
            }
            case 6: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name required.");
                alert.showAndWait();
            }
        }
    }
    /**
     * Populates Parts and Products Table
     * @param url Location to Path
     * @param resourceBundle Resource to root
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddProductTable.setItems(Inventory.getAllParts());

        AssocAddProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocAddProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocAddProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocAddProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        System.out.println("Initialized");
    }
}
