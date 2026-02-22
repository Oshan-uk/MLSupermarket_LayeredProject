package lk.ijse.mlsupermarket.dao;

import lk.ijse.mlsupermarket.dao.impl.*;

public class DAOFactory {

    private static DAOFactory instance;
    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return (instance == null) ? instance = new DAOFactory() : instance;
    }

    public enum DAOType {
        CUSTOMER,
        PRODUCT,
        INVENTORY,
        SALES,
        SALE_ITEM,
        SUPPLIER,
        USER,
        QUERY
    }

    public SuperDAO getDAO(DAOType daoType) {
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case SALES:
                return new SalesDAOImpl();
            case SALE_ITEM:
                return new SalesItemDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case USER:
                return new UserDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }

}
