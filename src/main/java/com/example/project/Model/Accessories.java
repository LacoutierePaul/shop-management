package com.example.project.Model;

public class Accessories extends Product {
    //region Constructors and copy
    /**
     * Unprotected constructor, use for get data from database only
     */
    public Accessories(int id,String name, double purchasePrice,double sellingPrice, int nbItems) {
        super(id, name, purchasePrice, sellingPrice, nbItems);
    }
    /**
     * Protected constructor, use for product creation / modification
     */
    public Accessories(String name, double purchasePrice,double sellingPrice, int nbItems) {
        super(name, purchasePrice, sellingPrice, nbItems);
    }

    public Product copy(){
        return new Accessories(getId(), getName(), getPurchasePrice(), getSellingPrice(), getNbItems());
    }

    //endregion

    //region Properties
    public String getType() {
        return "Accessories";
    }

    @Override
    public Integer getSize() {
        return null;
    }

    //endregion

    public String toString() {
        return "Accessories{" + super.toString() + "}";
    }

    public void applyDiscount(){
        super.applyDiscount(0.5);
    }
}
