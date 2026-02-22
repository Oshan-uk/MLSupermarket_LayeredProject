package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dao.CrudDAO;
import lk.ijse.mlsupermarket.entity.Supplier;

public interface SupplierDAO extends CrudDAO<Supplier> {

    String generateNextId() throws Exception;
}