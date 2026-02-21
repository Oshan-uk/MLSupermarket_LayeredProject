package lk.ijse.mlsupermarket.dao.impl;

import lk.ijse.mlsupermarket.dao.CrudUtil;
import lk.ijse.mlsupermarket.dao.custom.InventoryDAO;
import lk.ijse.mlsupermarket.dto.InventoryDTO;
import lk.ijse.mlsupermarket.entity.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {


    @Override
    public boolean save(Inventory entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Inventory entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public String generateId() throws Exception {
        return "";
    }

    @Override
    public Inventory search(String id) throws Exception {
        return null;
    }


    public ArrayList<Inventory> getAll() throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT product_id, name, category, qty, reorder_level FROM product ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC");
        ArrayList<Inventory> inventory = new ArrayList<>();

        while (rs.next()) {

            String id = rs.getString("product_id");
            String name = rs.getString("name");
            String category = rs.getString("category");
            int qty = Integer.parseInt(rs.getString("qty"));
            int reorder_level = Integer.parseInt(rs.getString("reorder_level"));


            inventory.add(new Inventory(id, name, category, qty, reorder_level));

        }

        return inventory;
    }


    @Override
    public ArrayList<Inventory> getByCategory(String category) throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT product_id, name, category, qty, reorder_level FROM product WHERE category=? ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC",
                category
        );

        ArrayList<Inventory> inventory = new ArrayList<>();

        while (rs.next()) {

            String id = rs.getString("product_id");
            String name = rs.getString("name");
            String cg = rs.getString("category");
            int qty = Integer.parseInt(rs.getString("qty"));
            int reorder_level = Integer.parseInt(rs.getString("reorder_level"));

            inventory.add(new Inventory(id, name, cg, qty, reorder_level));

        }
        return inventory;


    }



}
