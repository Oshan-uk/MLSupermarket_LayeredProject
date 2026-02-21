package lk.ijse.mlsupermarket.model;

import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.ProductDTO;
import lk.ijse.mlsupermarket.dao.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {

    public boolean saveProduct(ProductDTO product) throws SQLException {

        return CrudUtil.execute("INSERT INTO product (product_id, name, category, qty, reorder_level, price, supplier_id) VALUES (?,?,?,?,?,?)", product.getId(), product.getName(), product.getCategory(), product.getQty(), product.getReorderLevel(), product.getPrice());

    }

    public boolean updateProduct(ProductDTO product) throws SQLException {
        return CrudUtil.execute("UPDATE product SET name=?, category=?, qty=?, reorder_level=?, price=?, supplier_id=? WHERE product_id=?", product.getName(), product.getCategory(), product.getQty(), product.getReorderLevel(), product.getPrice(), product.getSupplierId(), product.getId());
    }


    public boolean deleteProduct(String id) throws SQLException {

        return CrudUtil.execute("DELETE FROM product WHERE product_id=?", id);

    }

    public ProductDTO searchProduct(String id) throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM product WHERE product_id=?", id);

        if (rs.next()) {

            return new ProductDTO(rs.getString("product_id"), rs.getString("name"), rs.getString("category"), rs.getInt("qty"), rs.getInt("reorder_level"), rs.getDouble("price"),rs.getString("supplier_id"));

        }

        return null;
    }

    public List<ProductDTO> getProducts() throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM product ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC");

        List<ProductDTO> productList = new ArrayList<>();

        while (rs.next()) {
            productList.add(new ProductDTO(rs.getString("product_id"), rs.getString("name"), rs.getString("category"), rs.getInt("qty"), rs.getInt("reorder_level"), rs.getDouble("price"), rs.getString("supplier_id")));
        }

        return productList;
    }



    public String generateNextProductId() throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT product_id FROM product ORDER BY CAST(SUBSTRING(product_id, 2) AS UNSIGNED) DESC LIMIT 1";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String lastId = rs.getString("product_id");
            int num = Integer.parseInt(lastId.substring(1));
            return "P" + (num + 1);
        }

        return "P1";
    }


}
