package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.InventoryBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.CustomerDAO;
import lk.ijse.mlsupermarket.dao.custom.InventoryDAO;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.entity.Inventory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBO {

    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY);


    @Override
    public ArrayList<Inventory> getAllInventory() throws Exception {

        return inventoryDAO.getAll();
    }

    @Override
    public ArrayList<Inventory> getInventoryByCategory(String category) throws Exception {
        return inventoryDAO.getByCategory(category);
    }

    @Override
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

    @Override
    public String calculateStatus(int qty, int reorder) {
        if (qty == 0) return "OUT OF STOCK";
        if (qty <= reorder) return "LOW STOCK";
        return "IN STOCK";
    }
}
