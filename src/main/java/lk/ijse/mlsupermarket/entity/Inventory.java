package lk.ijse.mlsupermarket.entity;

public class Inventory {

    private String id;
    private String name;
    private String category;
    private int qty;
    private int reorderLevel;
    private String status;

    public Inventory(String id, String name, String category, int qty, int reorderLevel, String status) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.qty = qty;
        this.reorderLevel = reorderLevel;
        this.status = status;

    }

    public String getId() {

        return id;

    }


    public String getName() {

        return name;

    }


    public String getCategory() {

        return category;

    }


    public int getQty() {

        return qty;

    }


    public int getReorderLevel() {

        return reorderLevel;

    }


    public String getStatus() {

        return status;

    }


    public void setQty(int qty) {

        this.qty = qty;

    }


    public void setStatus(String status) {

        this.status = status;

    }
}
