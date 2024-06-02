package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductUpdaterTest {

    private IProductRepository iProductRepository;

    private ProductUpdater productUpdater;

    @BeforeEach
    void setUp() {
        iProductRepository = Mockito.mock(IProductRepository.class);
        productUpdater = new ProductUpdater(iProductRepository);
    }

    @Test
    void testUpdateProduct() {
        // Given
        Product product = new Product("Old Product", new BigDecimal("100.00"), "Old Description", DiscountPolicy.NONE);
        String newName = "New Product";
        BigDecimal newPrice = new BigDecimal("150.00");
        String newDescription = "New Description";
        DiscountPolicy newDiscountPolicy = DiscountPolicy.FIX_1000_AMOUNT;

        // 상품 업데이트 시 반환될 상품을 설정
        Product updatedProduct = new Product(newName, newPrice, newDescription, newDiscountPolicy);
        when(iProductRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        Product result = productUpdater.updateProduct(product, newName, newPrice, newDescription, newDiscountPolicy);

        // Then
        verify(iProductRepository, times(1)).save(product);  // save 메서드가 호출되었는지 확인
        assertEquals(newName, result.getName(), "Name should be updated.");
        assertEquals(newPrice, result.getPrice(), "Price should be updated.");
        assertEquals(newDescription, result.getDescription(), "Description should be updated.");
        assertEquals(newDiscountPolicy, result.getDiscountPolicy(), "Discount policy should be updated.");
    }
}
