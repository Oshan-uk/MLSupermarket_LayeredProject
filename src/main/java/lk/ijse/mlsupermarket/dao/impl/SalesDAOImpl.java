package lk.ijse.mlsupermarket.dao.impl;

import lk.ijse.mlsupermarket.dao.CrudUtil;
import lk.ijse.mlsupermarket.dao.custom.SalesDAO;
import lk.ijse.mlsupermarket.entity.Sales;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SalesDAOImpl implements SalesDAO {

    @Override
    public boolean save(Sales entity) throws Exception {

        return CrudUtil.execute(
                "INSERT INTO sales (sale_id, total_amount, sale_date) VALUES (?, ?, ?)",
                entity.getSaleId(),
                entity.getTotalAmount(),
                entity.getSaleDate()
        );
    }

    @Override
    public boolean update(Sales entity) throws Exception {

        return CrudUtil.execute(
                "UPDATE sales SET total_amount = ?, sale_date = ? WHERE sale_id = ?",
                entity.getTotalAmount(),
                entity.getSaleDate(),
                entity.getSaleId()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {

        return CrudUtil.execute(
                "DELETE FROM sales WHERE sale_id = ?",
                id
        );
    }

    @Override
    public String generateNextId() throws Exception {
        return "";
    }

    @Override
    public Sales search(String id) throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM sales WHERE sale_id = ?",
                id
        );

        if (rs.next()) {
            return new Sales(
                rs.getString(getLastSaleId()),
                rs.get
            );
        }

        return null;
    }

    @Override
    public ArrayList<Sales> getAll() throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM sales ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC"
        );

        ArrayList<Sales> list = new ArrayList<>();

        while (rs.next()) {

            String saleId = rs.getString("sale_id");
            String productId = rs.getString("product_id");
            int quantity = rs.getInt("quantity");

            Sales sales = new Sales(saleId,productId,quantity);
            sales.add(saleId);

            list.add(new Sales(
                    rs.getString("sale_id"),
                    rs.getDouble("total_amount"),
                    rs.getString("sale_date")
            ));
        }

        return list;
    }

    @Override
    public String getLastSaleId() throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT sale_id FROM sales ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC LIMIT 1"
        );

        if (rs.next()) {
            return rs.getString("sale_id");
        }

        return null;
    }

    @Override
    public List<String> getAllSaleIds() throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT sale_id FROM sales ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC"
        );

        List<String> list = new ArrayList<>();

        while (rs.next()) {
            list.add(rs.getString("sale_id"));
        }

        return list;
    }

    @Override
    public boolean updateTotalAmount(String saleId) throws Exception {

        return CrudUtil.execute(
                "UPDATE sales SET total_amount = " +
                        "(SELECT IFNULL(SUM(quantity * unit_price),0) FROM sales_item WHERE sale_id = ?) " +
                        "WHERE sale_id = ?",
                saleId, saleId
        );
    }
}