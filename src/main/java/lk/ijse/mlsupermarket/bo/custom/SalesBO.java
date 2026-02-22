package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.entity.Sales;

import java.util.List;

public interface SalesBO extends SuperBO {

    boolean placeSale(SaleItemDTO sale, List<SaleItemDTO> items) throws Exception;

    boolean returnSaleItem(String saleId, String productId, int qty) throws Exception;

    String generateNextSaleId() throws Exception;

    List<String> getAllSaleIds() throws Exception;

    List<SaleItemDTO> getAllSaleItems() throws Exception;
}
