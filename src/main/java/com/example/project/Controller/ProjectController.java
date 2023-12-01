package com.example.project.Controller;

import com.example.project.Database.DBManager;
import com.example.project.Model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectController implements Initializable{
    //region Controls FXML
    @FXML
    private Label txtCapital;
    @FXML
    private Label txtIncome;
    @FXML
    private Label txtCost;

    @FXML
    private ComboBox<String> comboBoxCategory;
    @FXML
    private TableView<Product> tvProducts;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtQuantity;
    @FXML
    private TextField txtBuying;
    @FXML
    private TextField txtSelling;
    @FXML
    private ComboBox<String> comboBoxType;
    @FXML
    private TextField txtSize;
    @FXML
    private Label lblSize;

    @FXML
    private Label labelInfo;

    @FXML
    private ComboBox<String> comboBoxBuySell;
    @FXML
    private Spinner<Integer> spinnerQuantity;
    @FXML
    private CheckBox checkboxSoldes;
    @FXML
    private TextField txtUnitPrice;
    @FXML
    private TextField txtTotalPrice;
    @FXML
    private Button btnBuySell;

    //endregion

    DBManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new DBManager();

        //Initialize Combo box
        comboBoxCategory.setItems(FXCollections.observableArrayList("All", "Accessories", "Shoes", "Clothes"));
        comboBoxCategory.getSelectionModel().select(0);
        comboBoxCategory.setOnAction(event -> { fetchProducts(); });
        comboBoxType.setItems(FXCollections.observableArrayList("Accessories", "Shoes", "Clothes"));
        comboBoxType.setOnAction(event -> { setDisplayFormat(comboBoxType.getValue());});
        comboBoxBuySell.setItems(FXCollections.observableArrayList("Buy", "Sell"));
        comboBoxBuySell.setOnAction(event -> { onSelectBuySell(); });
        //comboBoxBuySell.setValue("Buy");

        //Initialize spinner
        spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
        spinnerQuantity.valueProperty().addListener(event -> updatePrice());

        //Initialize CheckBox
        checkboxSoldes.setVisible(false);
        checkboxSoldes.setOnAction(event -> updatePrice());

        //Initialize TableView
        String[] colsNames = {"id", "Name", "SP", "PP", "Qty", "Type","Size"};
        String[] colsProperties = {"id", "name", "sellingPrice", "purchasePrice", "nbItems", "type","size"};
        String[] colsInfos = {"Product ID", "Product Name", "Selling Price", "Purchase Price", "Quantity", "Category","Size"};
        int[] colsWidth = {20, 120, 40, 40, 50,75,40};
        for (int i = 0; i < colsNames.length; i++){
            var col = new TableColumn<Product, Type>();
            col.setCellValueFactory(new PropertyValueFactory<>(colsProperties[i]));
            col.setMinWidth(colsWidth[i]);
            col.setMaxWidth(colsWidth[i]);
            col.setPrefWidth(colsWidth[i]);
            Label columnLabel = new Label(colsNames[i]);
            columnLabel.setTooltip(new Tooltip(colsInfos[i]));
            col.setGraphic(columnLabel);
            tvProducts.getColumns().add(col);
        }
        tvProducts.getSelectionModel().selectedItemProperty().addListener(e->
                displayProductDetails(tvProducts.getSelectionModel().getSelectedItem()));

        labelInfo.setText(null);

        //Fetch all data from database
        fetchProducts();
        fetchMoney();
    }

    //region Fetch and Update
    private void fetchMoney() {
        List<Double> money =manager.loadMoney();
        System.out.println(money);

        Product.setCapital(money.get(0));
        Product.setIncome(money.get(1));
        Product.setCost(money.get(2));
        this.txtCapital.setText(String.valueOf(Product.getCapital())+"€");
        this.txtIncome.setText(String.valueOf(Product.getIncome())+"€");
        this.txtCost.setText(String.valueOf(Product.getCost())+"€");
    }
    private void updateMoney() {
        manager.updateMoneyDb();
        this.txtCapital.setText(String.valueOf(Product.getCapital())+"€");
        this.txtIncome.setText(String.valueOf(Product.getIncome())+"€");
        this.txtCost.setText(String.valueOf(Product.getCost())+"€");
    }

    private void fetchProducts() {
        List<Product> listProduct=manager.loadProducts(comboBoxCategory.getValue());
        if(listProduct!=null)
            tvProducts.setItems(FXCollections.observableArrayList(listProduct));
    }

    private void updatePrice(){
        Product selectedProduct = tvProducts.getSelectionModel().getSelectedItem();
        String action = comboBoxBuySell.getValue();
        if(selectedProduct == null || action == null) return;
        selectedProduct = selectedProduct.copy();
        int quantity = spinnerQuantity.getValue();
        double unit;
        if(action.equals("Sell")){
            if(checkboxSoldes.isSelected())
                selectedProduct.applyDiscount();
            unit = selectedProduct.getSellingPrice();
        }
        else
            unit = selectedProduct.getPurchasePrice();

        txtUnitPrice.setText(String.valueOf(unit));
        txtTotalPrice.setText(String.valueOf(unit*quantity));
    }

    //endregion

    //region Details Display
    private void displayProductDetails(Product selectedItem) {
        if(selectedItem!=null)
        {
            txtName.setText(selectedItem.getName());
            txtBuying.setText(String.valueOf(selectedItem.getPurchasePrice()));
            comboBoxType.setValue(selectedItem.getType()); //-> call setDisplayFormat() on change
            txtQuantity.setText(String.valueOf(selectedItem.getNbItems()));
            txtSelling.setText(String.valueOf(selectedItem.getSellingPrice()));

            switch(selectedItem.getType()){
                case "Accessories":
                    txtSize.setText(null);
                    break;
                case "Clothes":
                    txtSize.setText(String.valueOf(((Clothes)selectedItem).getClothesSize()));
                    break;
                case "Shoes":
                    txtSize.setText(String.valueOf(((Shoes)selectedItem).getShoesSize()));
                    break;
            }
            updatePrice();
        }
    }

    private void setDisplayFormat(String category){
        switch(category){
            case "All":
                txtSize.setVisible(true);
                lblSize.setVisible(true);
                lblSize.setText("Test");

            case "Accessories":
                txtSize.setVisible(false);
                lblSize.setVisible(false);
                break;
            case "Clothes":
                txtSize.setVisible(true);
                lblSize.setVisible(true);
                lblSize.setText("Size");
                break;
            case "Shoes":
                txtSize.setVisible(true);
                lblSize.setVisible(true);
                lblSize.setText("Shoes Size");
                break;
        }
    }

    private Product getProductFromDetails(){
        String errMissingInfo = "You need to fill all information";
        String errValidInfo = "Incorrect Type Value";
        if(txtName.getText().isEmpty() ||
                txtQuantity.getText().isEmpty() ||
                txtBuying.getText().isEmpty() ||
                txtSelling.getText().isEmpty() ||
                comboBoxType.getValue() == null)
            throw new IllegalArgumentException(errMissingInfo);
        try{
            String name = txtName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double buyingPrice = Double.parseDouble(txtBuying.getText());
            double sellingPrice = Double.parseDouble(txtSelling.getText());
            String selectedType = comboBoxType.getValue();
            switch (selectedType) {
                case "Accessories":
                    return new Accessories(name, buyingPrice, sellingPrice, quantity);

                case "Shoes":
                    if (txtSize.getText().isEmpty()) throw new IllegalArgumentException(errMissingInfo);
                    int shoesSize = Integer.parseInt(txtSize.getText());
                    return new Shoes(name, buyingPrice, sellingPrice, quantity, shoesSize);

                case "Clothes":
                    if (txtSize.getText().isEmpty()) throw new IllegalArgumentException(errMissingInfo);
                    int clothesSize = Integer.parseInt(txtSize.getText());
                    return new Clothes(name, buyingPrice, sellingPrice, quantity, clothesSize);

                default:
                    throw new IllegalArgumentException("A type must be select");
            }
        }
        catch(NumberFormatException e){
            throw new IllegalArgumentException(errValidInfo);
        }
    }

    //endregion

    //region onAction methods
    public void onNew() {
        try{
            Product selectedProduct = getProductFromDetails();
            int val = selectedProduct.findMostSimilarProduct(manager.loadProducts("All")).similarityWith(selectedProduct);
            if (val > 2){
                String msg = val == 3 ?
                        "This product already exists with a different price.\nAre you sure you want to create a new ?" :
                        "This product already exists.\nAre you sure you want to create it ?";
                if(!confirmationDialog(msg)) return;
            }
            manager.createProduct(selectedProduct);
            labelInfo.setText("Added");
            clear();
            fetchProducts();
        }
        catch(Exception e) {
            displayError(e.getMessage());
        }
    }

    public void onUpdate(){
        try{
            Product selectedProduct = tvProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct == null)
                throw new Exception("You need to select a product");
            manager.updateProduct(getProductFromDetails(), selectedProduct.getId());

            labelInfo.setText("Updated");
            fetchProducts();
            clear();
        }
        catch(Exception e) {
            displayError(e.getMessage());
        }
    }

    public void onDelete() {
        Product selectedProduct = tvProducts.getSelectionModel().getSelectedItem();

        if (selectedProduct != null && confirmationDialog("This product will be delete")) {
            System.out.println(selectedProduct);
            manager.deleteProduct(selectedProduct.getId());

            labelInfo.setText("Deleted");
            fetchProducts();
            clear();
        }
    }

    public void onClear(){
        comboBoxCategory.setValue("All");
        clear();
        labelInfo.setText("");
    }

    public void onSelectBuySell(){
        String action = comboBoxBuySell.getValue();
        btnBuySell.setText(action);
        if (action.equals("Sell")){
            checkboxSoldes.setVisible(true);
            btnBuySell.setOnAction(event -> onSell());
        }
        else if(action.equals("Buy")){
            checkboxSoldes.setVisible(false);
            btnBuySell.setOnAction(event -> onBuy());
        }
        updatePrice();
    }

    public void onSell(){
        try {
            int quantity = spinnerQuantity.getValue();
            Product selectedProduct = tvProducts.getSelectionModel().getSelectedItem();
            if(selectedProduct == null)
                throw new Exception("Select a product to Sell");
            if (checkboxSoldes.isSelected())
                selectedProduct.applyDiscount();
            selectedProduct.sell(quantity);
            manager.updateProductQty(selectedProduct);
            updateMoney();

            labelInfo.setText("Sold");
            fetchProducts();
            clear();

        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    public void onBuy(){
        try {
            int quantity = spinnerQuantity.getValue();
            Product selectedProduct = tvProducts.getSelectionModel().getSelectedItem();
            if(selectedProduct == null)
                throw new Exception("Select a product to Buy");
            selectedProduct.purchase(quantity);
            manager.updateProductQty(selectedProduct);
            updateMoney();

            labelInfo.setText("Bought");
            fetchProducts();
            clear();
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    public void addCapital(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Capital");
        dialog.setHeaderText("Capital to add :");
        try{
            var res = dialog.showAndWait();
            if (res.isEmpty()) return;
            Product.addCapital(Double.parseDouble(res.get()));
            updateMoney();
        } catch(IllegalArgumentException e){
            displayError(e.getMessage());
        }
    }

    //endregion

    public void clear() {
        tvProducts.getSelectionModel().clearSelection();
        txtName.setText("");
        txtBuying.setText("");
        //comboBoxType.setValue("");
        txtQuantity.setText("");
        txtSelling.setText("");
        txtSize.setText("");
        //comboBoxCategory.setValue("All");
        checkboxSoldes.setSelected(false);
        txtTotalPrice.setText("");
        txtUnitPrice.setText("");
        spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
    }

    private void displayError(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private boolean confirmationDialog(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        var result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
            return true;
        return false;
    }

}
