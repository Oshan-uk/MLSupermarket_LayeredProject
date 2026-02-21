package lk.ijse.mlsupermarket.model;

import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.InventoryDTO;
import lk.ijse.mlsupermarket.dao.CrudUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryModel {



    public List<InventoryDTO> getAllInventory() throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT product_id, name, category, qty, reorder_level FROM product ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC");

        List<InventoryDTO> list = new ArrayList<>();

        while (rs.next()) {

            int qty = rs.getInt("qty");
            int reorder = rs.getInt("reorder_level");
            String status = calculateStatus(qty, reorder);

            list.add(new InventoryDTO(rs.getString("product_id"), rs.getString("name"), rs.getString("category"), qty, reorder, status));
        }

        return list;
    }


    public List<InventoryDTO> getInventoryByCategory(String category) throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT product_id, name, category, qty, reorder_level FROM product WHERE category=? ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC", category);

        List<InventoryDTO> list = new ArrayList<>();

        while (rs.next()) {

            int qty = rs.getInt("qty");
            int reorder = rs.getInt("reorder_level");
            String status = calculateStatus(qty, reorder);

            list.add(new InventoryDTO(
                    rs.getString("product_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    qty,
                    reorder,
                    status
            ));
        }

        return list;
    }



    private String calculateStatus(int qty, int reorder) {
        if (qty == 0) return "OUT OF STOCK";
        if (qty <= reorder) return "LOW STOCK";
        return "IN STOCK";
    }


    public void printStockReport() throws SQLException, JRException {

        Connection conn = DBConnection.getInstance().getConnection();
        String reportPath = "/lk/ijse/mlsupermarket/reports/inventory_report.jasper";
        InputStream inputStream = getClass().getResourceAsStream(reportPath);

        if (inputStream == null) {
            throw new JRException("Compiled report file not found!");
        }

        try {
            JasperPrint jp = JasperFillManager.fillReport(inputStream, null, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("ML Supermarket - Inventory Stock Report");
            viewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
            throw e;
        }
    }

}