package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductReaderTest {

    @Mock
    private IProductRepository iProductRepository;

    @InjectMocks
    private ProductReader productReader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSelectOneExistingProduct() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        when(iProductRepository.findByProductId(anyLong())).thenReturn(Optional.of(product));

        // When
        Optional<Product> result = productReader.selectOne(productId);

        // Then
        verify(iProductRepository, times(1)).findByProductId(productId);
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void testSelectOneNonExistingProduct() {
        // Given
        Long productId = 1L;
        when(iProductRepository.findByProductId(anyLong())).thenReturn(Optional.empty());

        // When
        Optional<Product> result = productReader.selectOne(productId);

        // Then
        verify(iProductRepository, times(1)).findByProductId(productId);
        assertTrue(result.isEmpty());
    }
}
