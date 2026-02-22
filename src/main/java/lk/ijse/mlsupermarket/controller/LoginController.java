package lk.ijse.mlsupermarket.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import lk.ijse.mlsupermarket.App;
import lk.ijse.mlsupermarket.bo.BOFactory;
import lk.ijse.mlsupermarket.bo.custom.UserBO;
import lk.ijse.mlsupermarket.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleCombo;

    private final UserBO userBO =
            (UserBO) BOFactory.getInstance()
                    .getBO(BOFactory.BOTypes.USER);

    @FXML
    public void initialize() {

        roleCombo.getItems().addAll("Owner", "Cashier", "Manager", "Stock Keeper");
    }

    @FXML
    private void login() throws IOException {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleCombo.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Username and Password are required!").show();
            return;
        }

        if (role == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a role!").show();
            return;
        }

        try {

            int result = userBO.authenticate(username, password, role);

            switch (result) {
                case 1:
                    new Alert(Alert.AlertType.ERROR, "Username does not exist!").show();
                    return;
                case 2:
                    new Alert(Alert.AlertType.ERROR, "Incorrect password!").show();
                    return;
                case 3:
                    new Alert(Alert.AlertType.ERROR, "Incorrect role selected!").show();
                    return;
                case 4:
                    App.loggedUsername = username;
                    App.loggedUserRole = role;
                    new Alert(Alert.AlertType.INFORMATION, "Login Successful!").show();
                    App.setRoot("dashboard");
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }


}
