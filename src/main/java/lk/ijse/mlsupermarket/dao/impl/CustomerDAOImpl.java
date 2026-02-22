package lk.ijse.mlsupermarket.dao.impl;

import lk.ijse.mlsupermarket.dao.CrudUtil;
import lk.ijse.mlsupermarket.dao.custom.CustomerDAO;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.CustomerDTO;
import lk.ijse.mlsupermarket.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean save(Customer customer) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO customer VALUES (?,?,?)",
                customer.getId(),
                customer.getName(),
                customer.getContactNo()
        );
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        return CrudUtil.execute(
                "UPDATE customer SET name=?, contact_no=? WHERE customer_id=?",
                customer.getName(),
                customer.getContactNo(),
                customer.getId()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.execute(
                "DELETE FROM customer WHERE customer_id=?",
                id
        );
    }

    @Override
    public Customer search(String id) throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM customer WHERE customer_id=?",
                id
        );

        if (rs.next()) {
            return new Customer(
                    rs.getString("customer_id"),
                    rs.getString("name"),
                    rs.getString("contact_no")
            );
        }
        return null;
    }

    @Override
    public List<Customer> getAll() throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM customer ORDER BY CAST(SUBSTRING(customer_id, 2) AS UNSIGNED) DESC"
        );

        List<Customer> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Customer(
                    rs.getString("customer_id"),
                    rs.getString("name"),
                    rs.getString("contact_no")
            ));
        }

        return list;
    }

    @Override
    public String generateNextId() throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT customer_id FROM customer ORDER BY CAST(SUBSTRING(customer_id, 2) AS UNSIGNED) DESC LIMIT 1"
        );

        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.substring(1));
            return "C" + (num + 1);
        }

        return "C1";
    }
}