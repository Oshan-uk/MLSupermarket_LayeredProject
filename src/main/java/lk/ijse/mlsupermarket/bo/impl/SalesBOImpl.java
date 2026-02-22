package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.SalesBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.SalesDAO;
import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;

import java.util.List;

public class SalesBOImpl implements SalesBO {

    private final SalesDAO salesDAO =
            (SalesDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.SALES);

    @Override
    public boolean saveSale(SalesDTO sale, List<SaleItemDTO> items) throws Exception {
        return salesDAO.saveSale(sale, items);
    }

    @Override
    public boolean returnSaleItem(String saleId, String productId,
                                  int returnQty, double unitPrice) throws Exception {
        return salesDAO.returnSaleItem(saleId, productId, returnQty, unitPrice);
    }

    @Override
    public List<SaleItemDTO> getAllSalesItems() throws Exception {
        return salesDAO.getAllSalesItems();
    }

    @Override
    public void printStockReport() throws Exception {
        salesDAO.printStockReport();
    }

    @Override
    public String generateNextSaleId() throws Exception {
        return salesDAO.generateNextSaleId();
    }

    @Override
    public double getUnitPrice(String saleId, String productId) throws Exception {
        return salesDAO.getUnitPrice(saleId, productId);
    }

    @Override
    public List<String> getAllSaleIds() throws Exception {
        return salesDAO.getAllSaleIds();
    }
}