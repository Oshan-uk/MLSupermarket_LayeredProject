package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.bo.SuperBO;

import java.util.List;

public interface SalesBO extends SuperBO {

    boolean saveSale(SalesDTO sale, List<SaleItemDTO> items) throws Exception;

    boolean returnSaleItem(String saleId, String productId,
                           int returnQty, double unitPrice) throws Exception;

    List<SaleItemDTO> getAllSalesItems() throws Exception;

    void printStockReport() throws Exception;

    String generateNextSaleId() throws Exception;

    double getUnitPrice(String saleId, String productId) throws Exception;

    List<String> getAllSaleIds() throws Exception;
}