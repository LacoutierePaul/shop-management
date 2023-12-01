package com.example.project.Database;

import com.example.project.Model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    static private String DB_URL;
    static private String DB_USER;
    static private String DB_PASSWORD;

    //region SQL Connection
    public static void setCredentials(String url, String user, String pwd){
        DB_URL = url;
        DB_USER = user;
        DB_PASSWORD = pwd;
    }

    private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if(myStmt!=null)
                myStmt.close();
            if(myRs!=null)
                myRs.close();
            if(myConn!=null)
                myConn.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static Connection Connector() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //endregion

    //region Product
    private void fillSQLStatementWithProduct(PreparedStatement myStmt, Product product) throws SQLException, ClassNotFoundException {
        String type = product.getType();
        myStmt.setString(1, product.getName());
        myStmt.setInt(2, product.getNbItems());
        myStmt.setDouble(3, product.getPurchasePrice());
        myStmt.setDouble(4, product.getSellingPrice());
        myStmt.setString(5, type);
        switch(type){
            case "Accessories":
                myStmt.setInt(6, 0);
                break;
            case "Clothes":
                myStmt.setInt(6, ((Clothes)product).getClothesSize());
                break;
            case "Shoes":
                myStmt.setInt(6, ((Shoes)product).getShoesSize());
                break;
            default:
                throw new ClassNotFoundException(type + " not implemented");
        }
    }

    private List<Product> loadProducts(ResultSet myRs) throws SQLException {
        List<Product> productAll = new ArrayList<Product>();
        while (myRs.next()) {
            var cat = myRs.getString("category");
            switch(cat){
                case "Accessories":
                    productAll.add(new Accessories(
                            myRs.getInt("id"),
                            myRs.getString("name"),
                            myRs.getBigDecimal("purchasePrice").doubleValue(),
                            myRs.getBigDecimal("sellingPrice").doubleValue(),
                            myRs.getInt("quantity")));
                    break;
                case "Shoes":
                    productAll.add(new Shoes(
                            myRs.getInt("id"),
                            myRs.getString("name"),
                            myRs.getBigDecimal("purchasePrice").doubleValue(),
                            myRs.getBigDecimal("sellingPrice").doubleValue(),
                            myRs.getInt("quantity"),
                            myRs.getInt("size")));
                    break;
                case "Clothes":
                    productAll.add(new Clothes(
                            myRs.getInt("id"),
                            myRs.getString("name"),
                            myRs.getBigDecimal("purchasePrice").doubleValue(),
                            myRs.getBigDecimal("sellingPrice").doubleValue(),
                            myRs.getInt("quantity"),
                            myRs.getInt("size")));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected category '" + cat + "'");
            }
        }
        return productAll;
    }
    public List<Product> loadProducts(String categoryFilter) {
        List<Product> productAll = null;
        Connection myConn = Connector();
        try {
            Statement myStmt = myConn.createStatement();
            String sql = categoryFilter == "All" ?
                    "SELECT * FROM product" :
                    "SELECT * FROM product WHERE category = '" + categoryFilter + "'";
            ResultSet myRs = myStmt.executeQuery(sql);
            productAll = loadProducts(myRs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(myConn, null, null);
            return productAll;
        }
    }

    public void createProduct(Product selectedProduct) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = Connector();
            String sql = "INSERT INTO product (name,quantity,purchasePrice,sellingPrice,category,size) VALUES (?, ?,?,?,?,?)";
            myStmt = myConn.prepareStatement(sql);
            fillSQLStatementWithProduct(myStmt, selectedProduct);

            myStmt.execute();
            System.out.println(selectedProduct+" Added");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,null);
        }
    }

    public void updateProduct(Product selectedProduct, int id) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = Connector();
            String sql = "UPDATE product SET name = ?, quantity = ?, purchasePrice = ?, sellingPrice = ?, category = ? ,size = ? WHERE id = ?";
            myStmt = myConn.prepareStatement(sql);
            fillSQLStatementWithProduct(myStmt, selectedProduct);
            myStmt.setInt(7, id);
            myStmt.executeUpdate();
            System.out.println(selectedProduct + " Updated");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            close(myConn, myStmt, null); // Note: No ResultSet is used for DELETE operation
        }
    }

    public void deleteProduct(int id) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = Connector();
            String sql = "DELETE FROM  product WHERE id = ?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, id);
            myStmt.executeUpdate();
            System.out.println("Product id:"+ id + " Deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(myConn, myStmt, null); // Note: No ResultSet is used for DELETE operation
        }
    }

    //endregion

    //region Money and Quantity
    public List<Double> loadMoney() {
        List<Double> listmoney= new ArrayList<Double>();
        Connection myConn= Connector();
        try {
            Statement myStmt= myConn.createStatement();
            String sql = "select * from money";
            ResultSet myRs= myStmt.executeQuery(sql);

            myRs.next();
            listmoney.add(myRs.getDouble("capital"));
            listmoney.add(myRs.getDouble("income"));
            listmoney.add(myRs.getDouble("cost"));

            close(myConn, myStmt, myRs);
            return listmoney;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateMoneyDb() {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = Connector();
            String sql = "UPDATE money SET capital = ?, income = ?, cost = ? WHERE id = 1";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setDouble(1, Product.getCapital());
            myStmt.setDouble(2, Product.getIncome());
            myStmt.setDouble(3, Product.getCost());
            myStmt.executeUpdate();
            System.out.println("Capital Updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(myConn, myStmt, null); // Note: No ResultSet is used for DELETE operation
        }
    }

    public void updateProductQty(Product selectedProduct) {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = Connector();
            String sql = "UPDATE product SET quantity = ? WHERE id = ?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, selectedProduct.getNbItems());
            myStmt.setInt(2, selectedProduct.getId());

            myStmt.executeUpdate();
            System.out.println(selectedProduct+ " : Quantity Updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(myConn, myStmt, null); // Note: No ResultSet is used for DELETE operation
        }
    }

    //endregion

    //region ScriptSQL
    public static void executeScriptFile(String filePath, String schema) throws IOException {
        StringBuilder scriptContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scriptContent.append(line.trim());
            }
        }
        String sqlScript = "USE " + schema+";" + scriptContent.toString();
        Connection myConn = null;
        Statement myStmt = null;
        try{
            myConn = Connector();
            for(String statement : sqlScript.split(";")) {
                System.out.println(statement);
                myStmt = myConn.createStatement();
                myStmt.execute(statement);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(myConn, myStmt, null);
        }
    }
    public static boolean isTablesCreated(){
        Connection myConn = null;
        Statement myStmt = null;
        try{
            myConn = Connector();
            myStmt = myConn.createStatement();
            myStmt.execute("SELECT * FROM product");
            myStmt = myConn.createStatement();
            myStmt.execute("SELECT * FROM money");
            close(myConn, myStmt, null);
            return true;
        }
        catch (SQLException e) {
            close(myConn, myStmt, null);
            return false;
        }
    }

    //endregion
}