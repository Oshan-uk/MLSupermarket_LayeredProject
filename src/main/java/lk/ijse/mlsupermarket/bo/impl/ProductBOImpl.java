package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.ProductBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.CustomerDAO;
import lk.ijse.mlsupermarket.dao.custom.ProductDAO;
import lk.ijse.mlsupermarket.entity.Customer;
import lk.ijse.mlsupermarket.entity.Product;

import java.util.ArrayList;
import java.util.UUID;

public class ProductBOImpl implements ProductBO {

    ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);


    @Override
    public String generateId() throws Exception {
        return productDAO.generateId();
    }

    @Override
    public boolean save(Product id) throws Exception {
        return productDAO.save(id);
    }

    @Override
    public Product search(String id) throws Exception {
        return productDAO.search(id);
    }

    @Override
    public boolean update(Product product) throws Exception {
        return productDAO.update(product);
    }

    @Override
    public boolean delete(String id) throws Exception {
        return productDAO.delete(id);
    }

    @Override
    public ArrayList<Product> getAll() throws Exception {
        return productDAO.getAll();
    }
}
