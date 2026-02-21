package lk.ijse.mlsupermarket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.mlsupermarket.App;
import lk.ijse.mlsupermarket.dto.*;
import lk.ijse.mlsupermarket.model.CustomerModel;
import lk.ijse.mlsupermarket.model.ProductModel;
import lk.ijse.mlsupermarket.model.SalesModel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesController {

    @FXML private TextField txtCustomerId;
    @FXML private TextField txtCustomerName;
    @FXML private TextField txtCustomerContact;
    @FXML private TextField txtProductId;
    @FXML private TextField txtUnitPrice;
    @FXML private TextField txtAvailable;
    @FXML private TextField txtQty;
    @FXML private TextField saleId;
    @FXML private TextField date;
    @FXML private TextField grandTotal;

    @FXML private ComboBox<ProductDTO> cmbProduct;

    @FXML private TableView<SaleItemDTO> tblItems;
    @FXML private TableColumn<SaleItemDTO, String> colPid;
    @FXML private TableColumn<SaleItemDTO, String> colPName;
    @FXML private TableColumn<SaleItemDTO, Integer> colQty;
    @FXML private TableColumn<SaleItemDTO, Double> colUnit;
    @FXML private TableColumn<SaleItemDTO, Double> colLineTotal;
    @FXML private TableColumn<SaleItemDTO, Button> colRemove;

    @FXML private TableView<SaleItemDTO> tblSalesView;
    @FXML private TableColumn<SaleItemDTO, String> colSVSaleId;
    @FXML private TableColumn<SaleItemDTO, String> colSVProductId;
    @FXML private TableColumn<SaleItemDTO, Integer> colSVQty;
    @FXML private TableColumn<SaleItemDTO, Double> colSVUnitPrice;
    @FXML private TableColumn<SaleItemDTO, Double> colSVTotal;

    private final ProductModel productModel = new ProductModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final SalesModel salesModel = new SalesModel();

    private final ObservableList<SaleItemDTO> cartList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colPid.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colPName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colLineTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
        tblItems.setItems(cartList);

        colSVSaleId.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        colSVProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colSVQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSVUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colSVTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        date.setText(LocalDate.now().toString());

        try {
            saleId.setText(salesModel.generateNextSaleId());
            cmbProduct.setItems(FXCollections.observableArrayList(productModel.getProducts()));
            loadSalesViewTable();
            refreshSalesTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cmbProduct.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ProductDTO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        cmbProduct.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ProductDTO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        cmbProduct.setOnAction(e -> {
            ProductDTO p = cmbProduct.getValue();
            if (p != null) {
                txtProductId.setText(p.getId());
                txtUnitPrice.setText(String.valueOf(p.getPrice()));
                txtAvailable.setText(String.valueOf(p.getQty()));
            } else {
                clearProductFields();
            }
        });
    }

    @FXML
    private void handleAddItem() {
        try {
            if (txtQty.getText().isEmpty()) return;

            int qty = Integer.parseInt(txtQty.getText());
            ProductDTO p = productModel.searchProduct(txtProductId.getText());

            if (p == null) {
                new Alert(Alert.AlertType.ERROR, "Product not found!").show();
                return;
            }

            if (qty <= 0) {
                new Alert(Alert.AlertType.ERROR, "Quantity must be greater than 0!").show();
                return;
            }

            if (qty > p.getQty()) {
                new Alert(Alert.AlertType.ERROR, "Insufficient stock! Available: " + p.getQty()).show();
                return;
            }

            double total = p.getPrice() * qty;
            Button btn = new Button("Remove");

            SaleItemDTO item = new SaleItemDTO(p.getId(), p.getName(), qty, p.getPrice(), total, btn);

            btn.setOnAction(e -> {
                cartList.remove(item);
                updateTotal();
            });

            cartList.add(item);
            updateTotal();
            clearProductFields();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }


    @FXML
    private void handleConfirmSale() {
        try {
            if (cartList.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Cart is empty!").show();
                return;
            }

            SalesDTO sale = new SalesDTO(
                    saleId.getText(),
                    Double.parseDouble(grandTotal.getText()),
                    date.getText(),
                    txtCustomerId.getText().isEmpty() ? null : txtCustomerId.getText()
            );

            List<SaleItemDTO> items = new ArrayList<>(cartList);

            if (salesModel.saveSale(sale, items)) {
                new Alert(Alert.AlertType.INFORMATION, "Sale successfully completed!").show();

                cartList.clear();
                updateTotal();
                saleId.setText(salesModel.generateNextSaleId());
                loadSalesViewTable();
                clearAllFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to complete sale!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }


    private void updateTotal() {
        double total = 0;
        for (SaleItemDTO i : cartList) total += i.getTotal();
        grandTotal.setText(String.format("%.2f", total));
    }

    private void clearProductFields() {
        txtProductId.clear();
        cmbProduct.getSelectionModel().clearSelection();
        txtUnitPrice.clear();
        txtAvailable.clear();
        txtQty.clear();
    }

    private void clearAllFields() {
        clearProductFields();
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerContact.clear();
    }

    @FXML
    private void handleCartItemClick() {
        SaleItemDTO item = tblItems.getSelectionModel().getSelectedItem();
        if (item == null) return;

        txtProductId.setText(item.getProductId());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQty.setText(String.valueOf(item.getQuantity()));
    }

    private void loadSalesViewTable() {
        try {
            tblSalesView.setItems(FXCollections.observableArrayList(salesModel.getAllSalesItems()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshSalesTable() {

        loadSalesViewTable();
    }

    @FXML
    private void handleCustomerSearch(KeyEvent event) {
        if (event.getCode() != KeyCode.ENTER) return;

        try {
            String id = txtCustomerId.getText().trim();

            if (id.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Enter customer ID").show();
                return;
            }

            CustomerDTO customer = customerModel.searchCustomer(id);

            if (customer != null) {
                txtCustomerName.setText(customer.getName());
                txtCustomerContact.setText(customer.getContactNo());
            } else {
                txtCustomerName.clear();
                txtCustomerContact.clear();
                new Alert(Alert.AlertType.ERROR, "Customer not found").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error").show();
        }
    }

    @FXML
    private void handleProductSearch(KeyEvent event) {
        if (event.getCode() != KeyCode.ENTER) return;

        String id = txtProductId.getText().trim();

        if (id.isEmpty()) {
            cmbProduct.getSelectionModel().clearSelection();
            txtUnitPrice.clear();
            txtAvailable.clear();
            new Alert(Alert.AlertType.WARNING, "Enter product ID").show();
            return;
        }

        try {
            ProductDTO product = productModel.searchProduct(id);

            if (product != null) {
                cmbProduct.getSelectionModel().select(product);
                txtUnitPrice.setText(String.valueOf(product.getPrice()));
                txtAvailable.setText(String.valueOf(product.getQty()));
            } else {
                cmbProduct.getSelectionModel().clearSelection();
                txtUnitPrice.clear();
                txtAvailable.clear();
                new Alert(Alert.AlertType.ERROR, "Product not found").show();
            }

        } catch (Exception e) {
            cmbProduct.getSelectionModel().clearSelection();
            txtUnitPrice.clear();
            txtAvailable.clear();
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }

    @FXML
    private void handleReset() {
        refreshSalesTable();
        clearAllFields();
        cartList.clear();
        updateTotal();
        tblItems.getItems().clear();
    }

    @FXML
    private void openCustomer() throws IOException {
        App.setRoot("CustomerView");
    }

    @FXML
    void handlePrintReport(javafx.event.ActionEvent event) {
        try {
            salesModel.printStockReport();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate report: " + e.getMessage()).show();
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
}
