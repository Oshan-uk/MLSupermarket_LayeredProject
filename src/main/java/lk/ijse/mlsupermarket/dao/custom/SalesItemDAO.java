package lk.ijse.mlsupermarket.dao.custom;


import lk.ijse.mlsupermarket.dao.SuperDAO;
import lk.ijse.mlsupermarket.entity.SaleItem;


import java.util.List;


public interface SalesItemDAO extends SuperDAO {

    boolean save(SaleItem item) throws Exception;

    boolean updateQuantity(String saleId, String productId, int qty) throws Exception;

    boolean deleteIfZero(String saleId, String productId) throws Exception;

    List<SaleItem> getAll() throws Exception;


}

