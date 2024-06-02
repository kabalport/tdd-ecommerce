package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockReader;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockUpdater;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductStockManager {

    private final ProductStockUpdater productStockUpdater;
    private final ProductStockReader productStockReader;

    public Map<Product, ProductStock> manageProductStock(List<ProductOrderItem> items) {
        return items.stream().collect(Collectors.toMap(
                ProductOrderItem::getProduct,
                item -> {
                    ProductStock productStock = productStockReader.getProductStock(item.getProduct().getId());
                    if (productStock.getQuantity() < item.getQuantity()) {
                        throw new ProductException("Insufficient stock for product: " + item.getProduct().getId());
                    }
//                    productStockUpdater.decreaseStock(productStock, item.getQuantity());
                    return productStock;
                }
        ));
    }


}
