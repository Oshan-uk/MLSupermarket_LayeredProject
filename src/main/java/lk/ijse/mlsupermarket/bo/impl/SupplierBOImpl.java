package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.SupplierBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.SupplierDAO;
import lk.ijse.mlsupermarket.dto.SupplierDTO;
import lk.ijse.mlsupermarket.entity.Supplier;

import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    private final SupplierDAO supplierDAO =
            (SupplierDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public boolean saveSupplier(SupplierDTO dto) throws Exception {
        return supplierDAO.save(
                new Supplier(dto.getSupplierId(), dto.getName(), dto.getContactInfo())
        );
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws Exception {
        return supplierDAO.update(
                new Supplier(dto.getSupplierId(), dto.getName(), dto.getContactInfo())
        );
    }

    @Override
    public boolean deleteSupplier(String id) throws Exception {
        return supplierDAO.delete(id);
    }

    @Override
    public SupplierDTO searchSupplier(String id) throws Exception {

        Supplier supplier = supplierDAO.search(id);

        if (supplier == null) return null;

        return new SupplierDTO(
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getContactInfo()
        );
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() throws Exception {

        List<Supplier> entityList = supplierDAO.getAll();
        List<SupplierDTO> dtoList = new ArrayList<>();

        for (Supplier supplier : entityList) {
            dtoList.add(new SupplierDTO(
                    supplier.getSupplierId(),
                    supplier.getName(),
                    supplier.getContactInfo()
            ));
        }

        return dtoList;
    }

    @Override
    public String generateNextSupplierId() throws Exception {
        return supplierDAO.generateNextId();
    }
}
