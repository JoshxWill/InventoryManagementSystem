package wgu.joshwill.managementsystemproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for Add Part Application
 *
 * @author Joshua Williams
 */
public class AddPartController implements Initializable {

    /**
     * The FXML includes
     * RadioButton: AddPartInHouseRadio, AddPartOutSourcedRadio
     * Labels: AddPartForm, AddPartID, AddPartName, AddPartInv, AddPartPrice, etc.
     * TextField for each label above
     * Button: Save, Cancel
     */
    @javafx.fxml.FXML
    private RadioButton AddPartInHouseRadio;
    @javafx.fxml.FXML
    private RadioButton AddPartOutsourcedRadio;
    @javafx.fxml.FXML
    private Label AddPartMachineID;
    @javafx.fxml.FXML
    private TextField txtAddPartName;
    @javafx.fxml.FXML
    private TextField txtAddPartInv;
    @javafx.fxml.FXML
    private TextField txtAddPartPrice;
    @javafx.fxml.FXML
    private TextField txtAddPartMax;
    @javafx.fxml.FXML
    private TextField txtAddPartMin;
    @javafx.fxml.FXML
    private Label AddPartForm;
    @javafx.fxml.FXML
    private Label AddPartID;
    @javafx.fxml.FXML
    private Label AddPartName;
    @javafx.fxml.FXML
    private Label AddPartInv;
    @javafx.fxml.FXML
    private Label AddPartPrice;
    @javafx.fxml.FXML
    private Label AddPartMax;
    @javafx.fxml.FXML
    private TextField txtAddPartMachineID;
    @javafx.fxml.FXML
    private Label AddPartMin;
    @javafx.fxml.FXML
    private Button btnAddPartSave;
    @javafx.fxml.FXML
    private Button btnAddPartCancel;
    @javafx.fxml.FXML
    private Label txtAddPartId;

    /**
     * When In-House radio selected, Machine ID appears
     *
     * @param event In-House (Radio Button)
     */
    @javafx.fxml.FXML
    void AddPartInHouse(ActionEvent event) {

        AddPartMachineID.setText("Machine ID");
    }

    /**
     * When Outsourced radio selected, Company Name appears
     *
     *
     * @param event Outsourced (Radio Button)
     */
    @javafx.fxml.FXML
    void AddPartOutsourced(ActionEvent event) {

        AddPartMachineID.setText("Company Name");
    }

    /**
     * Adds to Inventory when saved and returns to Main Screen to show update
     *
     * @param event Save Button
     */
    @javafx.fxml.FXML
    void AddPartSaveHandler(ActionEvent event) {
        try {
            int id = 0;
            String name = txtAddPartName.getText();
            double price = Double.parseDouble(txtAddPartPrice.getText());
            int stock = Integer.parseInt(txtAddPartInv.getText());
            int min = Integer.parseInt(txtAddPartMin.getText());
            int max = Integer.parseInt(txtAddPartMax.getText());
            int machineID;
            String companyName;
            boolean addPartSuccessful = false;
            if (name.isEmpty()){
                displayAlert(5);
            }
            else{
                if(validMin(min, max) && validInv(min, max, stock)) {
                    if (AddPartInHouseRadio.isSelected()) {
                        try {
                            machineID = Integer.parseInt(txtAddPartMachineID.getText());
                            InHouse newInHouse = new InHouse(id, name, price, stock, min, max, machineID);
                            newInHouse.setId(Inventory.getNewPartID());
                            Inventory.addPart(newInHouse);
                            addPartSuccessful = true;
                        }
                        catch (Exception e){
                            displayAlert(2);
                        }
                    }
                    if (AddPartOutsourcedRadio.isSelected()) {
                        companyName = txtAddPartMachineID.getText();
                        Outsourced newOutsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                        newOutsourced.setId(Inventory.getNewPartID());
                        Inventory.addPart(newOutsourced);
                        addPartSuccessful = true;
                    }
                    if (addPartSuccessful) {
                        returnHomeScreen(event);
                    }
                }
            }
        }   catch (Exception e) {
            displayAlert(1);
        }
    }

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

        if(stock < min || stock >max){
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
     * Confirmation Dialog for Cancel
     *
     * @param event Cancel Button
     * @throws IOException from FXML Loader
     *
     */
    @javafx.fxml.FXML
    void AddPartCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("You sure you want to cancel and return to home page?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            returnHomeScreen(event);
        }
    }
    /**
     * Return to Main Form
     *
     * @param event From Parent
     * @throws IOException from FXML Loader
     */

    private void returnHomeScreen(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainScreenForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Alert Messages
     *
     * @param alertMessage Message Board
     */
    private void displayAlert(int alertMessage) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertMessage) {
            case 1: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Error Add Part");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
            }
            case 2: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid Machine ID value");
                alert.setContentText("Must contain numbers only.");
                alert.showAndWait();
            }
            case 3: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid Min value");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
            }
            case 4: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid Inventory value");
                alert.setContentText("Inventory must match valid value.");
                alert.showAndWait();
            }
            case 5: {
                alert.setTitle("Warning!");
                alert.setHeaderText("Name Field Empty");
                alert.setContentText("Name Required.");
                alert.showAndWait();
            }
        }
    }


    /**
     *Modified controller , sets In-House button
     *
     * @param url Location Path
     * @param resourceBundle Resources used for root
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AddPartInHouseRadio.setSelected(true);

        System.out.println("Initialized");
    }
}




