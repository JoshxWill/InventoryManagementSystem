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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    /**
     * Label for Modify Part
     */
    @javafx.fxml.FXML
    private Label ModifyPartForm;
    /**
     * Radio Button for In-House
     */
    @javafx.fxml.FXML
    private RadioButton ModifyPartInHouseRadio;

    /**
     * Radio Button for Outsourced
     */
    @javafx.fxml.FXML
    private RadioButton ModifyPartOutsourcedRadio;

    /**
     * Label for ID
     */
    @javafx.fxml.FXML
    private Label ModifyPartID;

    /**
     * Label for Name
     */
    @javafx.fxml.FXML
    private Label ModifyPartName;

    /**
     * Label for Inventory
     */
    @javafx.fxml.FXML
    private Label ModifyPartInv;

    /**
     * Label for Price/Cost
     */
    @javafx.fxml.FXML
    private Label ModifyPartPrice;

    /**
     * Label for Max
     */
    @javafx.fxml.FXML
    private Label ModifyPartMax;

    /**
     * Label for Company Name
     */
    @javafx.fxml.FXML
    private Label ModifyPartCompanyName;

    /**
     *
     * TextField for each Label:
     *
     * Name, Inventory, Price/Cost, Max, Min,
     */
    @javafx.fxml.FXML
    private TextField txtModifyPartName;
    @javafx.fxml.FXML
    private TextField txtModifyPartInv;
    @javafx.fxml.FXML
    private TextField txtModifyPartPrice;
    @javafx.fxml.FXML
    private TextField txtModifyPartMax;
    @javafx.fxml.FXML
    private TextField txtModifyPartCompanyName;

    /**
     * Label for Min
     */
    @javafx.fxml.FXML
    private Label ModifyPartMin;
    @javafx.fxml.FXML
    private TextField txtModifyPartMin;
    /**
     * Button for Save & Cancel
     */
    @javafx.fxml.FXML
    private Button btnModifyPartSave;
    @javafx.fxml.FXML
    private Button btnModifyPartCancel;
    /**
     * Label for ID TextField
     */
    @javafx.fxml.FXML
    private Label txtModifyPartID;

    /**
     * When In-House radio selected, Machine ID appears
     * @param actionEvent In-House (Radio Button)
     */
    @javafx.fxml.FXML
    void ModifyPartInHouse(ActionEvent actionEvent) {
        ModifyPartCompanyName.setText("Machine ID");
    }

    /**
     * When Outsourced radio selected, Company Name appears
     * @param actionEvent Outsourced (Radio Button)
     */
    @javafx.fxml.FXML
    void ModifyPartOutsourced(ActionEvent actionEvent) {
        ModifyPartCompanyName.setText("Company Name");
    }

    /**
     * Adds to Inventory when saved and returns to Main Screen to show update
     *
     * @param actionEvent Save Button
     * @throws IOException from FXML Loader
     */
    @javafx.fxml.FXML
    void ModifyPartSaveHandler(ActionEvent actionEvent) throws IOException {
        try {
            int id = partSelect.getId();
            String name = txtModifyPartName.getText();
            Double price = Double.parseDouble(txtModifyPartPrice.getText());
            int stock = Integer.parseInt(txtModifyPartInv.getText());
            int min = Integer.parseInt(txtModifyPartMin.getText());
            int max = Integer.parseInt(txtModifyPartMax.getText());
            int machineID;
            String companyName;
            boolean addPartSuccessful = false;

            if(validMin(min, max) && validInv(min, max, stock)) {
                if (ModifyPartInHouseRadio.isSelected()) {
                    try {
                        machineID = Integer.parseInt(txtModifyPartName.getText());
                        InHouse newInHouse = new InHouse(id, name, price, stock, min, max, machineID);
                        newInHouse.setId(Inventory.getNewPartID());
                        Inventory.addPart(newInHouse);
                        addPartSuccessful = true;
                    }
                    catch (Exception e){
                        displayAlert(2);
                    }
                }
                if (ModifyPartOutsourcedRadio.isSelected()) {
                    companyName = txtModifyPartName.getText();
                    Outsourced newOutsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newOutsourced);
                    addPartSuccessful = true;
                }
                if (addPartSuccessful) {
                    Inventory.deletePart(partSelect);
                    returnHomeScreen(actionEvent);
                }
            }

        } catch (Exception e) {
            displayAlert(1);

        }
    }

    private Part partSelect;

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
     * Confirmation Dialog for Cancel
     *
     * @param actionEvent Cancel Button
     * @throws IOException from FXML Loader
     */
    @javafx.fxml.FXML
    void ModifyPartCancelHandler(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("You sure you want to cancel and return to home page?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            returnHomeScreen(actionEvent);
        }
    }
    /**
     * Return to Home Screen
     *
     * @param event from Parent
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
            case 1:
                alert.setTitle("Warning!");
                alert.setHeaderText("Error Modify Part");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid Machine ID value");
                alert.setContentText("Must contain numbers only.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid Min value");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid Inventory value");
                alert.setContentText("Inventory must match valid value.");
                alert.showAndWait();
                break;
        }
    }


    /**
     *Modify Controller, Populates Text Fields
     *
     * @param url Location Path
     * @param resourceBundle Resource to root
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partSelect = MainScreenController.getPartModify();

        if (partSelect instanceof InHouse) {
            ModifyPartInHouseRadio.setSelected(true);
            ModifyPartCompanyName.setText("Machine ID");
            txtModifyPartCompanyName.setText(String.valueOf(((InHouse) partSelect).getMachineId()));
        }

        if (partSelect instanceof Outsourced){
            ModifyPartOutsourcedRadio.setSelected(true);
            ModifyPartCompanyName.setText("Company Name");
            txtModifyPartCompanyName.setText(((Outsourced) partSelect).getCompanyName());
        }

        txtModifyPartID.setText(String.valueOf(partSelect.getId()));
        txtModifyPartName.setText(partSelect.getName());
        txtModifyPartInv.setText(String.valueOf(partSelect.getStock()));
        txtModifyPartPrice.setText(String.valueOf(partSelect.getPrice()));
        txtModifyPartMax.setText(String.valueOf(partSelect.getMax()));
        txtModifyPartMin.setText(String.valueOf(partSelect.getMin()));

        System.out.println("Initialized");
    }
}




