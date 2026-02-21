package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dao.CrudDAO;
import lk.ijse.mlsupermarket.dao.SuperDAO;
import lk.ijse.mlsupermarket.entity.Inventory;

import java.util.ArrayList;

public interface InventoryDAO extends CrudDAO<Inventory> {

    ArrayList<Inventory> getByCategory(String category) throws Exception;
}
