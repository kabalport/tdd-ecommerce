package com.example.tddecommerce.order;

import java.time.LocalDateTime;

public class ProductStock {

    private Long productStockId;

    private Long productId;
    private Product product;
    private int quantity;


    private LocalDateTime lastUpdated;

    public Long getProductStockId() {
        return productStockId;
    }

    public void setProductStockId(Long productStockId) {
        this.productStockId = productStockId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void decreaseStock(int amount) {
        if (this.quantity < amount) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }
        this.quantity -= amount;
        this.lastUpdated = LocalDateTime.now();
    }
}
