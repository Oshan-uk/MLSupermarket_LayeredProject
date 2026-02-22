package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.SalesBO;
import lk.ijse.mlsupermarket.dao.CrudUtil;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.*;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.*;
import lk.ijse.mlsupermarket.entity.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SalesBOImpl implements SalesBO {

    private final SalesDAO salesDAO =
            (SalesDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SALES);

    private final SalesItemDAO saleItemDAO =
            (SalesItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SALE_ITEM);

    private final ProductDAO productDAO =
            (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);

    @Override
    public boolean saveSale(SalesDTO saleDTO, List<SaleItemDTO> items) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            boolean isSaleSaved = salesDAO.save(
                    new Sales(
                            salesDTO.getSaleId(),
                            salesDTO.getTotalAmount(),
                            salesDTO.getSaleDate(),
                            salesDTO.getCustomerId()
                    )
            );

            if (!isSaleSaved) {
                conn.rollback();
                return false;
            }

            for (SaleItemDTO dto : items) {

                boolean isItemSaved = saleItemDAO.save(
                        new SaleItem(
                                saleDTO.getSaleId(),
                                dto.getProductId(),
                                dto.getQuantity(),
                                dto.getUnitPrice(),
                                dto.getTotal()
                        )
                );

                if (!isItemSaved) {
                    conn.rollback();
                    return false;
                }

                boolean isProductUpdated =
                        productDAO.reduceQuantity(dto.getProductId(), dto.getQuantity());

                if (!isProductUpdated) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public String generateNextSaleId() throws Exception {
        return salesDAO.generateNextId();
    }

    @Override
    public List<SaleItemDTO> getAllSalesItems() throws Exception {

        List<SaleItem> list = saleItemDAO.getAll();
        List<SaleItemDTO> dtoList = new ArrayList<>();

        for (SaleItem s : list) {
            dtoList.add(new SaleItemDTO(
                    s.getSaleId(),
                    s.getProductId(),
                    s.getQuantity(),
                    s.getUnitPrice(),
                    s.getTotalPrice()
            ));
        }

        return dtoList;
    }

    @Override
    public void printStockReport() throws Exception {
        salesDAO.printReport();
    }
}