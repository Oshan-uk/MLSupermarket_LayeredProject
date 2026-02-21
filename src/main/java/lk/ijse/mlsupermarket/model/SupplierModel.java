package lk.ijse.mlsupermarket.model;

import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.SupplierDTO;
import lk.ijse.mlsupermarket.dao.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {


    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {

        return CrudUtil.execute("INSERT INTO supplier (supplier_id, name, contact_info) VALUES (?,?,?)", supplierDTO.getSupplierId(), supplierDTO.getName(), supplierDTO.getContactInfo());

    }


    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {

        return CrudUtil.execute("UPDATE supplier SET name=?, contact_info=? WHERE supplier_id=?", supplierDTO.getName(), supplierDTO.getContactInfo(), supplierDTO.getSupplierId());

    }


    public boolean deleteSupplier(String id) throws SQLException {

        return CrudUtil.execute("DELETE FROM supplier WHERE supplier_id=?", id);

    }


    public SupplierDTO searchSupplier(String id) throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier WHERE supplier_id=?", id);

        if (rs.next()) {

            return new SupplierDTO(rs.getString("supplier_id"), rs.getString("name"), rs.getString("contact_info"));

        }

        return null;
    }


    public List<SupplierDTO> getSuppliers() throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier ORDER BY CAST(SUBSTRING(supplier_id, 2) AS UNSIGNED) DESC");

        List<SupplierDTO> supplierList = new ArrayList<>();

        while (rs.next()) {

            SupplierDTO supplier = new SupplierDTO(rs.getString("supplier_id"), rs.getString("name"), rs.getString("contact_info"));
            supplierList.add(supplier);
        }

        return supplierList;
    }



    public String generateNextSupplierId() throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT supplier_id FROM supplier ORDER BY CAST(SUBSTRING(supplier_id, 2) AS UNSIGNED) DESC LIMIT 1";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String lastId = rs.getString("supplier_id");
            int num = Integer.parseInt(lastId.substring(1));
            return "S" + (num + 1);
        }

        return "S1";
    }


}
