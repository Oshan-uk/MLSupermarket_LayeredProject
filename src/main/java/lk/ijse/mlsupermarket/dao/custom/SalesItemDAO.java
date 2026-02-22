package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dao.CrudDAO;
import lk.ijse.mlsupermarket.entity.SaleItem;

import java.util.ArrayList;


public interface SalesItemDAO extends CrudDAO<SaleItem> {

    boolean save(SaleItem entity) throws Exception;

    boolean reduceQuantity(String saleId, String productId, int qty) throws Exception;

    boolean deleteIfZero(String saleId, String productId) throws Exception;

    ArrayList<SaleItem> getAll() throws Exception;

    double getUnitPrice(String saleId, String productId) throws Exception;


}

