package lk.ijse.mlsupermarket.dao.impl;


import lk.ijse.mlsupermarket.dao.custom.SupplierDAO;
import lk.ijse.mlsupermarket.entity.Supplier;
import lk.ijse.mlsupermarket.dao.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public boolean save(Supplier supplier) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO supplier (supplier_id, name, contact_info) VALUES (?,?,?)",
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getContactInfo()
        );
    }

    @Override
    public boolean update(Supplier supplier) throws Exception {
        return CrudUtil.execute(
                "UPDATE supplier SET name=?, contact_info=? WHERE supplier_id=?",
                supplier.getName(),
                supplier.getContactInfo(),
                supplier.getSupplierId()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.execute(
                "DELETE FROM supplier WHERE supplier_id=?",
                id
        );
    }

    @Override
    public Supplier search(String id) throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM supplier WHERE supplier_id=?",
                id
        );

        if (rs.next()) {
            return new Supplier(
                    rs.getString("supplier_id"),
                    rs.getString("name"),
                    rs.getString("contact_info")
            );
        }

        return null;
    }

    @Override
    public List<Supplier> getAll() throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM supplier ORDER BY CAST(SUBSTRING(supplier_id, 2) AS UNSIGNED) DESC"
        );

        List<Supplier> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Supplier(
                    rs.getString("supplier_id"),
                    rs.getString("name"),
                    rs.getString("contact_info")
            ));
        }

        return list;
    }

    @Override
    public String generateNextId() throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT supplier_id FROM supplier ORDER BY CAST(SUBSTRING(supplier_id, 2) AS UNSIGNED) DESC LIMIT 1"
        );

        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.substring(1));
            return "S" + (num + 1);
        }

        return "S1";
    }
}
