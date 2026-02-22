package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dao.CrudDAO;
import lk.ijse.mlsupermarket.dao.SuperDAO;
import lk.ijse.mlsupermarket.entity.Inventory;
import lk.ijse.mlsupermarket.entity.Product;

import java.util.ArrayList;
import java.util.List;

public interface InventoryDAO extends SuperDAO {

    List<Product> getAllProducts() throws Exception;

    List<Product> getProductsByCategory(String category) throws Exception;

}
