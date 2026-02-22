package lk.ijse.mlsupermarket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.mlsupermarket.App;
import lk.ijse.mlsupermarket.bo.BOFactory;
import lk.ijse.mlsupermarket.bo.custom.SupplierBO;
import lk.ijse.mlsupermarket.dto.SupplierDTO;
import lk.ijse.mlsupermarket.model.SupplierModel;

import java.util.List;

public class SupplierController {

    @FXML
    private TextField supplierIdField;

    @FXML
    private TextField supplierNameField;

    @FXML
    private TextField supplierContactField;

    @FXML
    private Button btnDashboard;

    @FXML
    private TableView<SupplierDTO> tblSuppliers;

    @FXML
    private TableColumn<SupplierDTO, String> colSupplierId;

    @FXML
    private TableColumn<SupplierDTO, String> colSupplierName;

    @FXML
    private TableColumn<SupplierDTO, String> colSupplierContact;


    private final String SUPPLIER_ID_REGEX = "^[A-Za-z0-9]+$";
    private final String SUPPLIER_NAME_REGEX = "^[A-Za-z]{3,}(\\s[A-Za-z]{3,})*$";
    private final String SUPPLIER_CONTACT_REGEX = "^[0-9]{10,15}$";


    private final SupplierBO supplierBO =
            (SupplierBO) BOFactory.getInstance()
                    .getBO(BOFactory.BOTypes.SUPPLIER);
    @FXML
    public void initialize() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupplierContact.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));

        loadSupplierTable();

        try {
            supplierIdField.setText(supplierBO.generateNextSupplierId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveSupplier() {

        String name = supplierNameField.getText().trim();
        String contact = supplierContactField.getText().trim();

        if (!name.matches(SUPPLIER_NAME_REGEX)) {

            new Alert(Alert.AlertType.ERROR, "Invalid supplier name").show();

        } else if (!contact.matches(SUPPLIER_CONTACT_REGEX)) {

            new Alert(Alert.AlertType.ERROR, "Invalid contact number").show();

        } else {

            try {

                SupplierDTO supplier = new SupplierDTO(supplierIdField.getText().trim(), name, contact);

                boolean result = supplierBO.saveSupplier(supplier);

                if (result) {

                    new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully!").show();

                    cleanFields();
                    loadSupplierTable();
                    supplierIdField.setText(supplierBO.generateNextSupplierId());


                } else {

                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }

            } catch (Exception e) {

                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();

            }
        }
    }

    @FXML
    private void handleSearchSupplier(KeyEvent event) {
        try {

            if (event.getCode() == KeyCode.ENTER) {

                String id = supplierIdField.getText().trim();

                if (!id.matches(SUPPLIER_ID_REGEX)) {

                    new Alert(Alert.AlertType.ERROR, "Invalid ID").show();

                } else {

                    SupplierDTO supplier = supplierBO.searchSupplier(id);

                    if (supplier != null) {

                        supplierNameField.setText(supplier.getName());
                        supplierContactField.setText(supplier.getContactInfo());

                    } else {

                        new Alert(Alert.AlertType.ERROR, "Supplier not found!").show();

                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    @FXML
    private void handleSupplierUpdate() {
        try {

            String id = supplierIdField.getText().trim();
            String name = supplierNameField.getText().trim();
            String contact = supplierContactField.getText().trim();


            if (!id.matches(SUPPLIER_ID_REGEX)) {

                new Alert(Alert.AlertType.ERROR, "Invalid supplier id").show();

            } else if (!name.matches(SUPPLIER_NAME_REGEX)) {

                new Alert(Alert.AlertType.ERROR, "Invalid supplier name").show();

            } else if (!contact.matches(SUPPLIER_CONTACT_REGEX)) {

                new Alert(Alert.AlertType.ERROR, "Invalid contact number").show();

            } else {

                SupplierDTO supplier = new SupplierDTO(id, name, contact);

                boolean result = supplierBO.updateSupplier(supplier);

                if (result) {

                    new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully!").show();

                    cleanFields();
                    loadSupplierTable();

                    try {
                        supplierIdField.setText(supplierBO.generateNextSupplierId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();

                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    @FXML
    private void handleSupplierDelete() {
        try {

            String id = supplierIdField.getText().trim();

            if (!id.matches(SUPPLIER_ID_REGEX)) {

                new Alert(Alert.AlertType.ERROR, "Invalid ID").show();

            } else {

                boolean result = supplierBO.deleteSupplier(id);

                if (result) {

                    new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully!").show();

                    cleanFields();
                    loadSupplierTable();

                    try {
                        supplierIdField.setText(supplierBO.generateNextSupplierId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();

                }

            }
        } catch (Exception e) {

            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    @FXML
    private void handleReset() {

        cleanFields();

        try {
            supplierIdField.setText(supplierBO.generateNextSupplierId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanFields() {

        supplierIdField.setText("");
        supplierNameField.setText("");
        supplierContactField.setText("");
    }

    private void loadSupplierTable() {
        try {

            List<SupplierDTO> supplierList = supplierBO.getAllSuppliers();

            ObservableList<SupplierDTO> obList = FXCollections.observableArrayList();

            obList.addAll(supplierList);
            tblSuppliers.setItems(obList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDashboard() {
        try {

            App.setRoot("Dashboard");

        } catch (Exception e) {

            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot open Dashboard!").show();

        }
    }

    @FXML
    private void handleSupplierTableClick() {

        SupplierDTO selectedSupplier =
                tblSuppliers.getSelectionModel().getSelectedItem();

        if (selectedSupplier == null) {
            return;
        }

        supplierIdField.setText(selectedSupplier.getSupplierId());
        supplierNameField.setText(selectedSupplier.getName());
        supplierContactField.setText(selectedSupplier.getContactInfo());
    }

}
