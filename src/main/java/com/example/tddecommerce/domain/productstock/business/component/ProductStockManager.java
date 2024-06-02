package com.example.tddecommerce.domain.productstock.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductStockManager {

    public Map<Product, ProductStock> manageProductStock(List<ProductOrderItem> items) {
        return items.stream().collect(Collectors.toMap(
                ProductOrderItem::getProduct,
                item -> {
//                    ProductStock productStock = productStockReader.getProductStock(item.getProduct());
//                    if (productStock.getQuantity() < item.getQuantity()) {
//                        throw new ProductException("Insufficient stock for product: " + item.getProduct().getId());
//                    }
//                    productStockUpdater.decreaseStock(productStock, item.getQuantity());
//                    return productStock;
                    return null;
                }
        ));
    }

}
