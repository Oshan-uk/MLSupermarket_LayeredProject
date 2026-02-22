package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.ProductBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.ProductDAO;
import lk.ijse.mlsupermarket.dto.ProductDTO;
import lk.ijse.mlsupermarket.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {

    private final ProductDAO productDAO =
            (ProductDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.PRODUCT);

    @Override
    public boolean saveProduct(ProductDTO dto) throws Exception {
        return productDAO.save(
                new Product(dto.getId(), dto.getName(), dto.getCategory(),
                        dto.getQty(), dto.getReorderLevel(),
                        dto.getPrice(), dto.getSupplierId())
        );
    }

    @Override
    public boolean updateProduct(ProductDTO dto) throws Exception {
        return productDAO.update(
                new Product(dto.getId(), dto.getName(), dto.getCategory(),
                        dto.getQty(), dto.getReorderLevel(),
                        dto.getPrice(), dto.getSupplierId())
        );
    }

    @Override
    public boolean deleteProduct(String id) throws Exception {
        return productDAO.delete(id);
    }

    @Override
    public ProductDTO searchProduct(String id) throws Exception {
        Product product = productDAO.search(id);

        if (product == null) return null;

        return new ProductDTO(
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
    public List<ProductDTO> getAllProducts() throws Exception {
        List<Product> entityList = productDAO.getAll();
        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product product : entityList) {
            dtoList.add(new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getCategory(),
                    product.getQty(),
                    product.getReorderLevel(),
                    product.getPrice(),
                    product.getSupplierId()
            ));
        }

        return dtoList;
    }

    @Override
    public String generateNextProductId() throws Exception {
        return productDAO.generateNextId();
    }
}