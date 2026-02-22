package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.InventoryBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.InventoryDAO;
import lk.ijse.mlsupermarket.dto.InventoryDTO;
import lk.ijse.mlsupermarket.entity.Product;
import lk.ijse.mlsupermarket.db.DBConnection;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class InventoryBOImpl implements InventoryBO {

    private final InventoryDAO inventoryDAO =
            (InventoryDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.INVENTORY);

    @Override
    public List<InventoryDTO> getAllInventory() throws Exception {

        List<Product> products = inventoryDAO.getAllProducts();
        List<InventoryDTO> list = new ArrayList<>();

        for (Product p : products) {

            String status = calculateStatus(p.getQty(), p.getReorderLevel());

            list.add(new InventoryDTO(
                    p.getId(),
                    p.getName(),
                    p.getCategory(),
                    p.getQty(),
                    p.getReorderLevel(),
                    status
            ));
        }

        return list;
    }

    @Override
    public List<InventoryDTO> getInventoryByCategory(String category) throws Exception {

        List<Product> products = inventoryDAO.getProductsByCategory(category);
        List<InventoryDTO> list = new ArrayList<>();

        for (Product p : products) {

            String status = calculateStatus(p.getQty(), p.getReorderLevel());

            list.add(new InventoryDTO(
                    p.getId(),
                    p.getName(),
                    p.getCategory(),
                    p.getQty(),
                    p.getReorderLevel(),
                    status
            ));
        }

        return list;
    }

    private String calculateStatus(int qty, int reorder) {
        if (qty == 0) return "OUT OF STOCK";
        if (qty <= reorder) return "LOW STOCK";
        return "IN STOCK";
    }

    @Override
    public void printStockReport() throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream(
                        "/lk/ijse/mlsupermarket/reports/inventory_report.jasper"
                );

        JasperPrint jp = JasperFillManager.fillReport(inputStream, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}