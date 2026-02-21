package lk.ijse.mlsupermarket.bo;


import lk.ijse.mlsupermarket.bo.impl.*;

public class BOFactory {


    private static BOFactory instance;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        if (instance == null) {
            instance = new BOFactory();

        }
        return instance;
    }


    public enum BOTypes {
        CUSTOMER,
        INVENTORY,
        PRODUCT,
        SALES,
        SUPPLIER
    }

    public SuperBO getBO(BOTypes boType) {

        switch (boType) {

            case CUSTOMER:
                return new CustomerBOImpl();

            case INVENTORY:
                return new InventoryBOImpl();

            case PRODUCT:
                return new ProductBOImpl();

            case SALES:
                return new SalesBOImpl();

            case SUPPLIER:
                return new SupplierBOImpl();

            default:
                return null;
        }
    }
}
