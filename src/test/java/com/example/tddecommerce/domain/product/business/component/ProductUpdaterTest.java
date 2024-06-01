package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.infrastructure.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductUpdaterTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductUpdater productUpdater;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateProduct() {
        // Given
        Product product = new Product();
//        product.setId(1L);
//        product.setName("Old Product");
        product.changePrice(new BigDecimal("100.00"));
//        product.setDescription("Old Description");
//        product.setDiscountPolicy(DiscountPolicy.NONE);

        String newName = "New Product";
        BigDecimal newPrice = new BigDecimal("150.00");
        String newDescription = "New Description";
        DiscountPolicy newDiscountPolicy = DiscountPolicy.FIX_1000_AMOUNT;

        Product updatedProduct = new Product();
//        updatedProduct.setId(1L);
//        updatedProduct.setName(newName);
        updatedProduct.changePrice(newPrice);
//        updatedProduct.setDescription(newDescription);
//        updatedProduct.setDiscountPolicy(newDiscountPolicy);

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        Product result = productUpdater.updateProduct(product, newName, newPrice, newDescription, newDiscountPolicy);

        // Then
        verify(productRepository, times(1)).save(product);
        assertEquals(newName, result.getName());
        assertEquals(newPrice, result.getPrice());
        assertEquals(newDescription, result.getDescription());
        assertEquals(newDiscountPolicy, result.getDiscountPolicy());
    }
}
