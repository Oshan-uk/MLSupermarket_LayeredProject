package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dao.CrudDAO;
import lk.ijse.mlsupermarket.entity.Sales;

import java.util.List;

public interface SalesDAO extends CrudDAO<Sales> {

    String generateNextId() throws Exception;
}