package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;
import lk.ijse.mlsupermarket.dao.SuperDAO;
import lk.ijse.mlsupermarket.dto.CustomerDTO;
import lk.ijse.mlsupermarket.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public interface CustomerBO  extends SuperBO {


    boolean saveCustomer(CustomerDTO dto) throws Exception;

    boolean updateCustomer(CustomerDTO dto) throws Exception;

    boolean deleteCustomer(String id) throws Exception;

    CustomerDTO searchCustomer(String id) throws Exception;

    List<CustomerDTO> getAllCustomers() throws Exception;

    String generateNextCustomerId() throws Exception;

}
