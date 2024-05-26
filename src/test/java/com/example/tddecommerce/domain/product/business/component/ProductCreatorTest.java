package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.infrastructure.ProductRepository;
import com.example.tddecommerce.domain.product.business.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductCreatorTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductCreator productCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProduct() {
        // Given
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        Product savedProduct = productCreator.save(product);

        // Then
        verify(productRepository, times(1)).save(product);
        assertEquals(product, savedProduct);
    }
}
