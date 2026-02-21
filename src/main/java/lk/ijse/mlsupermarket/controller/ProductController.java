package lk.ijse.mlsupermarket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.mlsupermarket.App;
import lk.ijse.mlsupermarket.dto.CustomerDTO;
import lk.ijse.mlsupermarket.dto.ProductDTO;
import lk.ijse.mlsupermarket.model.ProductModel;

import java.util.List;

public class ProductController {

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtProductName;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtReorderLevel;

    @FXML
    private TextField txtPrice;

    @FXML
    private TableView<ProductDTO> tblProducts;

    @FXML
    private TableColumn<ProductDTO, String> colProductId;

    @FXML
    private TableColumn<ProductDTO, String> colSupplierId;

    @FXML
    private TableColumn<ProductDTO, String> colProductName;

    @FXML
    private TableColumn<ProductDTO, String> colCategory;

    @FXML
    private TableColumn<ProductDTO, Integer> colQty;

    @FXML
    private TableColumn<ProductDTO, Integer> colReorderLevel;

    @FXML
    private TableColumn<ProductDTO, Double> colPrice;

    private final ProductModel productModel = new ProductModel();

    @FXML
    public void initialize() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colReorderLevel.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        cmbCategory.getItems().addAll("Snacks", "Drinks", "Dairy", "Fruits", "Vegetables", "Bakery", "Frozen Foods", "Household", "Personal Care", "Baby Products", "Meat & Fish", "Spices & Condiments", "Grains & Rice", "Canned Foods", "Health & Wellness", "Beverages");


        loadProductTable();

        try {
            txtProductId.setText(productModel.generateNextProductId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @FXML
    private void handleAddProduct() {

        try {

            ProductDTO product = new ProductDTO(txtProductId.getText().trim(), txtSupplierId.getText().trim(), txtProductName.getText().trim(), cmbCategory.getValue(), Integer.parseInt(txtQty.getText().trim()), Integer.parseInt(txtReorderLevel.getText().trim()), Double.parseDouble(txtPrice.getText().trim()));

            boolean result = productModel.saveProduct(product);

            if (result) {

                new Alert(Alert.AlertType.INFORMATION, "Product added successfully!").show();

                cleanFields();
                loadProductTable();
                txtProductId.setText(productModel.generateNextProductId());


            } else {

                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

        } catch (Exception e) {

            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
        }
    }

    @FXML
    private void handleUpdateProduct() {

        try {

            ProductDTO product = new ProductDTO(txtProductId.getText().trim(), txtProductName.getText().trim(), cmbCategory.getValue(), Integer.parseInt(txtQty.getText().trim()), Integer.parseInt(txtReorderLevel.getText().trim()), Double.parseDouble(txtPrice.getText().trim()), txtSupplierId.getText().trim());

            boolean result = productModel.updateProduct(product);

            if (result) {

                new Alert(Alert.AlertType.INFORMATION, "Product updated successfully!").show();

                cleanFields();
                loadProductTable();

                try {
                    txtProductId.setText(productModel.generateNextProductId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();

            }

        } catch (Exception e) {

            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
        }
    }

    @FXML
    private void handleDeleteProduct() {

        try {

            String id = txtProductId.getText().trim();

            boolean result = productModel.deleteProduct(id);

            if (result) {

                new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully!").show();

                cleanFields();
                loadProductTable();

                try {
                    txtProductId.setText(productModel.generateNextProductId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

        } catch (Exception e) {

            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot delete!").show();
        }
    }

    @FXML
    private void handleReset() {

        cleanFields();

        try {
            txtProductId.setText(productModel.generateNextProductId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cleanFields() {

        txtProductId.setText("");
        txtProductName.setText("");
        txtSupplierId.setText("");
        cmbCategory.getSelectionModel().clearSelection();
        txtQty.setText("");
        txtReorderLevel.setText("");
        txtPrice.setText("");
    }

    private void loadProductTable() {
        try {

            List<ProductDTO> productList = productModel.getProducts();

            ObservableList<ProductDTO> obList = FXCollections.observableArrayList();

            obList.addAll(productList);

            tblProducts.setItems(obList);

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
    private void handleProductTableClick() {

        ProductDTO selectedProduct =
                tblProducts.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            return;
        }

        txtProductId.setText(selectedProduct.getId());
        txtSupplierId.setText(selectedProduct.getSupplierId());
        txtProductName.setText(selectedProduct.getName());
        cmbCategory.setValue(selectedProduct.getCategory());
        txtQty.setText(String.valueOf(selectedProduct.getQty()));
        txtReorderLevel.setText(String.valueOf(selectedProduct.getReorderLevel()));
        txtPrice.setText(String.valueOf(selectedProduct.getPrice()));
    }

}
