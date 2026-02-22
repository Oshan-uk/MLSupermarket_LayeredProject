package lk.ijse.mlsupermarket.entity;

public class Sales {

    private String saleId;
    private String productId;
    private double totalAmount;
    private String saleDate;
    private String customerId;

    public Sales() {}

    public Sales(String saleId,String productId , double totalAmount, String saleDate, String customerId) {
        this.saleId = saleId;
        this.productId = productId;
        this.totalAmount = totalAmount;
        this.saleDate = saleDate;
        this.customerId = customerId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public String getSaleId() {

        return saleId;

    }
    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getCustomerId() {

        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
