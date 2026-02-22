package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.CustomerBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.CustomerDAO;
import lk.ijse.mlsupermarket.dto.CustomerDTO;
import lk.ijse.mlsupermarket.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO =
            (CustomerDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws Exception {
        return customerDAO.save(
                new Customer(dto.getId(), dto.getName(), dto.getContactNo())
        );
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws Exception {
        return customerDAO.update(
                new Customer(dto.getId(), dto.getName(), dto.getContactNo())
        );
    }

    @Override
    public boolean deleteCustomer(String id) throws Exception {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws Exception {
        Customer customer = customerDAO.search(id);

        if (customer == null) return null;

        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getContactNo()
        );
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws Exception {
        List<Customer> entityList = customerDAO.getAll();
        List<CustomerDTO> dtoList = new ArrayList<>();

        for (Customer customer : entityList) {
            dtoList.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getContactNo()
            ));
        }

        return dtoList;
    }

    @Override
    public String generateNextCustomerId() throws Exception {
        return customerDAO.generateNextId();
    }
}