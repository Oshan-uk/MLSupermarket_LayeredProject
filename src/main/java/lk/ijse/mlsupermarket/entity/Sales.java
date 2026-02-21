package lk.ijse.mlsupermarket.entity;

public class Sales {

    private String saleId;
    private double totalAmount;
    private String saleDate;
    private String customerId;

    public Sales() {}

    public Sales(String saleId, double totalAmount, String saleDate, String customerId) {
        this.saleId = saleId;
        this.totalAmount = totalAmount;
        this.saleDate = saleDate;
        this.customerId = customerId;
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
