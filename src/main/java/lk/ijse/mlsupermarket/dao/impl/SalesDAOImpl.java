package lk.ijse.mlsupermarket.dao.impl;

import lk.ijse.mlsupermarket.dao.custom.SalesDAO;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class SalesDAOImpl implements SalesDAO {

    @Override
    public boolean saveSale(SalesDTO sale, List<SaleItemDTO> items) throws SQLException {

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            String sqlSale = "INSERT INTO sales (sale_id, total_amount, sale_date) VALUES (?, ?, ?)";
            PreparedStatement psSale = conn.prepareStatement(sqlSale);

            psSale.setString(1, sale.getSaleId());
            psSale.setDouble(2, sale.getTotalAmount());
            psSale.setString(3, sale.getSaleDate());
            psSale.executeUpdate();

            String sqlItem = "INSERT INTO sales_item (sale_id, product_id, quantity, unit_price, total_price) VALUES (?,?,?,?,?)";
            PreparedStatement psItem = conn.prepareStatement(sqlItem);

            String sqlUpdateProduct = "UPDATE product SET qty = qty - ? WHERE product_id = ?";
            PreparedStatement psUpdateProduct = conn.prepareStatement(sqlUpdateProduct);

            String sqlUpdateInventory = "UPDATE inventory SET stock_quantity = stock_quantity - ? WHERE product_id = ?";
            PreparedStatement psUpdateInventory = conn.prepareStatement(sqlUpdateInventory);

            for (SaleItemDTO it : items) {

                psItem.setString(1, sale.getSaleId());
                psItem.setString(2, it.getProductId());
                psItem.setInt(3, it.getQuantity());
                psItem.setDouble(4, it.getUnitPrice());
                psItem.setDouble(5, it.getTotal());
                psItem.executeUpdate();

                psUpdateProduct.setInt(1, it.getQuantity());
                psUpdateProduct.setString(2, it.getProductId());
                psUpdateProduct.executeUpdate();

                psUpdateInventory.setInt(1, it.getQuantity());
                psUpdateInventory.setString(2, it.getProductId());
                psUpdateInventory.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public boolean returnSaleItem(String saleId, String productId,
                                  int returnQty, double unitPrice) throws SQLException {

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            PreparedStatement psReduce = conn.prepareStatement(
                    "UPDATE sales_item SET quantity = quantity - ? WHERE sale_id = ? AND product_id = ?");

            psReduce.setInt(1, returnQty);
            psReduce.setString(2, saleId);
            psReduce.setString(3, productId);
            psReduce.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public List<SaleItemDTO> getAllSalesItems() throws Exception {

        List<SaleItemDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM sales_item WHERE quantity > 0 ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC";

        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            list.add(new SaleItemDTO(
                    rs.getString("sale_id"),
                    rs.getString("product_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price"),
                    rs.getDouble("total_price")));
        }

        return list;
    }

    @Override
    public void printStockReport() throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream("/lk/ijse/mlsupermarket/reports/invoice_report.jasper");

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(inputStream, new HashMap<>(), conn);

        JasperViewer.viewReport(jasperPrint, false);
    }

    @Override
    public String generateNextSaleId() throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT sale_id FROM sales ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC LIMIT 1";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String lastId = rs.getString("sale_id");
            int num = Integer.parseInt(lastId.substring(1));
            return "S" + (num + 1);
        }

        return "S1";
    }

    @Override
    public double getUnitPrice(String saleId, String productId) throws SQLException {

        String sql = "SELECT unit_price FROM sales_item WHERE sale_id = ? AND product_id = ?";

        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, saleId);
        pst.setString(2, productId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) return rs.getDouble("unit_price");
        return 0;
    }

    @Override
    public List<String> getAllSaleIds() throws SQLException {

        List<String> list = new ArrayList<>();

        String sql = "SELECT sale_id FROM sales ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC";

        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) list.add(rs.getString("sale_id"));

        return list;
    }
}