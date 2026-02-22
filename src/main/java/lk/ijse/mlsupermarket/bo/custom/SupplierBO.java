package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;
import lk.ijse.mlsupermarket.dto.SupplierDTO;

import java.util.List;

public interface SupplierBO extends SuperBO {

    boolean saveSupplier(SupplierDTO dto) throws Exception;

    boolean updateSupplier(SupplierDTO dto) throws Exception;

    boolean deleteSupplier(String id) throws Exception;

    SupplierDTO searchSupplier(String id) throws Exception;

    List<SupplierDTO> getAllSuppliers() throws Exception;

    String generateNextSupplierId() throws Exception;
}
