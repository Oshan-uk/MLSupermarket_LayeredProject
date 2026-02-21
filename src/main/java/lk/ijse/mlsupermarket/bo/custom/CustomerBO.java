package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;
import lk.ijse.mlsupermarket.dao.SuperDAO;
import lk.ijse.mlsupermarket.entity.Customer;

import java.util.ArrayList;

public interface CustomerBO  extends SuperBO {


    String generateId() throws Exception;
    boolean save(Customer customer) throws Exception;
    Customer search(String id) throws Exception;
    boolean update(Customer customer) throws Exception;
    boolean delete(String id) throws Exception;
    ArrayList<Customer> getAll() throws Exception;

}
