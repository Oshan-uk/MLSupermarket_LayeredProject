package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;
import lk.ijse.mlsupermarket.dao.custom.InventoryDAO;
import lk.ijse.mlsupermarket.dto.InventoryDTO;
import lk.ijse.mlsupermarket.entity.Inventory;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface InventoryBO extends SuperBO {

    List<InventoryDTO> getAllInventory() throws Exception;

    List<InventoryDTO> getInventoryByCategory(String category) throws Exception;

    void printStockReport() throws Exception;
}
