package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.entity.Sales;

import java.util.List;

public interface SalesBO extends SuperBO {

    boolean saveSale(SalesDTO saleDTO, List<SaleItemDTO> items) throws Exception;

    String generateNextSaleId() throws Exception;

    List<SaleItemDTO> getAllSalesItems() throws Exception;

    void printStockReport() throws Exception;
}
