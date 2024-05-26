package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.infrastructure.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductDeleterTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductDeleter productDeleter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteProduct() {
        // Given
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDelFlag(false);

        // When
        productDeleter.deleteProduct(product);

        // Then
        verify(productRepository, times(1)).save(product);
        assertTrue(product.isDelFlag());
    }
}
