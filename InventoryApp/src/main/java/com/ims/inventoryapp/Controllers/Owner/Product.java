package com.ims.inventoryapp.Controllers.Owner;

public class Product {
    String category;
    String product_type;
    String quantity;
    String selling_price;

    String gross_price;



    public Product(String category, String product_type, String quantity, String selling_price, String gross_price) {
        this.category = category;
        this.product_type = product_type;
        this.quantity = quantity;
        this.selling_price = selling_price;
        this.gross_price = gross_price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.product_type = quantity;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.product_type = selling_price;
    }

    public String getGross_price() {
        return gross_price;
    }

    public void setGross_price(String gross_price) {
        this.gross_price = gross_price;
    }
}
