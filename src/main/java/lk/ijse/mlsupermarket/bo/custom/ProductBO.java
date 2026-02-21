package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;
import lk.ijse.mlsupermarket.entity.Customer;
import lk.ijse.mlsupermarket.entity.Product;

import java.util.ArrayList;

public interface ProductBO extends SuperBO {

    String generateId() throws Exception;
    boolean save(Product product) throws Exception;
    Product search(String id) throws Exception;
    boolean update(Product product) throws Exception;
    boolean delete(String id) throws Exception;
    ArrayList<Product> getAll() throws Exception;
}
