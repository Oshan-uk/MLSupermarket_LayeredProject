package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;
import lk.ijse.mlsupermarket.dao.custom.InventoryDAO;
import lk.ijse.mlsupermarket.entity.Inventory;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryBO extends SuperBO {

    ArrayList<Inventory> getAllInventory() throws Exception;

    ArrayList<Inventory> getInventoryByCategory(String category) throws Exception;

    void printStockReport() throws SQLException, JRException;

    String calculateStatus(int qty, int reorder);
}
