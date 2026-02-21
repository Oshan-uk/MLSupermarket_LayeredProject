package lk.ijse.mlsupermarket.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import lk.ijse.mlsupermarket.App;
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
            Connection conn = DBConnection.getInstance().getConnection();

            String sqlUser = "SELECT * FROM user WHERE username=?";
            PreparedStatement psUser = conn.prepareStatement(sqlUser);
            psUser.setString(1, username);
            ResultSet rsUser = psUser.executeQuery();

            if (!rsUser.next()) {
                new Alert(Alert.AlertType.ERROR, "Username does not exist!").show();
                return;
            }

            String sqlPass = "SELECT * FROM user WHERE username=? AND password=MD5(?)";
            PreparedStatement psPass = conn.prepareStatement(sqlPass);
            psPass.setString(1, username);
            psPass.setString(2, password);
            ResultSet rsPass = psPass.executeQuery();

            if (!rsPass.next()) {
                new Alert(Alert.AlertType.ERROR, "Incorrect password!").show();
                return;
            }

            String sqlRole = "SELECT * FROM user WHERE username=? AND password=MD5(?) AND role=?";
            PreparedStatement psRole = conn.prepareStatement(sqlRole);
            psRole.setString(1, username);
            psRole.setString(2, password);
            psRole.setString(3, role);
            ResultSet rs = psRole.executeQuery();

            if (!rs.next()) {
                new Alert(Alert.AlertType.ERROR, "Incorrect role selected!").show();
                return;
            }

            App.loggedUsername = username;
            App.loggedUserRole = role;

            new Alert(Alert.AlertType.INFORMATION, "Login Successful!").show();
            App.setRoot("dashboard");

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }


}
