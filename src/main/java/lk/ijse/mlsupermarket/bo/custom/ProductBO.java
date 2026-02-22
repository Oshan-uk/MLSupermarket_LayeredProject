package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;
import lk.ijse.mlsupermarket.dto.ProductDTO;
import lk.ijse.mlsupermarket.entity.Customer;
import lk.ijse.mlsupermarket.entity.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductBO extends SuperBO {

    boolean saveProduct(ProductDTO dto) throws Exception;

    boolean updateProduct(ProductDTO dto) throws Exception;

    boolean deleteProduct(String id) throws Exception;

    ProductDTO searchProduct(String id) throws Exception;

    List<ProductDTO> getAllProducts() throws Exception;

    String generateNextProductId() throws Exception;
}
