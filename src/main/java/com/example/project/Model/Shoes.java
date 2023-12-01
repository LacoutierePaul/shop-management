package com.example.project.Model;

public class Shoes extends Product {
    private int shoesSize;

    //region Constructors and copy
    /**
     * Unprotected constructor, use for get data from database only
     */
    public Shoes(int id,String name, double purchasePrice,double sellingPrice, int nbItems, int shoesSize) {
        super(id,name, purchasePrice,sellingPrice,nbItems);
        this.shoesSize = shoesSize;
    }

    /**
     * Protected constructor, use for product creation / modification
     */
    public Shoes(String name, double purchasePrice,double sellingPrice, int nbItems, int shoesSize) {
        super(name, purchasePrice,sellingPrice,nbItems);
        this.setShoesSize(shoesSize);
    }
    public Product copy(){
        return new Shoes(getId(), getName(), getPurchasePrice(), getSellingPrice(), getNbItems(), getShoesSize());
    }

    //endregion

    //region Properties
    public String getType() {
        return "Shoes";
    }

    @Override
    public Integer getSize() {
        return this.shoesSize;
    }

    public Integer getShoesSize() {
        return this.shoesSize;
    }
    private void setShoesSize(int shoeSize) {
        if(!(36 <= shoeSize && shoeSize <= 50)) throw new IllegalArgumentException("Wrong shoe size ! \nMust be between 36 and 50");
        this.shoesSize = shoeSize;
    }

    //endregion

    public String toString() {
        String var10000 = super.toString();
        return "Shoes{" + var10000 + " shoeSize=" + this.shoesSize + "}";
    }

    public void applyDiscount() {
        super.applyDiscount(0.2);
    }
}
