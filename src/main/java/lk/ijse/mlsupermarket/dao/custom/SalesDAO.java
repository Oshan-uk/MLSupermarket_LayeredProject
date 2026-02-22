package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.dao.SuperDAO;

import java.sql.SQLException;
import java.util.List;

public interface SalesDAO extends SuperDAO {

    boolean saveSale(SalesDTO sale, List<SaleItemDTO> items) throws SQLException;

    boolean returnSaleItem(String saleId, String productId,
                           int returnQty, double unitPrice) throws SQLException;

    List<SaleItemDTO> getAllSalesItems() throws Exception;

    void printStockReport() throws Exception;

    String generateNextSaleId() throws Exception;

    double getUnitPrice(String saleId, String productId) throws SQLException;

    List<String> getAllSaleIds() throws SQLException;
}