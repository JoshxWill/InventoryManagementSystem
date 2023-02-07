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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    /**TextField for ID, Name, Inv, Price/Cost, Max, Min*/
    @javafx.fxml.FXML
    private Label txtModifyProductID;
    @javafx.fxml.FXML
    private TextField txtModifyProductName;
    @javafx.fxml.FXML
    private TextField txtModifyProductInv;
    @javafx.fxml.FXML
    private TextField txtModifyProductPrice;
    @javafx.fxml.FXML
    private TextField txtModifyProductMax;
    @javafx.fxml.FXML
    private TextField txtModifyProductMin;

    /**Table Columns for both Tables
     *
     * ID, Name, Inventory, Price/Cost
     */
    @javafx.fxml.FXML
    private TableColumn<Part, Integer> ModifyProductIDColumn;
    @javafx.fxml.FXML
    private TableColumn<Part , String> ModifyProductNameColumn;
    @javafx.fxml.FXML
    private TableColumn<Part , Integer> ModifyProductInventoryColumn;
    @javafx.fxml.FXML
    private TableColumn<Part , Double> ModifyProductPriceColumn;
    @javafx.fxml.FXML
    private TableColumn<Part , Integer> AssocModifyProductID;
    @javafx.fxml.FXML
    private TableColumn<Part , String> AssocModifyProductName;
    @javafx.fxml.FXML
    private TableColumn<Part , Integer> AssocModifyProductInventory;
    @javafx.fxml.FXML
    private TableColumn<Part , Double> AssocModifyProductPrice;

    /**TableView for Modify Product Table & Associated Modify Product Table*/
    @FXML
    private TableView<Part> ModifyProductTable;
    @FXML
    private TableView<Part> AssocModifyProductTable;
    @FXML
    private TextField txtSearchModifyProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     * Adds selected objects to Associated Table
     *
     * Error Message if nothing is selected
     *
     * @param event Add Button Functionality
     */
    @javafx.fxml.FXML
    void AddModifyBtn(ActionEvent event) {
        Part selectedPart = ModifyProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            displayAlert(5);
        }
        else {
            associatedParts.add(selectedPart);
            AssocModifyProductTable.setItems(associatedParts);
        }
    }

    /**
     * Confirmation Message to remove item from Associated Parts Table
     *
     * Error Message if nothing is selected
     *
     * @param event Remove Associated Part Button Functionality
     */
    @FXML
    void BtnModifyProductRemove(ActionEvent event) {
        Part selectedPart = AssocModifyProductTable.getSelectionModel().getSelectedItem();

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
                AssocModifyProductTable.setItems(associatedParts);
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
    void SaveModifyBtn(ActionEvent event) throws IOException {
        try {
            int id = productSelect.getId();
            String name = txtModifyProductName.getText();
            Double price = Double.parseDouble(txtModifyProductPrice.getText());
            int stock = Integer.parseInt(txtModifyProductInv.getText());
            int min = Integer.parseInt(txtModifyProductMin.getText());
            int max = Integer.parseInt(txtModifyProductMax.getText());

            if (name.isEmpty()){
                displayAlert(6);
            }
            else {
                if(validMin(min, max) && validInv (min, max, stock)){
                    Product newProduct = new Product(id, name, price, stock, min, max);
                    for(Part part : associatedParts){
                        newProduct.addAssociatedPart(part);
                    }
                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(productSelect);
                    returnHomeScreen(event);
                }
            }
        } catch (Exception e){
            displayAlert(1);
        }
    }

    private Product productSelect;

    /**
     * Checks validation of Inventory Level
     *
     * @param min of Add Part
     * @param max of Add Part
     * @param stock of Add Part
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
     *
     * @param min of Add Part
     * @param max of Add Part
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
     *
     * RUNTIME ERROR: For FXMLLoader, I've entered the wrong path to the MainScreenForm which cause an error when canceling
     * and returning to home screen.
     *
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
     * RUNTIME ERROR: Received an error insert the wrong path for returnHomeScreen
     * actionEvent which caused an error for CancelModifyBtn to properly function.
     *
     * @param event Cancel Button Functionality
     * @throws IOException from FXML Loader
     */
    @FXML
    void CancelModifyBtn(ActionEvent event) throws IOException {
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
     * @param event Search Button Functionality
     */
    @FXML
    void SearchModifyProductBtn(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String stringSearch = txtSearchModifyProduct.getText();

        for (Part part : allParts){
            if (String.valueOf(part.getId()).contains(stringSearch)|| part.getName().contains(stringSearch)){
                foundParts.add(part);
            }
        }
        ModifyProductTable.setItems(foundParts);
        if (foundParts.size() == 0){
            displayAlert(1);
        }
    }

    /**
     * Shows Part table after value entered into search text field
     * @param event Part (Search Text Field)
     */
    @FXML
    void ModifyProductKeyPress(KeyEvent event) {
        if (txtSearchModifyProduct.getText().isEmpty()){
            ModifyProductTable.setItems(Inventory.getAllParts());
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
            case 1:
                alert.setTitle("Warning!");
                alert.setHeaderText("Error Modify Product");
                alert.setContentText("Must fill blank fields.");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Information");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
                break;
            case 3:
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must match valid value.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Warning!");
                alert.setHeaderText("Part not selected");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Warning!");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name required.");
                alert.showAndWait();
                break;
        }
    }
    /**
     * Populates Parts and Products Table
     *
     * RUNTIME ERROR: Receives a RUNTIME ERROR caused by java.lang.NullPointerException. Mentions that
     * it cannot invoke "wgu.joshwill.managementsystemproject.Product.getId()" because "this.productSelect" is null
     *
     *
     * @param url Location of path
     * @param resourceBundle Resource to root
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productSelect = MainScreenController.getProductModify();
        ModifyProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModifyProductTable.setItems(Inventory.getAllParts());

        associatedParts = productSelect.getAllAssociatedParts();
        AssocModifyProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocModifyProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocModifyProductInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocModifyProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssocModifyProductTable.setItems(associatedParts);

        txtModifyProductID.setText(String.valueOf(productSelect.getId()));
        txtModifyProductName.setText(productSelect.getName());
        txtModifyProductInv.setText(String.valueOf(productSelect.getStock()));
        txtModifyProductPrice.setText(String.valueOf(productSelect.getPrice()));
        txtModifyProductMax.setText(String.valueOf(productSelect.getMax()));
        txtModifyProductMin.setText(String.valueOf(productSelect.getMin()));

        System.out.println("Initialized");
    }
}

