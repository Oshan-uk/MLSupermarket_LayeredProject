package lk.ijse.mlsupermarket.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.mlsupermarket.App;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.CustomerDTO;
import lk.ijse.mlsupermarket.model.CustomerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CustomerViewController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField contactField;

    @FXML
    private TableView<CustomerDTO> tblCustomer;

    @FXML
    private TableColumn<CustomerDTO, String> colId;

    @FXML
    private TableColumn<CustomerDTO, String> colName;

    @FXML
    private TableColumn<CustomerDTO, String> colContact;

    private final String CUSTOMER_ID_REGEX = "^[A-Za-z0-9]+$";
    private final String CUSTOMER_NAME_REGEX = "^[A-Za-z]{3,}(\\s[A-Za-z]{3,})?$";
    private final String CUSTOMER_CONTACT_REGEX = "^[0-9]{10,15}$";

    private final CustomerModel customerModel = new CustomerModel();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNo"));

        loadCustomerTable();

        try {
            idField.setText(customerModel.generateNextCustomerId());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void handleSaveCustomer() {

        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();

        if (!name.matches(CUSTOMER_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
        } else if (!contact.matches(CUSTOMER_CONTACT_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number").show();
        } else {
            try {
                CustomerDTO customer = new CustomerDTO(idField.getText().trim(), name, contact);
                boolean result = customerModel.saveCustomer(customer);

                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
                    cleanFields();
                    loadCustomerTable();
                    idField.setText(customerModel.generateNextCustomerId());

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
    private void handleSearchCustomer(KeyEvent event) {

        try {
            if (event.getCode() == KeyCode.ENTER) {
                String id = idField.getText().trim();

                if (!id.matches(CUSTOMER_ID_REGEX)) {
                    new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
                } else {
                    CustomerDTO customer = customerModel.searchCustomer(id);

                    if (customer != null) {
                        nameField.setText(customer.getName());
                        contactField.setText(customer.getContactNo());
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    @FXML
    private void handleCustomerUpdate() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();

            if (!id.matches(CUSTOMER_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid customer id").show();
            } else if (!name.matches(CUSTOMER_NAME_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
            } else if (!contact.matches(CUSTOMER_CONTACT_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid contact number").show();
            } else {
                CustomerDTO customer = new CustomerDTO(id, name, contact);
                boolean result = customerModel.updateCustomer(customer);

                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
                    cleanFields();
                    loadCustomerTable();

                    try {
                        idField.setText(customerModel.generateNextCustomerId());
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
    private void handleCustomerDelete() {
        try {
            String id = idField.getText().trim();

            if (!id.matches(CUSTOMER_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
            } else {
                boolean result = customerModel.deleteCustomer(id);

                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully!").show();
                    cleanFields();
                    loadCustomerTable();

                    try {
                        idField.setText(customerModel.generateNextCustomerId());
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
            idField.setText(customerModel.generateNextCustomerId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cleanFields() {
        idField.clear();
        nameField.setText("");
        contactField.setText("");

    }

    private void loadCustomerTable() {
        try {
            List<CustomerDTO> customerList = customerModel.getCustomers();
            ObservableList<CustomerDTO> obList = FXCollections.observableArrayList();
            obList.addAll(customerList);
            tblCustomer.setItems(obList);
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
    private void handleCustomerTableClick() {

        CustomerDTO selectedCustomer =
                tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            return;
        }

        idField.setText(selectedCustomer.getId());
        nameField.setText(selectedCustomer.getName());
        contactField.setText(selectedCustomer.getContactNo());
    }

}
