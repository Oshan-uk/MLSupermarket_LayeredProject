package lk.ijse.mlsupermarket.dao.impl;


import lk.ijse.mlsupermarket.dao.custom.SalesItemDAO;
import lk.ijse.mlsupermarket.dao.CrudUtil;
import lk.ijse.mlsupermarket.entity.SaleItem;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SalesItemDAOImpl implements SalesItemDAO {

    @Override
    public boolean save(SaleItem entity) throws Exception {

        return CrudUtil.execute(
                "INSERT INTO sales_item (sale_id, product_id, quantity, unit_price, total_price) VALUES (?,?,?,?,?)",
                entity.getSaleId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getTotal()
        );
    }

    @Override
    public boolean update(SaleItem entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public String generateNextId() throws Exception {
        return "";
    }

    @Override
    public SaleItem search(String id) throws Exception {
        return null;
    }

    @Override
    public boolean reduceQuantity(String saleId, String productId, int qty) throws Exception {

        return CrudUtil.execute(
                "UPDATE sales_item SET quantity = quantity - ? WHERE sale_id = ? AND product_id = ?",
                qty, saleId, productId
        );
    }

    @Override
    public boolean deleteIfZero(String saleId, String productId) throws Exception {

        return CrudUtil.execute(
                "DELETE FROM sales_item WHERE sale_id = ? AND product_id = ? AND quantity <= 0",
                saleId, productId
        );
    }


    public double getUnitPrice(String saleId, String productId) throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT unit_price FROM sales_item WHERE sale_id = ? AND product_id = ?",
                saleId, productId
        );

        if (rs.next()) {
            return rs.getDouble("unit_price");
        }

        return 0;
    }

    @Override
    public ArrayList<SaleItem> getAll() throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM sales_item WHERE quantity > 0 ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC"
        );

        ArrayList<SaleItem> list = new ArrayList<>();

        while (rs.next()) {



            list.add(new SaleItem(
                    rs.getString("sale_id"),
                    rs.getString("product_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price"),
                    rs.getDouble("total_price")
            ));
        }

        return list;
    }
}
