package edu.fra.uas.shoppinglistitemmodel;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShoppingListItem {
     private static final Logger log = LoggerFactory.getLogger(ShoppingListItem.class);



    private long id;
    private String productName;
    private int quantity;
    private double productPrice;
    private String category;

    public ShoppingListItem() {
        log.debug("ShoppingList created without values");

    }

    public ShoppingListItem(long id, String productName, int quantity, double productPrice, String category) {
        this.category = category;
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        log.debug("ShoppingList create" + getProductName());

    }

    // Setter to set the product name
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter to retrieve the product name
    public String getProductName() {
        return productName;
    }

    // Setter to set the quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter to retrieve the quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter to set the product price
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    // Getter to retrieve the product price
    public double getProductPrice() {
        return productPrice;
    }

    // Setter to set the ID
    public void setId(long id) {
        this.id = id;
    }

    // Getter to retrieve the ID
    public long getId() {
        return id;
    }

    // The getter and setter for the approach discussed above
    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
