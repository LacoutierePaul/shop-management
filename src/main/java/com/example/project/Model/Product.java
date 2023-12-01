package com.example.project.Model;


import com.example.project.Interface.Discount;

import java.util.List;

public abstract class   Product implements Discount, Comparable<Product> {
    //region Instances
    private int id;
    private String name;
    private double purchasePrice;
    private double sellingPrice;
    private int nbItems;
    private static double income = 0.0;
    private static double capital = 0.0;
    private static double cost = 0.0;

    //endregion

    //region Constructors and copy
    /**
     * Unprotected constructor, use for get data from database only
     */
    public Product(int id,String name, double purchasePrice,double sellingPrice, int nbItems) {
        this.id =id;
        this.name = name;
        this.sellingPrice=sellingPrice;
        this.purchasePrice=purchasePrice;
        this.nbItems = nbItems;
    }

    /**
     * Protected constructor, use for product creation / modification
     */
    public Product(String name, double purchasePrice,double sellingPrice, int nbItems) {
        this.setName(name);
        this.setNbItems(nbItems);
        this.setPurchasePrice(purchasePrice);
        this.setSellingPrice(sellingPrice);
    }

    public abstract Product copy();

    //endregion

    //region Properties
    public static double getIncome() {
        return income;
    }
    public static void setIncome(double income) {
        Product.income = income;
    }

    public static double getCapital() {
        return capital;
    }
    public static void setCapital(double capital) {
        Product.capital = capital;
    }

    public static double getCost() {
        return cost;
    }
    public static void setCost(double cost) {
        Product.cost = cost;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    private void setName(String name) {
        this.name = name;
    }

    public int getNbItems() {
        return this.nbItems;
    }
    private void setNbItems(int nbItems) throws IllegalArgumentException{
        if (nbItems < 0)
            throw new IllegalArgumentException("Quantity is negative");
        this.nbItems = nbItems;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }
    private void setPurchasePrice(double purchasePrice) throws IllegalArgumentException {
        if (purchasePrice < 0.0)
            throw new IllegalArgumentException("PurchasePrice is negative");
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }
    private void setSellingPrice(double sellingPrice) throws IllegalArgumentException {
        if (sellingPrice < 0.0)
            throw new IllegalArgumentException("sellingPrice is negative");
        this.sellingPrice = sellingPrice;
    }

    public abstract String getType();

    //endregion

    //region methods
    public void sell(int nbItems) throws IllegalArgumentException {
        if (nbItems < 0)
            throw new IllegalArgumentException("Negative number selected");
        if (nbItems == 0)
            throw new IllegalArgumentException("Select a quantity to sell");
        if (this.nbItems == 0)
            throw new IllegalArgumentException("Unavailable product");
        if (this.nbItems < nbItems)
            throw new IllegalArgumentException("Not enough items");
        this.setNbItems(this.nbItems - nbItems);
        income += (double)nbItems * this.sellingPrice;
        capital+=(double)nbItems * this.sellingPrice;
        System.out.println("Sell OK !");
    }

    public void purchase(int nbItems) throws IllegalArgumentException {
        if (nbItems < 0)
            throw new IllegalArgumentException("Negative number selected");
        if (nbItems == 0)
            throw new IllegalArgumentException("Select a quantity to purchase");
        double cost = nbItems * this.purchasePrice;
        if(Product.capital < cost)
            throw new IllegalArgumentException("Not enough capital");
        Product.cost += cost;
        Product.capital -= cost;
        this.setNbItems(this.nbItems + nbItems);
        System.out.println("Purchase OK !");
    }

    public static void addCapital(double money) {
        var res = capital + money;
        if (res < 0)
            throw new IllegalArgumentException("Capital should not be negative");
        capital = res;
    }

    //endregion

    public abstract Integer getSize();

    public String toString() {
        return "Product{id=" + this.id + ", name='" + this.name + "', purchase=" + this.purchasePrice + ", nbItems=" + this.nbItems + "}";
    }

    public int compareTo(Product o) {
        return Double.compare(this.getSellingPrice(), o.getSellingPrice());
    }

    protected void applyDiscount(double discount) {
        this.setSellingPrice(this.getSellingPrice() * (1 - discount));
    }

    public int similarityWith(Product product){
        if(!product.getType().equals(this.getType())) return 0;
        if(!product.name.equals(this.name)) return 1;
        if(product.getSize() != this.getSize()) return 2;
        if(product.purchasePrice != this.purchasePrice || product.sellingPrice != this.sellingPrice) return 3;
        if(product.nbItems != this.nbItems) return 4;
        if(product.id != this.id) return 5;
        return 6;
    }
    public Product findMostSimilarProduct(List<Product> productList) {
        if (productList.isEmpty())
            return null;
        Product mostSimilarProduct = productList.get(0);
        int maxSimilarity = 0;
        for (Product product : productList) {
            int similarity = this.similarityWith(product);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                mostSimilarProduct = product;
            }
        }
        return mostSimilarProduct;
    }
}
