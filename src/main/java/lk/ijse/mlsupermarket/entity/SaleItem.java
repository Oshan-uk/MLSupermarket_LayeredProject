package lk.ijse.mlsupermarket.entity;

import javafx.scene.control.Button;

public class SaleItem {

    private String productId;
    private String name;
    private int quantity;
    private double unitPrice;
    private double total;
    private Button btnRemove;
    private String saleId;

    public SaleItem() {}

    public SaleItem(String productId, String name, int quantity, double unitPrice, double total, Button btnRemove) {

        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.btnRemove = btnRemove;

    }

    public SaleItem(String saleId, String productId, int quantity, double unitPrice, double total) {

        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;

    }

    public String getSaleId() { return saleId; }
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotal() { return total; }
    public Button getBtnRemove() { return btnRemove; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTotal(double total) { this.total = total; }

}
