package lk.ijse.mlsupermarket.entity;

public class Sales {

    private String saleId;
    private double totalAmount;
    private String saleDate;

    public Sales() {}

    public Sales(String saleId, double totalAmount, String saleDate) {
        this.saleId = saleId;
        this.totalAmount = totalAmount;
        this.saleDate = saleDate;
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

}
