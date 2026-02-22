package lk.ijse.mlsupermarket.dao.impl;

import lk.ijse.mlsupermarket.dao.custom.InventoryDAO;
import lk.ijse.mlsupermarket.entity.Product;
import lk.ijse.mlsupermarket.dao.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {

    @Override
    public List<Product> getAllProducts() throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT product_id, name, category, qty, reorder_level " +
                        "FROM product ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC"
        );

        List<Product> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Product(
                    rs.getString("product_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("qty"),
                    rs.getInt("reorder_level"),
                    0,
                    null
            ));
        }

        return list;
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT product_id, name, category, qty, reorder_level " +
                        "FROM product WHERE category=? " +
                        "ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC",
                category
        );

        List<Product> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Product(
                    rs.getString("product_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("qty"),
                    rs.getInt("reorder_level"),
                    0,
                    null
            ));
        }

        return list;
    }
}