package lk.ijse.mlsupermarket.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import lk.ijse.mlsupermarket.App;

import java.io.IOException;

public class DashboardController {

    private boolean hasAccess(String... allowedRoles) {
        if (App.loggedUserRole == null) {
            new Alert(Alert.AlertType.ERROR, "Please login again").show();
            return false;
        }
        for (String role : allowedRoles) {
            if (App.loggedUserRole.equals(role)) {
                return true;
            }
        }
        new Alert(Alert.AlertType.ERROR, "Access Denied").show();
        return false;
    }

    @FXML
    private void openCustomer() throws IOException {
        if (!hasAccess("Owner", "Manager")) return;
        App.setRoot("CustomerView");
    }

    @FXML
    private void openSupplier() throws IOException {
        if (!hasAccess("Owner", "Manager")) return;
        App.setRoot("Supplier");
    }

    @FXML
    private void openProduct() throws IOException {
        if (!hasAccess("Owner", "Stock Keeper")) return;
        App.setRoot("Product");
    }

    @FXML
    private void openInventory() throws IOException {
        if (!hasAccess("Owner", "Stock Keeper")) return;
        App.setRoot("Inventory");
    }

    @FXML
    private void openSales() throws IOException {
        if (!hasAccess("Owner", "Manager", "Cashier")) return;
        App.setRoot("Sales");
    }

    @FXML
    private void logout() throws IOException {
        App.loggedUserRole = null;
        App.loggedUsername = null;
        App.setRoot("Login");
    }
}
