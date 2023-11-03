package ra.model;

import java.io.Serializable;

public class Products implements Serializable {
    private int productId;
    private String productName;
    private int catalogId;
    private String description;
    private double price;
    private int stock;
    private boolean status;

    public Products() {
    }

    public Products(int productId, String productName, int catalogId, String description, double price, int stock, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.catalogId = catalogId;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "ID: " + productId +
                        " - Name: " + productName +
                        " - Price: " + price +
                        " - CatalogId: " + catalogId +
                        " - Description: " + description +
                        " - Stock: " + stock +
                        " _ Status: " + (status?"ĐANG BÁN" : "KHÔNG BÁN");
    }


}
