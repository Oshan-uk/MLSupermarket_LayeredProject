module lk.ijse.mlsupermarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.desktop;
    requires net.sf.jasperreports.core;
    requires lk.ijse.mlsupermarket;


    opens lk.ijse.mlsupermarket.controller to javafx.fxml;
    opens lk.ijse.mlsupermarket.dto to java.base;
    opens lk.ijse.mlsupermarket.reports to net.sf.jasperreports.core;
    exports lk.ijse.mlsupermarket;

    exports lk.ijse.mlsupermarket.controller;
    exports lk.ijse.mlsupermarket.dto;
}