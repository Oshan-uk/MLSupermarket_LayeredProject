package lk.ijse.mlsupermarket.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.CustomerDTO;
import lk.ijse.mlsupermarket.dao.CrudUtil;

public class CustomerModel {


    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {

        return CrudUtil.execute("INSERT INTO customer (customer_id, name, contact_no) VALUES (?,?,?)", customerDTO.getId(), customerDTO.getName(), customerDTO.getContactNo());

    }


    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {

        return CrudUtil.execute("UPDATE customer SET name=?, contact_no=? WHERE customer_id=?", customerDTO.getName(), customerDTO.getContactNo(), customerDTO.getId());

    }


    public boolean deleteCustomer(String id) throws SQLException {

        return CrudUtil.execute("DELETE FROM customer WHERE customer_id=?", id);

    }


    public CustomerDTO searchCustomer(String id) throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM customer WHERE customer_id=?", id);

        if (rs.next()) {

            return new CustomerDTO(rs.getString("customer_id"), rs.getString("name"), rs.getString("contact_no"));

        }

        return null;
    }



    public List<CustomerDTO> getCustomers() throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM customer ORDER BY CAST(SUBSTRING(customer_id, 2) AS UNSIGNED) DESC");

        List<CustomerDTO> customerList = new ArrayList<>();

        while (rs.next()) {
            customerList.add(
                    new CustomerDTO(
                            rs.getString("customer_id"),
                            rs.getString("name"),
                            rs.getString("contact_no")
                    )
            );
        }

        return customerList;
    }



    public String generateNextCustomerId() throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT customer_id FROM customer ORDER BY CAST(SUBSTRING(customer_id, 2) AS UNSIGNED) DESC LIMIT 1";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String lastId = rs.getString("customer_id");
            int num = Integer.parseInt(lastId.substring(1));
            return "C" + (num + 1);
        }

        return "C1";
    }


}
