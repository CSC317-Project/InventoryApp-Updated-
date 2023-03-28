package com.ims.inventoryapp.Controllers.Owner;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Category1_4Controller implements Initializable {
    @FXML
    public ChoiceBox<String> category_selector;
    @FXML
    public ChoiceBox<String> product_selector1;

    @FXML
    public ChoiceBox<String> product_selector2;
    @FXML
    public TextField quantity_fld1;
    @FXML
    public TextField quantity_fld2;
    @FXML
    public TextField selling_price_fld1;

    @FXML
    public TextField selling_price_fld2;
    @FXML
    public Label gross_lbl1;

    @FXML
    public TableView<Product> table_view;

    @FXML
    public TableColumn<Product, String> category_clmn;

    @FXML
    public TableColumn<Product, String> product_type_clmn;

    @FXML
    public TableColumn<Product, String> quantity_clmn;

    @FXML
    public TableColumn<Product, String> selling_price_clmn;

    @FXML
    public TableColumn<Product, String> gross_price_clmn;

    public ObservableList<Product> tableDataList;

    public Button save_btn;
    public Button add_good_btn;
    public Button remove_good_btn;
    public CheckBox gross_checkbox1;

    public CheckBox gross_checkbox2;
    public Label gross_lbl2;
    public Label error_lbl;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set up category choice box and populate with list...control and populate product choice box according to selected category
        category_selector.setItems(FXCollections.observableArrayList("Beverages", "Bread/Bakery", "Canned/Jarred Goods", "Dairy"));
        category_selector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            // Clear the product type choice boxes and add the appropriate items based on the selected category
            product_selector1.getItems().clear();
            product_selector2.getItems().clear();

            // clear the input fields and  uncheck checkboxes
            quantity_fld1.setText("");
            quantity_fld2.setText("");
            selling_price_fld1.setText("");
            selling_price_fld2.setText("");

            if (gross_checkbox1.isSelected()) {
                gross_checkbox1.setSelected(false);
                gross_lbl1.setText("");
            }
            if (gross_checkbox2.isSelected()) {
                gross_checkbox2.setSelected(false);
                gross_lbl2.setText("");
            }

            // set product type choice-boxes according to category type
            if (newValue.equals("Beverages")) {
                product_selector1.getItems().addAll("Tea/Coffee", "Alcohol", "Juice", "Soda");
                product_selector2.getItems().addAll("Tea/Coffee", "Alcohol", "Juice", "Soda");
            } else if (newValue.equals("Bread/Bakery")) {
                product_selector1.getItems().addAll("Sandwich loaves", "Dinner Rolls", "Tortillas", "Bagels");
                product_selector2.getItems().addAll("Sandwich loaves", "Dinner Rolls", "Tortillas", "Bagels");
            } else if (newValue.equals("Canned/Jarred Goods")) {
                product_selector1.getItems().addAll("Vegetables", "Spaghetti sauce", "Ketchup");
                product_selector2.getItems().addAll("Vegetables", "Spaghetti sauce", "Ketchup");
            } else if (newValue.equals("Dairy")) {
                product_selector1.getItems().addAll("Cheese", "Eggs", "Milk", "Yoghurt", "Butter");
                product_selector2.getItems().addAll("Cheese", "Eggs", "Milk", "Yoghurt", "Butter");

            }
        });

        // Set a calculation event handler for  gross_checkbox1
        gross_checkbox1.setOnAction(event -> {
            if (gross_checkbox1.isSelected()) {
                // Get the quantity and unit price values from the input fields
                double quantity;
                double unitPrice;
                try {
                    quantity = Integer.parseInt(quantity_fld1.getText());
                    unitPrice = Double.parseDouble(selling_price_fld1.getText());
                } catch (NumberFormatException e) {
                    error_lbl.setText("Calculation Error: Check Input");
                    gross_checkbox1.setSelected(false); // force checkbox uncheckable until correct calculation made
                    return;
                }
                error_lbl.setText(""); // clear error message

                // Calculate the gross_total
                double gross_total = quantity * unitPrice;

                // Update the subtotal label
                gross_lbl1.setText(String.format(" $%.2f", gross_total));
            }
            else {
                gross_lbl1.setText("");
            }
        });


        // Set a calculation event handler for  gross_checkbox2
        gross_checkbox2.setOnAction(event -> {
            if (gross_checkbox2.isSelected()) {
                // Get the quantity and unit price values from the input fields
                double quantity;
                double unitPrice;
                try {
                    quantity = Integer.parseInt(quantity_fld2.getText());
                    unitPrice = Double.parseDouble(selling_price_fld2.getText());
                } catch (NumberFormatException e) {
                    error_lbl.setText("Calculation Error: Check Input");
                    gross_checkbox2.setSelected(false);
                    return;
                }

                error_lbl.setText(""); // clear error message


                // Calculate the subtotal
                double gross_total = quantity * unitPrice;

                // Update the subtotal label
                gross_lbl2.setText(String.format(" $%.2f", gross_total));
            }
            else {
                gross_lbl2.setText("");
            }
                }
        );

        // Set event handlers to the text fields to uncheck the checkboxes and clear labels if the text fields are selected
        quantity_fld1.setOnMouseClicked(event -> {
            if (gross_checkbox1.isSelected()) {
                gross_checkbox1.setSelected(false);
                gross_lbl1.setText("");
            }
        });

        selling_price_fld1.setOnMouseClicked(event -> {
            if (gross_checkbox1.isSelected()) {
                gross_checkbox1.setSelected(false);
                gross_lbl1.setText("");
            }
        });

        quantity_fld2.setOnMouseClicked(event -> {
            if (gross_checkbox2.isSelected()) {
                gross_checkbox2.setSelected(false);
                gross_lbl2.setText("");
            }
        });

        selling_price_fld2.setOnMouseClicked(event -> {
            if (gross_checkbox2.isSelected()) {
                gross_checkbox2.setSelected(false);
                gross_lbl2.setText("");
            }
        });

        add_good_btn.setOnAction(event ->{
            if (product_selector1.getValue() == null || quantity_fld1.getText() == "" || selling_price_fld1.getText() == "" || gross_lbl1.getText() == ""){
                error_lbl.setText("Can't store information with missing values");
            }
            else{
                addRow();
                error_lbl.setText("");
            }

        });

        remove_good_btn.setOnAction(event ->{
            if (!tableDataList.isEmpty()){
                tableDataList.remove(0);
            }
            table_view.setItems(tableDataList);
        });

        // Initialize the table and columns
        tableDataList = FXCollections.observableArrayList();
        table_view.setItems(tableDataList);
        category_clmn.setCellValueFactory(new PropertyValueFactory<>("category"));
        product_type_clmn.setCellValueFactory(new PropertyValueFactory<>("product_type"));
        quantity_clmn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        selling_price_clmn.setCellValueFactory(new PropertyValueFactory<>("selling_price"));
        gross_price_clmn.setCellValueFactory(new PropertyValueFactory<>("gross_price"));




    }

    private void addRow() {
        String category = category_selector.getValue();
        String product_type = product_selector1.getValue();
        String quantity = quantity_fld1.getText();
        String selling_price = selling_price_fld1.getText();
        String gross_price = gross_lbl1.getText();
        Product product_data = new Product(category, product_type, quantity, selling_price, gross_price);
        tableDataList.add(product_data);
        Collections.reverse(tableDataList);
        table_view.setItems(tableDataList);
        product_selector1.getSelectionModel().clearSelection();
        quantity_fld1.clear();
        selling_price_fld1.clear();
        gross_checkbox1.setSelected(false);
        gross_lbl1.setText("");
    }

    private  void removeRow(){

    }
}

