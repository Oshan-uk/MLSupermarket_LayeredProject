package lk.ijse.mlsupermarket.bo.custom.impl;

import lk.ijse.mlsupermarket.bo.custom.SalesBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.InventoryDAO;
import lk.ijse.mlsupermarket.dao.custom.ProductDAO;
import lk.ijse.mlsupermarket.dao.custom.SalesDAO;
import lk.ijse.mlsupermarket.dao.custom.SalesItemDAO;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.entity.Sale;
import lk.ijse.mlsupermarket.entity.SaleItem;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SalesBOImpl implements SalesBO {

    private final SalesDAO salesDAO =
            (SalesDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.SALES);

    private final SalesItemDAO salesItemDAO =
            (SalesItemDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.SALE_ITEM);

    private final ProductDAO productDAO =
            (ProductDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.PRODUCT);

    private final InventoryDAO inventoryDAO =
            (InventoryDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.INVENTORY);

    @Override
    public boolean placeSale(SalesDTO saleDTO, List<SaleItemDTO> itemDTOList) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);


            boolean isSaleSaved = salesDAO.save(
                    new Sale(
                            saleDTO.getSaleId(),
                            saleDTO.getTotalAmount(),
                            saleDTO.getSaleDate()
                    )
            );

            if (!isSaleSaved) {
                conn.rollback();
                return false;
            }

            // 2️⃣ Save Sale Items + Update Stock
            for (SaleItemDTO dto : itemDTOList) {

                boolean isItemSaved = salesItemDAO.save(
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

                // Update product qty
                boolean isProductUpdated =
                        productDAO.reduceQty(dto.getProductId(), dto.getQuantity());

                if (!isProductUpdated) {
                    conn.rollback();
                    return false;
                }

                // Update inventory qty
                boolean isInventoryUpdated =
                        inventoryDAO.reduceQty(dto.getProductId(), dto.getQuantity());

                if (!isInventoryUpdated) {
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
    public boolean returnSaleItem(String saleId, String productId, int returnQty) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            // Reduce sale item quantity
            salesItemDAO.reduceQuantity(saleId, productId, returnQty);

            // Delete if zero
            salesItemDAO.deleteIfZero(saleId, productId);

            // Update sale total
            salesDAO.updateTotalAmount(saleId);

            // Increase product qty
            productDAO.addQty(productId, returnQty);

            // Increase inventory qty
            inventoryDAO.addQty(productId, returnQty);

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

        String lastId = salesDAO.getLastSaleId();

        if (lastId == null) {
            return "S1";
        }

        int num = Integer.parseInt(lastId.substring(1));
        return "S" + (num + 1);
    }

    @Override
    public List<String> getAllSaleIds() throws Exception {
        return salesDAO.getAllSaleIds();
    }

    @Override
    public List<SaleItemDTO> getAllSaleItems() throws Exception {

        List<SaleItem> entities = salesItemDAO.getAll();
        List<SaleItemDTO> dtoList = new ArrayList<>();

        for (SaleItem entity : entities) {
            dtoList.add(new SaleItemDTO(
                    entity.getSaleId(),
                    entity.getProductId(),
                    entity.getQuantity(),
                    entity.getUnitPrice(),
                    entity.getTotalPrice()
            ));
        }

        return dtoList;
    }
}