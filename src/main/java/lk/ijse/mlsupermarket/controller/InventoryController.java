package lk.ijse.mlsupermarket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.mlsupermarket.App;
import lk.ijse.mlsupermarket.bo.BOFactory;
import lk.ijse.mlsupermarket.bo.custom.InventoryBO;
import lk.ijse.mlsupermarket.dto.InventoryDTO;
import lk.ijse.mlsupermarket.model.InventoryModel;
import lk.ijse.mlsupermarket.model.SalesModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryController {

    @FXML
    private TableView<InventoryDTO> tblInventory;
    @FXML
    private TableColumn<InventoryDTO, String> colProductId;
    @FXML
    private TableColumn<InventoryDTO, String> colName;
    @FXML
    private TableColumn<InventoryDTO, String> colCategory;
    @FXML
    private TableColumn<InventoryDTO, Integer> colQty;
    @FXML
    private TableColumn<InventoryDTO, Integer> colReorder;
    @FXML
    private TableColumn<InventoryDTO, String> colStatus;
    @FXML
    private ListView<String> lvAlerts;
    @FXML
    private TextField txtReturnProductId;
    @FXML
    private TextField txtReturnQty;
    @FXML
    private ComboBox<String> cmbFilterCategory;

    @FXML
    private ComboBox<String> cmbSaleId;

    private final SalesModel salesModel = new SalesModel();

    private final InventoryBO inventoryBO =
            (InventoryBO) BOFactory.getInstance()
                    .getBO(BOFactory.BOTypes.INVENTORY);


    @FXML
    public void initialize() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colReorder.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        cmbFilterCategory.getItems().addAll("Snacks","Drinks","Dairy","Fruits","Vegetables", "Bakery","Frozen Foods","Household","Personal Care", "Baby Products","Meat & Fish","Spices & Condiments", "Grains & Rice","Canned Foods","Health & Wellness","Beverages");

        loadInventory();

        loadSaleIds();



    }



    private void loadInventory() {
        try {
            List<InventoryDTO> list = inventoryBO.getAllInventory();
            tblInventory.setItems(FXCollections.observableArrayList(list));
            loadAlerts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSaleIds() {
        try {
            List<String> saleIds = salesModel.getAllSaleIds();
            cmbSaleId.getItems().clear();
            cmbSaleId.getItems().addAll(saleIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadAlerts() {
        lvAlerts.getItems().clear();
        for (InventoryDTO item : tblInventory.getItems()) {
            if ("LOW STOCK".equals(item.getStatus())) {
                lvAlerts.getItems().add("LOW STOCK: " + item.getName() + " (" + item.getQty() + ")");
            } else if ("OUT OF STOCK".equals(item.getStatus())) {
                lvAlerts.getItems().add("OUT OF STOCK: " + item.getName());
            }
        }
    }

    @FXML
    private void handleProcessReturn() {

        String saleId = cmbSaleId.getValue();
        if (saleId == null) {
            new Alert(Alert.AlertType.WARNING, "Select Sale ID").show();
            return;
        }

        if (txtReturnProductId.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Enter Product ID!").show();
            txtReturnProductId.requestFocus();
            return;
        }

        if (txtReturnQty.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Enter Return Quantity!").show();
            txtReturnQty.requestFocus();
            return;
        }

        try {
            String productId = txtReturnProductId.getText().trim();
            int qty = Integer.parseInt(txtReturnQty.getText().trim());

            double unitPrice = salesModel.getUnitPrice(saleId, productId);

            boolean returned =
                    salesModel.returnSaleItem(saleId, productId, qty, unitPrice);

            if (returned) {
                new Alert(Alert.AlertType.INFORMATION, "Return processed!").show();
                loadInventory();
            } else {
                new Alert(Alert.AlertType.ERROR, "Return failed!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Return Quantity must be a number!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }





    @FXML
    private void handleFilterCategory() {
        try {
            String selected = cmbFilterCategory.getValue();
            List<InventoryDTO> list = (selected == null) ?
                    inventoryBO.getAllInventory() : inventoryBO.getInventoryByCategory(selected);
            tblInventory.setItems(FXCollections.observableArrayList(list));
            loadAlerts();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Filter failed!").show();
        }
    }

    @FXML
    private void handleRefresh() {
        cmbSaleId.setValue(null);
        cmbFilterCategory.getSelectionModel().clearSelection();
        txtReturnProductId.clear();
        txtReturnQty.clear();
        loadInventory();
    }



    @FXML
    void handlePrintReport(ActionEvent event) {
        try {
            inventoryBO.printStockReport();
        } catch (SQLException | JRException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    private void handleDashboard() {
        try {
            App.setRoot("Dashboard");
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Cannot open Dashboard!").show();
        }
    }


}