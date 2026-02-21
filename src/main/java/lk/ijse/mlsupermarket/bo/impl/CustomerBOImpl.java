package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.CustomerBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.CustomerDAO;
import lk.ijse.mlsupermarket.entity.Customer;

import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);


    @Override
    public String generateId() throws Exception {
        return customerDAO.generateId();
    }

    @Override
    public ArrayList<Customer> getAll() throws Exception {
        return customerDAO.getAll();
    }

    @Override
    public boolean save(Customer customer) throws Exception {
        return customerDAO.save(customer);
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        return customerDAO.update(customer);
    }

    @Override
    public boolean delete(String id) throws Exception {
        return customerDAO.delete(id);
    }

    @Override
    public Customer search(String id) throws Exception {
        return customerDAO.search(id);
    }
}
