package lk.ijse.mlsupermarket.entity;

public class Inventory {

    private String id;
    private String name;
    private String category;
    private int qty;
    private int reorderLevel;


    public Inventory(String id, String name, String category, int qty, int reorderLevel) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.qty = qty;
        this.reorderLevel = reorderLevel;


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





    public void setQty(int qty) {

        this.qty = qty;

    }



}
