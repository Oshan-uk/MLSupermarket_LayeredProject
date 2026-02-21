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

    public boolean save(Customer entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO customer (customer_id, name, contact_no) VALUES (?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getContactNo()
        );
    }

    public boolean update(Customer entity) throws SQLException {
        return CrudUtil.execute("UPDATE customer SET name=?, contact_no=? WHERE customer_id=?",
                entity.getName(),
                entity.getContactNo(),
                entity.getId()
        );
    }

    public boolean delete(String id) throws SQLException {

        return CrudUtil.execute("DELETE FROM customer WHERE customer_id=?", id);

    }

    public Customer search(String id) throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM customer WHERE customer_id=?", id);

        if (rs.next()) {
            return new Customer(
                    rs.getString("customer_id"),
                    rs.getString("name"),
                    rs.getString("contact_no")
            );
        }
        return null;
    }

    public ArrayList<Customer> getAll() throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM customer ORDER BY CAST(SUBSTRING(customer_id, 2) AS UNSIGNED) DESC");
        ArrayList<Customer> customers = new ArrayList<>();

        while (rs.next()) {
            String id = rs.getString("customer_id");
            String name = rs.getString("name");
            String contactNo = rs.getString("contact_no");

            Customer entity = new Customer(id, name, contactNo);
            customers.add(entity);

        }

        return customers;
    }

    public String generateId() throws Exception {

        ResultSet rst =  CrudUtil.execute("SELECT customer_id FROM customer ORDER BY CAST(SUBSTRING(customer_id, 2) AS UNSIGNED) DESC LIMIT 1");


        if (rst.next()) {
            String lastId = rst.getString("customer_id");
            int num = Integer.parseInt(lastId.substring(1));
            return "C" + (num + 1);
        }

        return "C1";
    }
}
