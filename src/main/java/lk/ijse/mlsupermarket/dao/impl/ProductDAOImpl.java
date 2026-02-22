package lk.ijse.mlsupermarket.dao.impl;

import lk.ijse.mlsupermarket.dao.custom.ProductDAO;
import lk.ijse.mlsupermarket.entity.Product;
import lk.ijse.mlsupermarket.dao.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean save(Product product) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO product (product_id, name, category, qty, reorder_level, price, supplier_id) VALUES (?,?,?,?,?,?,?)",
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getQty(),
                product.getReorderLevel(),
                product.getPrice(),
                product.getSupplierId()
        );
    }

    @Override
    public boolean update(Product product) throws Exception {
        return CrudUtil.execute(
                "UPDATE product SET name=?, category=?, qty=?, reorder_level=?, price=?, supplier_id=? WHERE product_id=?",
                product.getName(),
                product.getCategory(),
                product.getQty(),
                product.getReorderLevel(),
                product.getPrice(),
                product.getSupplierId(),
                product.getId()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.execute(
                "DELETE FROM product WHERE product_id=?",
                id
        );
    }

    @Override
    public Product search(String id) throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM product WHERE product_id=?",
                id
        );

        if (rs.next()) {
            return new Product(
                    rs.getString("product_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("qty"),
                    rs.getInt("reorder_level"),
                    rs.getDouble("price"),
                    rs.getString("supplier_id")
            );
        }

        return null;
    }

    @Override
    public List<Product> getAll() throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM product ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC"
        );

        List<Product> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Product(
                    rs.getString("product_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("qty"),
                    rs.getInt("reorder_level"),
                    rs.getDouble("price"),
                    rs.getString("supplier_id")
            ));
        }

        return list;
    }

    @Override
    public String generateNextId() throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT product_id FROM product ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC LIMIT 1"
        );

        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.substring(1));
            return "P" + (num + 1);
        }

        return "P1";
    }
}