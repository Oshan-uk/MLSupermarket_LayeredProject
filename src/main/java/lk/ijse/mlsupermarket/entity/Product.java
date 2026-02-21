package lk.ijse.mlsupermarket.entity;

public class Product {

    private String id;

    private String name;

    private String category;

    private int qty;

    private int reorderLevel;

    private double price;

    private String supplierId;



    public Product(String id, String name, String category, int qty, int reorderLevel, double price,  String supplierId) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.qty = qty;
        this.reorderLevel = reorderLevel;
        this.price = price;
        this.supplierId = supplierId;

    }


    public String getId() {
        return id;

    }
    public void setId(String id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public int getQty() {return qty;}
    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }
    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

}
