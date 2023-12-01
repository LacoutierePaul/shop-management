package com.example.project.Model;

public class Clothes extends Product {
    private int clothesSize;

    //region Constructors and copy
    /**
     * Unprotected constructor, use for get data from database only
     */
    public Clothes(int id, String name, double purchasePrice, double sellingPrice, int nbItems, int clothesSize) {
        super(id,name, purchasePrice,sellingPrice, nbItems);
        this.clothesSize = clothesSize;
    }

    /**
     * Protected constructor, use for product creation / modification
     */
    public Clothes(String name, double purchasePrice, double sellingPrice, int nbItems, int clothesSize) {
        super(name, purchasePrice,sellingPrice, nbItems);
        this.setClothesSize(clothesSize);
    }
    public Product copy(){
        return new Clothes(getId(), getName(), getPurchasePrice(), getSellingPrice(), getNbItems(), getClothesSize());
    }

    //endregion

    //region Properties
    public String getType() {
        return "Clothes";
    }

    @Override
    public Integer getSize() {
        return this.clothesSize;
    }

    public Integer getClothesSize()  {
        return this.clothesSize;
    }
    private void setClothesSize(int clothesSize) {
        if(!(34 <= clothesSize && clothesSize <= 50)) throw new IllegalArgumentException("Wrong clothes size ! \nMust be between 34 and 50");
        this.clothesSize = clothesSize;
    }

    //endregion

    public String toString() {
        String var10000 = super.toString();
        return "Clothes{" + var10000 + " size=" + this.clothesSize + "}";
    }

    public void applyDiscount() {
        super.applyDiscount(0.3);
    }
}
